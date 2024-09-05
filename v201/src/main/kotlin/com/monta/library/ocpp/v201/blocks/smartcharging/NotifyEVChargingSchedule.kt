package com.monta.library.ocpp.v201.blocks.smartcharging

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.CustomData
import com.monta.library.ocpp.v201.common.GenericStatus
import com.monta.library.ocpp.v201.common.StatusInfo
import com.monta.library.ocpp.v201.common.chargingprofile.ChargingSchedule
import java.time.ZonedDateTime

object NotifyEVChargingScheduleFeature : Feature {
    override val name: String = "NotifyEVChargingSchedule"
    override val requestType: Class<out OcppRequest> = NotifyEVChargingScheduleRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = NotifyEVChargingScheduleResponse::class.java
}

data class NotifyEVChargingScheduleRequest(
    /** Periods contained in the charging profile are relative to this point in time. */
    val timeBase: ZonedDateTime,
    val chargingSchedule: ChargingSchedule,
    /** The charging schedule contained in this notification applies to an EVSE. EvseId must be > 0. */
    val evseId: Long,
    val customData: CustomData? = null
) : OcppRequest

data class NotifyEVChargingScheduleResponse(
    val status: GenericStatus,
    val statusInfo: StatusInfo? = null,
    val customData: CustomData? = null
) : OcppConfirmation
