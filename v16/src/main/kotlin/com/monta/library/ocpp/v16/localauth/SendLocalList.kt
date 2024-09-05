package com.monta.library.ocpp.v16.localauth

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v16.IdTagInfo

object SendLocalAuthListFeature : Feature {
    override val name: String = "SendLocalList"
    override val requestType: Class<out OcppRequest> = SendLocalListRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = SendLocalListConfirmation::class.java
}

data class SendLocalListRequest(
    /**
     * Required
     * In case of a full update this is the version number of the full list. In case of a differential update it is the version number of the list after the update has been applied.
     */
    val listVersion: Int,
    /**
     * Optional
     * In case of a full update this contains the list of values that form the new local authorization list. In case of a differential update it contains the changes to be applied to the local authorization list in the Charge Point.
     * Maximum number of AuthorizationData elements is available in the configuration key: SendLocalListMaxLength
     */
    val localAuthorizationList: List<AuthorizationData>?,
    /**
     * Required
     * This contains the type of update (full or differential) of this request
     */
    val updateType: UpdateType
) : OcppRequest {
    data class AuthorizationData(
        /**
         * Required
         * The identifier to which this authorization applies
         */
        val idTag: String,

        /**
         * Optional
         *
         * (Required when UpdateType is Full)
         *
         * This contains information about authorization status, expiry and parent id. For a Differential update the following applies: If this element is present, then this entry SHALL be added or updated in the Local Authorization List. If this element is absent, than the entry for this idtag in the Local Authorization List SHALL be deleted
         */
        val idTagInfo: IdTagInfo?
    )

    enum class UpdateType {
        Differential,
        Full
    }
}

data class SendLocalListConfirmation(
    /**
     * Required
     * This indicates whether the Charge Point has successfully received and applied the update of the local authorization list.
     */
    val status: Status
) : OcppConfirmation {
    enum class Status {
        /**
         * Local Authorization List successfully updated.
         */
        Accepted,

        /**
         * Failed to update the Local Authorization List.
         */
        Failed,

        /**
         * Update of Local Authorization List is not supported by Charge Point.
         */
        NotSupported,

        /**
         * Version number in the request for a differential update is less or equal then version number of current list
         */
        VersionMismatch
    }
}
