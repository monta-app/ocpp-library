package com.monta.library.ocpp.v201.blocks.localauthorizationlistmanagement

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.CustomData
import com.monta.library.ocpp.v201.common.IdToken
import com.monta.library.ocpp.v201.common.IdTokenInfo
import com.monta.library.ocpp.v201.common.StatusInfo

object SendLocalListFeature : Feature {
    override val name: String = "SendLocalList"
    override val requestType: Class<out OcppRequest> = SendLocalListRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = SendLocalListResponse::class.java
}

data class SendLocalListRequest(
    val localAuthorizationList: List<AuthorizationData>? = null,
    /** In case of a full update this is the version number of the full list. In case of a differential update it is the version number of the list after the update has been applied. */
    val versionNumber: Long,
    val updateType: Update,
    val customData: CustomData? = null
) : OcppRequest {

    init {
        if (localAuthorizationList != null) {
            require(localAuthorizationList.isNotEmpty()) {
                "localAuthorizationList length < minimum 1 - ${localAuthorizationList.size}"
            }
        }
    }

    data class AuthorizationData(
        val idToken: IdToken,
        val idTokenInfo: IdTokenInfo? = null
    )

    enum class Update {
        Differential,
        Full
    }
}

data class SendLocalListResponse(
    val status: Status,
    val statusInfo: StatusInfo? = null,
    val customData: CustomData? = null
) : OcppConfirmation {

    enum class Status {
        Accepted,
        Failed,
        VersionMismatch
    }
}
