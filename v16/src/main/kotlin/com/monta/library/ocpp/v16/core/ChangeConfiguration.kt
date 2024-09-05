package com.monta.library.ocpp.v16.core

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest

/**
 * Central System can request a Charge Point to change configuration parameters. To achieve this, Central
 * System SHALL send a ChangeConfiguration.req. This request contains a key-value pair, where "key" is
 * the name of the configuration setting to change and "value" contains the new setting for the
 * configuration setting.
 *
 * Upon receipt of a ChangeConfiguration.req Charge Point SHALL reply with a ChangeConfiguration.conf
 * indicating whether it was able to execute the change. Content of "key" and "value" is not prescribed. If
 * "key" does not correspond to a configuration setting supported by Charge Point, it SHALL reply with a
 * status NotSupported. If the change was executed successfully, the Charge Point SHALL respond with a
 * status Accepted. If the change was executed successfully, but a reboot is needed to apply it, the Charge
 * Point SHALL respond with status RebootRequired In case of failure to set the configuration, the Charge
 * Point SHALL respond with status Rejected.
 *
 * If a key value is defined as a CSL, it MAY be accompanied with a KeyName MaxLength key, indicating the
 * max length of the CSL in items. If this key is not set, a safe value of 1 (one) item SHOULD be assumed.
 */
object ChangeConfigurationFeature : Feature {
    override val name: String = "ChangeConfiguration"
    override val requestType: Class<out OcppRequest> = ChangeConfigurationRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = ChangeConfigurationConfirmation::class.java
}

data class ChangeConfigurationRequest(
    /**
     * String[50]
     *
     * Required
     *
     * The name of the configuration setting to change. See for standard configuration key names and associated values
     */
    val key: String,
    /**
     * String[500]
     *
     * Required
     *
     * The new value as string for the setting. See for standard configuration key names and associated values
     */
    val value: String?
) : OcppRequest

enum class ConfigurationStatus {
    /**
     * Configuration key supported and setting has been changed.
     */
    Accepted,

    /**
     * Configuration key supported, but setting could not be changed.
     */
    Rejected,

    /**
     * Configuration key supported and setting has been changed, but change will be available after reboot (Charge Point will not reboot itself)
     */
    RebootRequired,

    /**
     * Configuration key is not supported.
     */
    NotSupported
}

data class ChangeConfigurationConfirmation(
    /**
     * Required
     *
     * Returns whether configuration change has been accepted.
     */
    val status: ConfigurationStatus
) : OcppConfirmation
