package com.monta.library.ocpp.v16.security

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import java.time.ZonedDateTime

/**
 * To enable the Central System retrieving of log information from a Charge Point.
 *
 * This use case covers the functionality of getting log information from a Charge Point. The Central System can
 * request a Charge Point to upload a file with log information to a given location (URL). The format of this log file is
 * not prescribed. The Charge Point uploads a log file and gives information about the status of the upload by
 * sending status notifications to the Central System.
 */
object GetLogFeature : Feature {
    override val name: String = "GetLog"
    override val requestType: Class<out OcppRequest> = GetLogRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = GetLogConfirmation::class.java
}

enum class LogEnumType {
    /**
     * This contains the field definition of a diagnostics log file
     */
    DiagnosticsLog,

    /**
     * Sent by the Central System to the Charge Point to request that the Charge Point uploads the security log.
     */
    SecurityLog
}

/**
 * Detailed information the retrieval of logging entries.
 */
data class LogParametersType(
    /**
     * Required
     *
     * The URL of the location at the remote system where the log should be stored.
     */
    val remoteLocation: String,
    /**
     * Optional
     *
     * This contains the date and time of the oldest logging information to include in the diagnostics.
     */
    val oldestTimestamp: ZonedDateTime? = null,
    /**
     * This contains the date and time of the latest logging information to include in the diagnostics.
     */
    val latestTimestamp: ZonedDateTime? = null
)

data class GetLogRequest(
    /**
     * Required
     *
     * This contains the type of log file that the Charge Point should send.
     */
    val logType: LogEnumType,
    /**
     * Required
     *
     * The Id of this request
     */
    val requestId: Int,
    /**
     * Optional
     *
     * his specifies how many times the Charge Point must try to upload the log before giving up.
     * If this field is not present, it is left to Charge Point to decide how many times it wants to retry.
     */
    val retries: Int? = null,
    /**
     * Optional
     *
     * The interval in seconds after which a retry may be attempted.
     * If this field is not present, it is left to Charge Point to decide how long to wait between attempts.
     */
    val retryInterval: Int? = null,
    /**
     * Required
     *
     * This field specifies the requested log and the location to which the log should be sent.
     */
    val log: LogParametersType
) : OcppRequest

enum class LogStatusEnumType {
    /**
     * Accepted this log upload. This does not mean the log file is uploaded is successfully,
     * the Charge Point will now start the log file upload.
     */
    Accepted,

    /**
     * Log update request rejected.
     */
    Rejected,

    /**
     * Accepted this log upload, but in doing this has canceled an ongoing log file upload.
     */
    AcceptedCanceled
}

data class GetLogConfirmation(
    /**
     * Required
     *
     * This field indicates whether the Charge Point was able to accept the request.
     */
    val status: LogStatusEnumType,

    /**
     * String[256]
     *
     * Optional
     *
     * This contains the name of the log file that will be uploaded. This field is not present when no logging information is available.
     */
    val filename: String? = null
) : OcppConfirmation
