package com.monta.library.ocpp.v201.common.chargingprofile

import com.monta.library.ocpp.common.chargingprofile.ChargingProfileKind
import com.monta.library.ocpp.common.chargingprofile.CommonChargingProfile
import com.monta.library.ocpp.common.chargingprofile.RecurrencyKind
import com.monta.library.ocpp.common.toZonedDateTime
import com.monta.library.ocpp.v201.common.CustomData
import java.time.ZonedDateTime

data class ChargingProfile(
    /**
     * Id of ChargingProfile.
     **/
    val id: Int,
    /**
     * Value determining level in hierarchy stack of profiles.
     * Higher values have precedence over lower values.
     * Lowest level is 0.
     **/
    val stackLevel: Int,
    val chargingProfilePurpose: ChargingProfilePurpose,
    val chargingProfileKind: ChargingProfileKind,
    val recurrencyKind: RecurrencyKind? = null,
    /** Charging_ Profile. Valid_ From. Date_ Time
     urn:x-oca:ocpp:uid:1:569234
     Point in time at which the profile starts to be valid. If absent, the profile is valid as soon as it is received by the Charging Station. */
    val validFrom: ZonedDateTime? = null,
    /** Charging_ Profile. Valid_ To. Date_ Time
     urn:x-oca:ocpp:uid:1:569235
     Point in time at which the profile stops to be valid. If absent, the profile is valid until it is replaced by another profile. */
    val validTo: ZonedDateTime? = null,
    val chargingSchedule: List<ChargingSchedule>,
    /** SHALL only be included if ChargingProfilePurpose is set to TxProfile. The transactionId is used to match the profile to a specific transaction. */
    val transactionId: String? = null,
    val customData: CustomData? = null
) {

    companion object {
        fun fromCommon(
            commonChargingProfile: CommonChargingProfile
        ): ChargingProfile {
            return ChargingProfile(
                id = commonChargingProfile.id,
                stackLevel = commonChargingProfile.stackLevel,
                chargingProfilePurpose = ChargingProfilePurpose.fromCommon(
                    commonChargingProfilePurpose = commonChargingProfile.chargingProfilePurpose
                ),
                chargingProfileKind = commonChargingProfile.chargingProfileKind,
                recurrencyKind = commonChargingProfile.recurrencyKind,
                validFrom = commonChargingProfile.validFrom.toZonedDateTime(),
                validTo = commonChargingProfile.validTo.toZonedDateTime(),
                chargingSchedule = listOf(
                    ChargingSchedule.fromCommon(
                        commonChargingSchedule = commonChargingProfile.chargingSchedule
                    )
                ),
                transactionId = commonChargingProfile.transactionId,
                customData = null
            )
        }
    }

    init {
        require(chargingSchedule.size in 1..3) {
            "chargingSchedule length not in range 1..3 - ${chargingSchedule.size}"
        }
        if (transactionId != null) {
            require(transactionId.length <= 36) {
                "transactionId length > maximum 36 - ${transactionId.length}"
            }
        }
    }
}
