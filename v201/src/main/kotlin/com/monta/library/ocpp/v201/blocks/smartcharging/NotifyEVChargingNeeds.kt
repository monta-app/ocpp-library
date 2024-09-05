package com.monta.library.ocpp.v201.blocks.smartcharging

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.CustomData
import com.monta.library.ocpp.v201.common.StatusInfo
import java.time.ZonedDateTime

object NotifyEVChargingNeedsFeature : Feature {
    override val name: String = "NotifyEVChargingNeeds"
    override val requestType: Class<out OcppRequest> = NotifyEVChargingNeedsRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = NotifyEVChargingNeedsResponse::class.java
}

data class NotifyEVChargingNeedsRequest(
    /** Contains the maximum schedule tuples the car supports per schedule. */
    val maxScheduleTuples: Long? = null,
    val chargingNeeds: ChargingNeeds,
    /** Defines the EVSE and connector to which the EV is connected. EvseId may not be 0. */
    val evseId: Long,
    val customData: CustomData? = null
) : OcppRequest {

    data class ChargingNeeds(
        val customData: CustomData? = null,
        val acChargingParameters: ACChargingParameters? = null,
        val dcChargingParameters: DCChargingParameters? = null,
        val requestedEnergyTransfer: EnergyTransferMode,
        /** Charging_ Needs. Departure_ Time. Date_ Time
         urn:x-oca:ocpp:uid:1:569223
         Estimated departure time of the EV. */
        val departureTime: ZonedDateTime? = null
    )

    data class ACChargingParameters(
        val customData: CustomData? = null,
        /** AC_ Charging_ Parameters. Energy_ Amount. Energy_ Amount
         urn:x-oca:ocpp:uid:1:569211
         Amount of energy requested (in Wh). This includes energy required for preconditioning. */
        val energyAmount: Long,
        /** AC_ Charging_ Parameters. EV_ Min. Current
         urn:x-oca:ocpp:uid:1:569212
         Minimum current (amps) supported by the electric vehicle (per phase). */
        val evMinCurrent: Long,
        /** AC_ Charging_ Parameters. EV_ Max. Current
         urn:x-oca:ocpp:uid:1:569213
         Maximum current (amps) supported by the electric vehicle (per phase). Includes cable capacity. */
        val evMaxCurrent: Long,
        /** AC_ Charging_ Parameters. EV_ Max. Voltage
         urn:x-oca:ocpp:uid:1:569214
         Maximum voltage supported by the electric vehicle */
        val evMaxVoltage: Long
    )

    data class DCChargingParameters(
        val customData: CustomData? = null,
        /** DC_ Charging_ Parameters. EV_ Max. Current
         urn:x-oca:ocpp:uid:1:569215
         Maximum current (amps) supported by the electric vehicle. Includes cable capacity. */
        val evMaxCurrent: Long,
        /** DC_ Charging_ Parameters. EV_ Max. Voltage
         urn:x-oca:ocpp:uid:1:569216
         Maximum voltage supported by the electric vehicle */
        val evMaxVoltage: Long,
        /** DC_ Charging_ Parameters. Energy_ Amount. Energy_ Amount
         urn:x-oca:ocpp:uid:1:569217
         Amount of energy requested (in Wh). This inludes energy required for preconditioning. */
        val energyAmount: Long? = null,
        /** DC_ Charging_ Parameters. EV_ Max. Power
         urn:x-oca:ocpp:uid:1:569218
         Maximum power (in W) supported by the electric vehicle. Required for DC charging. */
        val evMaxPower: Long? = null,
        /** DC_ Charging_ Parameters. State_ Of_ Charge. Numeric
         urn:x-oca:ocpp:uid:1:569219
         Energy available in the battery (in percent of the battery capacity) */
        val stateOfCharge: Int? = null,
        /** DC_ Charging_ Parameters. EV_ Energy_ Capacity. Numeric
         urn:x-oca:ocpp:uid:1:569220
         Capacity of the electric vehicle battery (in Wh) */
        val evEnergyCapacity: Long? = null,
        /** DC_ Charging_ Parameters. Full_ SOC. Percentage
         urn:x-oca:ocpp:uid:1:569221
         Percentage of SoC at which the EV considers the battery fully charged. (possible values: 0 - 100) */
        val fullSoC: Int? = null,
        /** DC_ Charging_ Parameters. Bulk_ SOC. Percentage
         urn:x-oca:ocpp:uid:1:569222
         Percentage of SoC at which the EV considers a fast charging process to end. (possible values: 0 - 100) */
        val bulkSoC: Int? = null
    ) {

        init {
            if (stateOfCharge != null) {
                require(stateOfCharge in 0..100) {
                    "stateOfCharge not in range 0..100 - $stateOfCharge"
                }
            }
            if (fullSoC != null) {
                require(fullSoC in 0..100) {
                    "fullSoC not in range 0..100 - $fullSoC"
                }
            }
            if (bulkSoC != null) {
                require(bulkSoC in 0..100) {
                    "bulkSoC not in range 0..100 - $bulkSoC"
                }
            }
        }
    }

    @Suppress("EnumEntryName")
    enum class EnergyTransferMode {
        DC,
        AC_single_phase,
        AC_two_phase,
        AC_three_phase
    }
}

data class NotifyEVChargingNeedsResponse(
    val status: Status,
    val statusInfo: StatusInfo? = null,
    val customData: CustomData? = null
) : OcppConfirmation {

    enum class Status {
        Accepted,
        Rejected,
        Processing
    }
}
