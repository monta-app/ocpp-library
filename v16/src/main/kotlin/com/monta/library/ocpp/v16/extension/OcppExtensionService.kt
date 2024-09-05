package com.monta.library.ocpp.v16.extension

import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.common.serialization.MessageSerializer
import com.monta.library.ocpp.common.session.OcppSession
import com.monta.library.ocpp.common.transport.OcppCallException
import com.monta.library.ocpp.v16.core.DataTransferConfirmation
import com.monta.library.ocpp.v16.core.DataTransferRequest
import com.monta.library.ocpp.v16.core.DataTransferStatus
import com.monta.library.ocpp.v16.error.MessageErrorCodeV16
import org.slf4j.LoggerFactory

class OcppExtensionService(
    private val messageSerializer: MessageSerializer,
    extensionProfiles: Set<ExtensionProfileDispatcher>
) {
    companion object {
        @JvmStatic
        private val logger = LoggerFactory.getLogger(OcppExtensionService::class.java)
    }

    private val vendorIdMap =
        extensionProfiles.associateBy { profile ->
            profile.vendorId
        }

    suspend fun handleRequest(
        ocppSessionInfo: OcppSession.Info,
        request: DataTransferRequest
    ): DataTransferConfirmation? {
        // Try to locate our dispatcher in the map we created
        val extensionProfileDispatcher = vendorIdMap[request.vendorId]
        // If no dispatcher is found we can pass the data transfer request back to the call by return null
        if (extensionProfileDispatcher == null) {
            return null
        }

        val messageId = request.messageId

        if (messageId == null) {
            logger.warn("no message id passed for vendorId ${request.vendorId}")
            return DataTransferConfirmation(
                status = DataTransferStatus.UnknownMessageId,
                data = "messageId is required for this action to function"
            )
        }

        val requestType = extensionProfileDispatcher.getRequestType(messageId)

        if (requestType == null) {
            return DataTransferConfirmation(
                status = DataTransferStatus.UnknownMessageId,
                data = "incorrect or not supporting this messageId for this vendor id ${request.messageId}"
            )
        }

        val nestedRequest =
            try {
                messageSerializer.parseDataTransferExtension(request.data, requestType)
            } catch (
                @Suppress("detekt.TooGenericExceptionCaught") exception: Exception
            ) {
                logger.warn("failed to deserialize data transfer data for ocpp extension", exception)
                throw OcppCallException(MessageErrorCodeV16.FormationViolation)
            }

        try {
            val confirmation =
                extensionProfileDispatcher.handleRequest(
                    ocppSessionInfo = ocppSessionInfo,
                    request = nestedRequest
                )
            return DataTransferConfirmation(
                status = DataTransferStatus.Accepted,
                data = messageSerializer.toPayloadString(confirmation)
            )
        } catch (e: ExtensionDisabled) {
            logger.debug("Extension did not accept the application of the vendor extension in the current context", e)
            return DataTransferConfirmation(DataTransferStatus.UnknownVendorId)
        } catch (e: MessageNotApplicable) {
            logger.debug("Extension rejected the message in the current context", e)
            return DataTransferConfirmation(DataTransferStatus.Rejected)
        } catch (
            @Suppress("detekt.TooGenericExceptionCaught") exception: Exception
        ) {
            logger.warn("failed to serialize profile extension response", exception)
            throw OcppCallException(MessageErrorCodeV16.InternalError)
        }
    }

    suspend fun send(
        vendorId: String,
        request: OcppRequest,
        block: suspend (DataTransferRequest) -> DataTransferConfirmation
    ): OcppConfirmation {
        val dispatcher = vendorIdMap[vendorId]
        requireNotNull(dispatcher)
        val feature = dispatcher.getFeature(request::class.java)
        requireNotNull(feature)
        val dataTransferRequest =
            DataTransferRequest(
                vendorId = vendorId,
                messageId = feature.name,
                data = messageSerializer.toPayloadString(request)
            )
        val response = block(dataTransferRequest)
        return messageSerializer.parseDataTransferExtension(response.data, feature.confirmationType)
    }
}
