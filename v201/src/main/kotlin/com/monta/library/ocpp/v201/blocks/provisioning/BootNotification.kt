package com.monta.library.ocpp.v201.blocks.provisioning

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.CustomData
import com.monta.library.ocpp.v201.common.StatusInfo
import java.time.ZonedDateTime

/**
 * - B01 Cold Boot Charging Station
 * - B02 Cold Boot Charging Station - Pending
 * - B03 Cold Boot Charging Station - Rejected
 * - B04 Offline Behavior Idle Charging Station
 */
object BootNotificationFeature : Feature {
    override val name: String = "BootNotification"
    override val requestType: Class<out OcppRequest> = BootNotificationRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = BootNotificationResponse::class.java
}

data class BootNotificationRequest(
    val chargingStation: ChargingStation,
    val reason: Reason,
    val customData: CustomData? = null
) : OcppRequest {

    data class ChargingStation(
        /**
         * Vendor-specific device identifier.
         **/
        val serialNumber: String? = null,
        /**
         * Defines the model of the device.
         **/
        val model: String,
        val modem: Modem? = null,
        /**
         * Identifies the vendor (not necessarily in a unique manner).
         **/
        val vendorName: String,
        /**
         * This contains the firmware version of the Charging Station.
         **/
        val firmwareVersion: String? = null,
        val customData: CustomData? = null
    ) {

        init {
            if (serialNumber != null) {
                require(serialNumber.length <= 25) {
                    "serialNumber length > maximum 25 - ${serialNumber.length}"
                }
            }
            require(model.length <= 20) {
                "model length > maximum 20 - ${model.length}"
            }
            require(vendorName.length <= 50) {
                "vendorName length > maximum 50 - ${vendorName.length}"
            }
            if (firmwareVersion != null) {
                require(firmwareVersion.length <= 50) {
                    "firmwareVersion length > maximum 50 - ${firmwareVersion.length}"
                }
            }
        }
    }

    data class Modem(
        /**
         * This contains the ICCID of the modem’s SIM card.
         **/
        val iccid: String? = null,
        /**
         * This contains the IMSI of the modem’s SIM card.
         * */
        val imsi: String? = null,
        val customData: CustomData? = null
    ) {

        init {
            if (iccid != null) {
                require(iccid.length <= 20) {
                    "iccid length > maximum 20 - ${iccid.length}"
                }
            }
            if (imsi != null) {
                require(imsi.length <= 20) {
                    "imsi length > maximum 20 - ${imsi.length}"
                }
            }
        }
    }

    enum class Reason {
        ApplicationReset,
        FirmwareUpdate,
        LocalReset,
        PowerUp,
        RemoteReset,
        ScheduledReset,
        Triggered,
        Unknown,
        Watchdog
    }
}

data class BootNotificationResponse(
    /**
     * This contains the CSMS’s current time.
     * */
    val currentTime: ZonedDateTime,
    /**
     * When "cmn_registrationstatus,Status" is Accepted, this contains the heartbeat interval in seconds.
     * If the CSMS returns something other than Accepted, the value of the interval field indicates the minimum wait time before sending a next BootNotification request.
     **/
    val interval: Long,
    val status: Status,
    val statusInfo: StatusInfo? = null,
    val customData: CustomData? = null
) : OcppConfirmation {
    enum class Status {
        Accepted,
        Pending,
        Rejected
    }
}
