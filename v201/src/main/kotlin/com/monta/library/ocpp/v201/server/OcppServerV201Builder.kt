package com.monta.library.ocpp.v201.server

import com.monta.library.ocpp.server.BaseOcppServerBuilder
import com.monta.library.ocpp.v201.blocks.authorization.AuthorizationServerDispatcher
import com.monta.library.ocpp.v201.blocks.availability.AvailabilityServerDispatcher
import com.monta.library.ocpp.v201.blocks.certificatemanagement.CertificateManagementServerDispatcher
import com.monta.library.ocpp.v201.blocks.datatransfer.DataTransferServerDispatcher
import com.monta.library.ocpp.v201.blocks.diagnostics.DiagnosticsServerDispatcher
import com.monta.library.ocpp.v201.blocks.displaymessage.DisplayMessageServerDispatcher
import com.monta.library.ocpp.v201.blocks.firmwaremanagement.FirmwareManagementServerDispatcher
import com.monta.library.ocpp.v201.blocks.localauthorizationlistmanagement.LocalAuthorizationListManagementServerDispatcher
import com.monta.library.ocpp.v201.blocks.metervalues.MeterValuesServerDispatcher
import com.monta.library.ocpp.v201.blocks.provisioning.ProvisioningServerDispatcher
import com.monta.library.ocpp.v201.blocks.remotecontrol.RemoteControlServerDispatcher
import com.monta.library.ocpp.v201.blocks.reservation.ReservationServerDispatcher
import com.monta.library.ocpp.v201.blocks.security.SecurityServerDispatcher
import com.monta.library.ocpp.v201.blocks.smartcharging.SmartChargingServerDispatcher
import com.monta.library.ocpp.v201.blocks.tariffandcost.TariffAndCostServerDispatcher
import com.monta.library.ocpp.v201.blocks.transactions.TransactionsServerDispatcher

class OcppServerV201Builder : BaseOcppServerBuilder<OcppServerV201Builder>() {

    fun addAuthorization(listener: AuthorizationServerDispatcher.Listener): OcppServerV201Builder {
        profiles.add(AuthorizationServerDispatcher(listener))
        return this
    }

    fun addAvailability(listener: AvailabilityServerDispatcher.Listener): OcppServerV201Builder {
        profiles.add(AvailabilityServerDispatcher(listener))
        return this
    }

    fun addCertificateManagement(listener: CertificateManagementServerDispatcher.Listener): OcppServerV201Builder {
        profiles.add(CertificateManagementServerDispatcher(listener))
        return this
    }

    fun addDataTransfer(listener: DataTransferServerDispatcher.Listener): OcppServerV201Builder {
        profiles.add(DataTransferServerDispatcher(listener))
        return this
    }

    fun addDiagnostics(listener: DiagnosticsServerDispatcher.Listener): OcppServerV201Builder {
        profiles.add(DiagnosticsServerDispatcher(listener))
        return this
    }

    fun addDisplayMessage(listener: DisplayMessageServerDispatcher.Listener): OcppServerV201Builder {
        profiles.add(DisplayMessageServerDispatcher(listener))
        return this
    }

    fun addFirmwareManagement(listener: FirmwareManagementServerDispatcher.Listener): OcppServerV201Builder {
        profiles.add(FirmwareManagementServerDispatcher(listener))
        return this
    }

    fun addLocalAuthorizationListManagement(): OcppServerV201Builder {
        profiles.add(LocalAuthorizationListManagementServerDispatcher())
        return this
    }

    fun addMeterValues(listener: MeterValuesServerDispatcher.Listener): OcppServerV201Builder {
        profiles.add(MeterValuesServerDispatcher(listener))
        return this
    }

    fun addProvisioning(listener: ProvisioningServerDispatcher.Listener): OcppServerV201Builder {
        profiles.add(ProvisioningServerDispatcher(listener))
        return this
    }

    fun addRemoteControl(): OcppServerV201Builder {
        profiles.add(RemoteControlServerDispatcher())
        return this
    }

    fun addReservation(listener: ReservationServerDispatcher.Listener): OcppServerV201Builder {
        profiles.add(ReservationServerDispatcher(listener))
        return this
    }

    fun addSecurity(listener: SecurityServerDispatcher.Listener): OcppServerV201Builder {
        profiles.add(SecurityServerDispatcher(listener))
        return this
    }

    fun addSmartCharging(listener: SmartChargingServerDispatcher.Listener): OcppServerV201Builder {
        profiles.add(SmartChargingServerDispatcher(listener))
        return this
    }

    fun addTariffAndCost(): OcppServerV201Builder {
        profiles.add(TariffAndCostServerDispatcher())
        return this
    }

    fun addTransactions(listener: TransactionsServerDispatcher.Listener): OcppServerV201Builder {
        profiles.add(TransactionsServerDispatcher(listener))
        return this
    }

    fun build(): OcppServerV201 {
        if (sendMessage == null && ocppSessionRepository == null) {
            throw IllegalStateException("server must be configured in either 'gateway' or 'local mode'")
        }

        if (profiles.isEmpty()) {
            throw RuntimeException("OCPP feature profiles not setup")
        }

        return OcppServerV201(
            onConnect = requireNotNull(onConnect),
            onDisconnect = requireNotNull(onDisconnect),
            sendMessage = sendMessage,
            ocppSessionRepository = ocppSessionRepository,
            settings = requireNotNull(settings),
            profiles = profiles
        )
    }
}
