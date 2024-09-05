package com.monta.library.ocpp.v201.client

import com.monta.library.ocpp.client.BaseOcppClientBuilder
import com.monta.library.ocpp.v201.blocks.availability.AvailabilityClientDispatcher
import com.monta.library.ocpp.v201.blocks.certificatemanagement.CertificateManagementClientDispatcher
import com.monta.library.ocpp.v201.blocks.datatransfer.DataTransferClientDispatcher
import com.monta.library.ocpp.v201.blocks.diagnostics.DiagnosticsClientDispatcher
import com.monta.library.ocpp.v201.blocks.displaymessage.DisplayMessageClientDispatcher
import com.monta.library.ocpp.v201.blocks.firmwaremanagement.FirmwareManagementClientDispatcher
import com.monta.library.ocpp.v201.blocks.localauthorizationlistmanagement.LocalAuthorizationListManagementClientDispatcher
import com.monta.library.ocpp.v201.blocks.remotecontrol.RemoteControlClientDispatcher
import com.monta.library.ocpp.v201.blocks.reservation.ReservationClientDispatcher
import com.monta.library.ocpp.v201.blocks.smartcharging.SmartChargingClientDispatcher
import com.monta.library.ocpp.v201.blocks.tariffandcost.TariffAndCostClientDispatcher
import com.monta.library.ocpp.v201.blocks.transactions.TransactionsClientDispatcher

class OcppClientV201Builder : BaseOcppClientBuilder<OcppClientV201Builder>() {

    fun addAvailability(
        listener: AvailabilityClientDispatcher.Listener
    ): OcppClientV201Builder {
        profiles.add(AvailabilityClientDispatcher(listener))
        return this
    }

    fun addCertificateManagement(
        listener: CertificateManagementClientDispatcher.Listener
    ): OcppClientV201Builder {
        profiles.add(CertificateManagementClientDispatcher(listener))
        return this
    }

    fun addDataTransfer(
        listener: DataTransferClientDispatcher.Listener
    ): OcppClientV201Builder {
        profiles.add(DataTransferClientDispatcher(listener))
        return this
    }

    fun addDiagnostics(
        listener: DiagnosticsClientDispatcher.Listener
    ): OcppClientV201Builder {
        profiles.add(DiagnosticsClientDispatcher(listener))
        return this
    }

    fun addDisplayMessage(
        listener: DisplayMessageClientDispatcher.Listener
    ): OcppClientV201Builder {
        profiles.add(DisplayMessageClientDispatcher(listener))
        return this
    }

    fun addFirmwareManagement(
        listener: FirmwareManagementClientDispatcher.Listener
    ): OcppClientV201Builder {
        profiles.add(FirmwareManagementClientDispatcher(listener))
        return this
    }

    fun addLocalAuthListManagement(
        listener: LocalAuthorizationListManagementClientDispatcher.Listener
    ): OcppClientV201Builder {
        profiles.add(LocalAuthorizationListManagementClientDispatcher(listener))
        return this
    }

    fun addRemoteControl(
        listener: RemoteControlClientDispatcher.Listener
    ): OcppClientV201Builder {
        profiles.add(RemoteControlClientDispatcher(listener))
        return this
    }

    fun addReservation(
        listener: ReservationClientDispatcher.Listener
    ): OcppClientV201Builder {
        profiles.add(ReservationClientDispatcher(listener))
        return this
    }

    fun addSmartCharging(
        listener: SmartChargingClientDispatcher.Listener
    ): OcppClientV201Builder {
        profiles.add(SmartChargingClientDispatcher(listener))
        return this
    }

    fun addTariffAndCost(
        listener: TariffAndCostClientDispatcher.Listener
    ): OcppClientV201Builder {
        profiles.add(TariffAndCostClientDispatcher(listener))
        return this
    }

    fun addTransactions(
        listener: TransactionsClientDispatcher.Listener
    ): OcppClientV201Builder {
        profiles.add(TransactionsClientDispatcher(listener))
        return this
    }

    fun build(): OcppClientV201 {
        return OcppClientV201(
            onConnect = requireNotNull(onConnect),
            onDisconnect = requireNotNull(onDisconnect),
            ocppSessionRepository = requireNotNull(ocppSessionRepository),
            settings = requireNotNull(settings),
            profiles = profiles,
            sendHook = sendHook
        )
    }
}
