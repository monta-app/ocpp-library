package com.monta.library.ocpp.v201.common.chargingprofile

import com.monta.library.ocpp.common.chargingprofile.CommonChargingProfilePurpose

enum class ChargingProfilePurpose {
    ChargingStationExternalConstraints,
    ChargingStationMaxProfile,
    TxDefaultProfile,
    TxProfile;

    companion object {
        fun fromCommon(
            commonChargingProfilePurpose: CommonChargingProfilePurpose
        ): ChargingProfilePurpose {
            return when (commonChargingProfilePurpose) {
                CommonChargingProfilePurpose.TxDefaultProfile -> TxDefaultProfile
                CommonChargingProfilePurpose.TxProfile -> TxProfile
                CommonChargingProfilePurpose.ChargePointMaxProfile -> ChargingStationMaxProfile
            }
        }
    }
}
