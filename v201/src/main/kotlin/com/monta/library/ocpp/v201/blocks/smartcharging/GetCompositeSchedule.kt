package com.monta.library.ocpp.v201.blocks.smartcharging

import com.monta.library.ocpp.common.chargingprofile.ChargingRateUnit
import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.CustomData
import com.monta.library.ocpp.v201.common.GenericStatus
import com.monta.library.ocpp.v201.common.StatusInfo
import com.monta.library.ocpp.v201.common.chargingprofile.ChargingSchedulePeriod
import java.time.ZonedDateTime

object GetCompositeScheduleFeature : Feature {
    override val name: String = "GetCompositeSchedule"
    override val requestType: Class<out OcppRequest> = GetCompositeScheduleRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = GetCompositeScheduleResponse::class.java
}

data class GetCompositeScheduleRequest(
    /** Length of the requested schedule in seconds. */
    val duration: Long,
    val chargingRateUnit: ChargingRateUnit? = null,
    /** The ID of the EVSE for which the schedule is requested. When evseid=0, the Charging Station will calculate the expected consumption for the grid connection. */
    val evseId: Long,
    val customData: CustomData? = null
) : OcppRequest

data class GetCompositeScheduleResponse(
    val status: GenericStatus,
    val statusInfo: StatusInfo? = null,
    val schedule: CompositeSchedule? = null,
    val customData: CustomData? = null
) : OcppConfirmation {

    data class CompositeSchedule(
        val customData: CustomData? = null,
        val chargingSchedulePeriod: List<ChargingSchedulePeriod>,
        /** The ID of the EVSE for which the
         schedule is requested. When evseid=0, the
         Charging Station calculated the expected
         consumption for the grid connection. */
        val evseId: Long,
        /** Duration of the schedule in seconds. */
        val duration: Long,
        /** Composite_ Schedule. Start. Date_ Time
         urn:x-oca:ocpp:uid:1:569456
         Date and time at which the schedule becomes active. All time measurements within the schedule are relative to this timestamp. */
        val scheduleStart: ZonedDateTime,
        val chargingRateUnit: ChargingRateUnit
    ) {

        init {
            require(chargingSchedulePeriod.isNotEmpty()) {
                "chargingSchedulePeriod length < minimum 1 - ${chargingSchedulePeriod.size}"
            }
        }
    }
}
