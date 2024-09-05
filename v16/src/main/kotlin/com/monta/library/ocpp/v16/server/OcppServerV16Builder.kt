package com.monta.library.ocpp.v16.server

import com.monta.library.ocpp.server.BaseOcppServerBuilder
import com.monta.library.ocpp.v16.core.CoreServerProfile
import com.monta.library.ocpp.v16.extension.ExtensionProfileDispatcher
import com.monta.library.ocpp.v16.extension.plugandcharge.PlugAndChargeExtensionServerProfile
import com.monta.library.ocpp.v16.firmware.FirmwareManagementServerProfile
import com.monta.library.ocpp.v16.localauth.LocalListServerProfile
import com.monta.library.ocpp.v16.remotetrigger.TriggerMessageServerProfile
import com.monta.library.ocpp.v16.security.SecurityServerProfile
import com.monta.library.ocpp.v16.smartcharge.SmartChargeServerProfile

class OcppServerV16Builder : BaseOcppServerBuilder<OcppServerV16Builder>() {
    private val extensionProfiles: MutableSet<ExtensionProfileDispatcher> = mutableSetOf()

    fun addCore(listener: CoreServerProfile.Listener): OcppServerV16Builder {
        profiles.add(CoreServerProfile(listener))
        return this
    }

    fun addSecurity(listener: SecurityServerProfile.Listener): OcppServerV16Builder {
        profiles.add(SecurityServerProfile(listener))
        return this
    }

    fun addFirmwareManagement(
        listener: FirmwareManagementServerProfile.Listener
    ): OcppServerV16Builder {
        profiles.add(FirmwareManagementServerProfile(listener))
        return this
    }

    fun addLocalAuth(): OcppServerV16Builder {
        profiles.add(LocalListServerProfile())
        return this
    }

    fun addTriggerMessage(): OcppServerV16Builder {
        profiles.add(TriggerMessageServerProfile())
        return this
    }

    fun addSmartCharge(): OcppServerV16Builder {
        profiles.add(SmartChargeServerProfile())
        return this
    }

    fun addPlugAndCharge(listener: PlugAndChargeExtensionServerProfile.Listener): OcppServerV16Builder {
        extensionProfiles.add(PlugAndChargeExtensionServerProfile(listener))
        return this
    }

    fun build(): OcppServerV16 {
        if (sendMessage == null && ocppSessionRepository == null) {
            throw IllegalStateException("server must be configured in either 'gateway' or 'local mode'")
        }

        if (profiles.isEmpty()) {
            throw RuntimeException("OCPP feature profiles not setup")
        }

        return OcppServerV16(
            onConnect = requireNotNull(onConnect),
            onDisconnect = requireNotNull(onDisconnect),
            sendMessage = sendMessage,
            ocppSessionRepository = ocppSessionRepository,
            settings = requireNotNull(settings),
            profiles = profiles,
            extensionProfiles = extensionProfiles
        )
    }
}
