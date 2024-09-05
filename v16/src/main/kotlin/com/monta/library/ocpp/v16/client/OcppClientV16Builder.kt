package com.monta.library.ocpp.v16.client

import com.monta.library.ocpp.client.BaseOcppClientBuilder
import com.monta.library.ocpp.v16.core.CoreClientProfile
import com.monta.library.ocpp.v16.firmware.FirmwareManagementClientProfile
import com.monta.library.ocpp.v16.localauth.LocalListClientProfile
import com.monta.library.ocpp.v16.remotetrigger.TriggerMessageClientProfile
import com.monta.library.ocpp.v16.security.SecurityClientProfile
import com.monta.library.ocpp.v16.smartcharge.SmartChargeClientProfile

class OcppClientV16Builder : BaseOcppClientBuilder<OcppClientV16Builder>() {

    fun addCore(
        listener: CoreClientProfile.Listener
    ): OcppClientV16Builder {
        profiles.add(CoreClientProfile(listener))
        return this
    }

    fun addSecurity(
        listener: SecurityClientProfile.Listener
    ): OcppClientV16Builder {
        profiles.add(SecurityClientProfile(listener))
        return this
    }

    fun addFirmwareManagement(
        listener: FirmwareManagementClientProfile.Listener
    ): OcppClientV16Builder {
        profiles.add(FirmwareManagementClientProfile(listener))
        return this
    }

    fun addLocalAuth(
        listener: LocalListClientProfile.Listener
    ): OcppClientV16Builder {
        profiles.add(LocalListClientProfile(listener))
        return this
    }

    fun addTriggerMessage(
        listener: TriggerMessageClientProfile.Listener
    ): OcppClientV16Builder {
        profiles.add(TriggerMessageClientProfile(listener))
        return this
    }

    fun addSmartCharge(
        listener: SmartChargeClientProfile.Listener
    ): OcppClientV16Builder {
        profiles.add(SmartChargeClientProfile(listener))
        return this
    }

    fun build(): OcppClientV16 {
        return OcppClientV16(
            onConnect = requireNotNull(onConnect),
            onDisconnect = requireNotNull(onDisconnect),
            ocppSessionRepository = requireNotNull(ocppSessionRepository),
            settings = requireNotNull(settings),
            profiles = profiles,
            sendHook = sendHook
        )
    }
}
