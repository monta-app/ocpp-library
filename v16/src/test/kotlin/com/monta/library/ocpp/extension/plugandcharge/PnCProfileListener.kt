package com.monta.library.ocpp.extension.plugandcharge

import com.monta.library.ocpp.common.serialization.Message
import com.monta.library.ocpp.common.session.OcppSession
import com.monta.library.ocpp.common.transport.OcppSettings
import com.monta.library.ocpp.v16.AuthorizationStatus
import com.monta.library.ocpp.v16.IdTagInfo
import com.monta.library.ocpp.v16.core.AuthorizeConfirmation
import com.monta.library.ocpp.v16.core.AuthorizeRequest
import com.monta.library.ocpp.v16.core.BootNotificationConfirmation
import com.monta.library.ocpp.v16.core.BootNotificationRequest
import com.monta.library.ocpp.v16.core.CoreServerProfile
import com.monta.library.ocpp.v16.core.DataTransferConfirmation
import com.monta.library.ocpp.v16.core.DataTransferRequest
import com.monta.library.ocpp.v16.core.HeartbeatConfirmation
import com.monta.library.ocpp.v16.core.HeartbeatRequest
import com.monta.library.ocpp.v16.core.MeterValuesConfirmation
import com.monta.library.ocpp.v16.core.MeterValuesRequest
import com.monta.library.ocpp.v16.core.StartTransactionConfirmation
import com.monta.library.ocpp.v16.core.StartTransactionRequest
import com.monta.library.ocpp.v16.core.StatusNotificationConfirmation
import com.monta.library.ocpp.v16.core.StatusNotificationRequest
import com.monta.library.ocpp.v16.core.StopTransactionConfirmation
import com.monta.library.ocpp.v16.core.StopTransactionRequest
import com.monta.library.ocpp.v16.extension.ExtensionDisabled
import com.monta.library.ocpp.v16.extension.plugandcharge.PlugAndChargeExtensionServerProfile
import com.monta.library.ocpp.v16.extension.plugandcharge.features.Get15118EVCertificateConfirmation
import com.monta.library.ocpp.v16.extension.plugandcharge.features.Get15118EVCertificateRequest
import com.monta.library.ocpp.v16.extension.plugandcharge.features.GetCertificateStatusConfirmation
import com.monta.library.ocpp.v16.extension.plugandcharge.features.GetCertificateStatusRequest
import com.monta.library.ocpp.v16.extension.plugandcharge.features.PncAuthorizeConfirmation
import com.monta.library.ocpp.v16.extension.plugandcharge.features.PncAuthorizeRequest
import com.monta.library.ocpp.v16.extension.plugandcharge.features.SignCertificateConfirmation
import com.monta.library.ocpp.v16.extension.plugandcharge.features.SignCertificateRequest
import com.monta.library.ocpp.v16.server.OcppServerV16
import com.monta.library.ocpp.v16.server.OcppServerV16Builder
import kotlinx.coroutines.channels.Channel
import org.slf4j.LoggerFactory
import java.time.ZonedDateTime

/**
 * A basic test implementation of the profile can return static responses.
 */
class PnCProfileListener : PlugAndChargeExtensionServerProfile.Listener {
    companion object {
        private val logger = LoggerFactory.getLogger(PnCProfileListener::class.java)
        fun createServer(
            reqChannel: Channel<Message.Request>,
            respChannel: Channel<Message.Response>,
            errorChannel: Channel<Message.Error>
        ): Pair<OcppServerV16, PnCProfileListener> {
            val listener = PnCProfileListener()
            val server: OcppServerV16 = OcppServerV16Builder()
                .settings(
                    OcppSettings()
                )
                .gatewayMode(
                    sendRequest = { _, ocppSessionInfo, message ->
                        logger.debug("sendRequest {} (session {})", message, ocppSessionInfo)
                        reqChannel.send(message)
                    },
                    sendResponse = { _, ocppSessionInfo, message ->
                        logger.debug("sendResponse {} (session {})", message, ocppSessionInfo)
                        respChannel.send(message)
                    },
                    sendError = { _, ocppSessionInfo, message ->
                        logger.debug("sendError {} (session {})", message, ocppSessionInfo)
                        errorChannel.send(message)
                    }
                )
                .onConnect { _ ->
                    logger.debug("onConnect")
                }
                .onDisconnect { _ ->
                    logger.debug("onDisconnect")
                }.apply {
                    // Can't process message without a core profile.
                    addCore(object : CoreServerProfile.Listener {
                        override suspend fun authorize(
                            ocppSessionInfo: OcppSession.Info,
                            request: AuthorizeRequest
                        ): AuthorizeConfirmation {
                            throw AssertionError("This method should never be reached.")
                        }

                        override suspend fun bootNotification(
                            ocppSessionInfo: OcppSession.Info,
                            request: BootNotificationRequest
                        ): BootNotificationConfirmation {
                            throw AssertionError("This method should never be reached.")
                        }

                        override suspend fun dataTransfer(
                            ocppSessionInfo: OcppSession.Info,
                            request: DataTransferRequest
                        ): DataTransferConfirmation {
                            throw AssertionError("This method should never be reached.")
                        }

                        override suspend fun heartbeat(
                            ocppSessionInfo: OcppSession.Info,
                            request: HeartbeatRequest
                        ): HeartbeatConfirmation {
                            throw AssertionError("This method should never be reached.")
                        }

                        override suspend fun meterValues(
                            ocppSessionInfo: OcppSession.Info,
                            request: MeterValuesRequest
                        ): MeterValuesConfirmation {
                            throw AssertionError("This method should never be reached.")
                        }

                        override suspend fun startTransaction(
                            ocppSessionInfo: OcppSession.Info,
                            request: StartTransactionRequest
                        ): StartTransactionConfirmation {
                            throw AssertionError("This method should never be reached.")
                        }

                        override suspend fun stopTransaction(
                            ocppSessionInfo: OcppSession.Info,
                            request: StopTransactionRequest
                        ): StopTransactionConfirmation {
                            throw AssertionError("This method should never be reached.")
                        }

                        override suspend fun statusNotification(
                            ocppSessionInfo: OcppSession.Info,
                            request: StatusNotificationRequest
                        ): StatusNotificationConfirmation {
                            throw AssertionError("This method should never be reached.")
                        }
                    })
                    addPlugAndCharge(listener)
                }.build()
            return Pair(server, listener)
        }
    }

    override suspend fun authorize(
        ocppSessionInfo: OcppSession.Info,
        request: PncAuthorizeRequest
    ): PncAuthorizeConfirmation {
        val cert = request.evX509Cert
        // Ideally we would get X500Name.getRFC2253Name - a collection of the RFC2253 names.
        // Then we don't need to parse them, but X500Name is hidden.
        return when (cert?.subjectX500Principal?.name) {
            "DC=MO,C=DE,O=EVerest,CN=UKSWI123456791A" -> {
                PncAuthorizeConfirmation(
                    idTokenInfo = IdTagInfo(
                        status = AuthorizationStatus.Accepted,
                        expiryDate = ZonedDateTime.now().plusHours(1)
                    ),
                    certificateStatus = PncAuthorizeConfirmation.Status.Accepted
                )
            }

            else -> {
                PncAuthorizeConfirmation(
                    idTokenInfo = IdTagInfo(
                        status = AuthorizationStatus.Blocked,
                        expiryDate = ZonedDateTime.now()
                    ),
                    certificateStatus = PncAuthorizeConfirmation.Status.CertChainError
                )
            }
        }
    }

    override suspend fun get15118EVCertificate(
        ocppSessionInfo: OcppSession.Info,
        request: Get15118EVCertificateRequest
    ): Get15118EVCertificateConfirmation {
        return when (request.iso15118SchemaVersion) {
            "urn:iso:15118:2:2013:MsgDef" -> {
                Get15118EVCertificateConfirmation(
                    status = Get15118EVCertificateConfirmation.Status.Accepted,
                    exiResponse = "CertificateInstallationRes (ISO 15118:2) exi in base64"
                )
            }
            // Not sure if example is realistic, could be "urn:iso:std:iso:15118:-20:DC" too..
            "urn:iso:std:iso:15118:-20:CommonMessages" -> {
                // CertificateInstallationRes
                Get15118EVCertificateConfirmation(
                    status = Get15118EVCertificateConfirmation.Status.Accepted,
                    exiResponse = "CertificateInstallationRes (ISO 15118:20) exi in base64"
                )
            }

            else -> {
                Get15118EVCertificateConfirmation(
                    status = Get15118EVCertificateConfirmation.Status.Failed,
                    exiResponse = ""
                )
            }
        }
    }

    override suspend fun getCertificateStatus(
        ocppSessionInfo: OcppSession.Info,
        request: GetCertificateStatusRequest
    ): GetCertificateStatusConfirmation {
        return GetCertificateStatusConfirmation(
            status = GetCertificateStatusConfirmation.Status.Accepted,
            ocspResult = "ocsp result (ASN.1 DER) encoded in base64"
        )
    }

    override suspend fun signCertificate(
        ocppSessionInfo: OcppSession.Info,
        request: SignCertificateRequest
    ): SignCertificateConfirmation {
        return when (request.csr) {
            "not-enabled" -> {
                throw ExtensionDisabled("extension not enabled")
            }

            else -> SignCertificateConfirmation(
                SignCertificateConfirmation.Status.Accepted
            )
        }
    }
}
