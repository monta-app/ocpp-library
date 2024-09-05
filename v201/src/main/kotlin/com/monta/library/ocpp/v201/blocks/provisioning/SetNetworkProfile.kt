package com.monta.library.ocpp.v201.blocks.provisioning

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.CustomData
import com.monta.library.ocpp.v201.common.StatusInfo

object SetNetworkProfileFeature : Feature {
    override val name: String = "SetNetworkProfile"
    override val requestType: Class<out OcppRequest> = SetNetworkProfileRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = SetNetworkProfileResponse::class.java
}

data class SetNetworkProfileRequest(
    /**
     * Slot in which the configuration should be stored.
     **/
    val configurationSlot: Long,
    val connectionData: ConnectionData,
    val customData: CustomData? = null
) : OcppRequest {

    data class ConnectionData(

        val ocppVersion: OCPPVersion,
        val ocppTransport: OCPPTransport,
        /**
         * URL of the CSMS(s) that this Charging Station communicates with.
         **/
        val ocppCsmsUrl: String,
        /**
         * Duration in seconds before a message send by the Charging Station via this network connection times-out.
         * The best setting depends on the underlying network and response times of the CSMS.
         * If you are looking for a some guideline: use 30 seconds as a starting point.
         **/
        val messageTimeout: Long,
        /**
         * This field specifies the security profile used when connecting to the CSMS with this NetworkConnectionProfile.
         * */
        val securityProfile: Long,
        val ocppInterface: OCPPInterface,
        val apn: APNSettings? = null,
        val vpn: VPNSettings? = null,
        val customData: CustomData? = null
    ) {

        init {
            require(ocppCsmsUrl.length <= 512) {
                "ocppCsmsUrl length > maximum 512 - ${ocppCsmsUrl.length}"
            }
        }
    }

    data class APNSettings(
        /** The Access Point Name as an URL. */
        val apn: String,
        /** APN username. */
        val apnUserName: String? = null,
        /** APN Password. */
        val apnPassword: String? = null,
        /** SIM card pin code. */
        val simPin: Long? = null,
        /** Preferred network, written as MCC and MNC concatenated. See note. */
        val preferredNetwork: String? = null,
        /**
         * Default: false. Use only the preferred Network, do
         * not dial in when not available. See Note.
         **/
        val useOnlyPreferredNetwork: Boolean = false,
        val apnAuthentication: APNAuthentication,
        val customData: CustomData? = null
    ) {

        init {
            require(apn.length <= 512) {
                "apn length > maximum 512 - ${apn.length}"
            }
            if (apnUserName != null) {
                require(apnUserName.length <= 20) {
                    "apnUserName length > maximum 20 - ${apnUserName.length}"
                }
            }
            if (apnPassword != null) {
                require(apnPassword.length <= 20) {
                    "apnPassword length > maximum 20 - ${apnPassword.length}"
                }
            }
            if (preferredNetwork != null) {
                require(preferredNetwork.length <= 6) {
                    "preferredNetwork length > maximum 6 - ${preferredNetwork.length}"
                }
            }
        }
    }

    enum class APNAuthentication {
        CHAP,
        NONE,
        PAP,
        AUTO
    }

    enum class OCPPVersion {
        OCPP12,
        OCPP15,
        OCPP16,
        OCPP20
    }

    enum class OCPPTransport {
        JSON,
        SOAP
    }

    enum class OCPPInterface {
        Wired0,
        Wired1,
        Wired2,
        Wired3,
        Wireless0,
        Wireless1,
        Wireless2,
        Wireless3
    }

    data class VPNSettings(
        /** VPN Server Address */
        val server: String,
        /** VPN User */
        val user: String,
        /** VPN group. */
        val group: String? = null,
        /** VPN Password. */
        val password: String,
        /** VPN shared secret. */
        val key: String,
        val type: Type,
        val customData: CustomData? = null
    ) {
        init {
            require(server.length <= 512) {
                "server length > maximum 512 - ${server.length}"
            }
            require(user.length <= 20) {
                "user length > maximum 20 - ${user.length}"
            }
            if (group != null) {
                require(group.length <= 20) {
                    "group length > maximum 20 - ${group.length}"
                }
            }
            require(password.length <= 20) {
                "password length > maximum 20 - ${password.length}"
            }
            require(key.length <= 255) {
                "key length > maximum 255 - ${key.length}"
            }
        }

        enum class Type {
            IKEv2,
            IPSec,
            L2TP,
            PPTP
        }
    }
}

data class SetNetworkProfileResponse(
    val status: Status,
    val statusInfo: StatusInfo? = null,
    val customData: CustomData? = null
) : OcppConfirmation {

    enum class Status {
        Accepted,
        Rejected,
        Failed
    }
}
