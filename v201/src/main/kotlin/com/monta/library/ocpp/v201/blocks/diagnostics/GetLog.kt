package com.monta.library.ocpp.v201.blocks.diagnostics

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.CustomData
import com.monta.library.ocpp.v201.common.StatusInfo
import java.time.ZonedDateTime

object GetLogFeature : Feature {
    override val name: String = "GetLog"
    override val requestType: Class<out OcppRequest> = GetLogRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = GetLogResponse::class.java
}

data class GetLogRequest(
    val log: LogParameters,
    val logType: LogType,
    /** The Id of this request */
    val requestId: Long,
    /** This specifies how many times the Charging Station must try to upload the log before giving up. If this field is not present, it is left to Charging Station to decide how many times it wants to retry. */
    val retries: Long? = null,
    /** The interval in seconds after which a retry may be attempted. If this field is not present, it is left to Charging Station to decide how long to wait between attempts. */
    val retryInterval: Long? = null,
    val customData: CustomData? = null
) : OcppRequest {

    data class LogParameters(
        val customData: CustomData? = null,
        /** Log. Remote_ Location. URI
         urn:x-enexis:ecdm:uid:1:569484
         The URL of the location at the remote system where the log should be stored. */
        val remoteLocation: String,
        /** Log. Oldest_ Timestamp. Date_ Time
         urn:x-enexis:ecdm:uid:1:569477
         This contains the date and time of the oldest logging information to include in the diagnostics. */
        val oldestTimestamp: ZonedDateTime? = null,
        /** Log. Latest_ Timestamp. Date_ Time
         urn:x-enexis:ecdm:uid:1:569482
         This contains the date and time of the latest logging information to include in the diagnostics. */
        val latestTimestamp: ZonedDateTime? = null
    ) {

        init {
            require(remoteLocation.length <= 512) {
                "remoteLocation length > maximum 512 - ${remoteLocation.length}"
            }
        }
    }

    enum class LogType {
        DiagnosticsLog,
        SecurityLog
    }
}

data class GetLogResponse(
    val status: Status,
    val statusInfo: StatusInfo? = null,
    /** This contains the name of the log file that will be uploaded. This field is not present when no logging information is available. */
    val filename: String? = null,
    val customData: CustomData? = null
) : OcppConfirmation {

    init {
        if (filename != null) {
            require(filename.length <= 255) {
                "filename length > maximum 255 - ${filename.length}"
            }
        }
    }

    enum class Status {
        Accepted,
        Rejected,
        AcceptedCanceled
    }
}
