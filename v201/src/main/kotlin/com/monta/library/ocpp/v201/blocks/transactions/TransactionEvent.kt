package com.monta.library.ocpp.v201.blocks.transactions

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v201.common.CustomData
import com.monta.library.ocpp.v201.common.EVSE
import com.monta.library.ocpp.v201.common.IdToken
import com.monta.library.ocpp.v201.common.IdTokenInfo
import com.monta.library.ocpp.v201.common.MessageContent
import com.monta.library.ocpp.v201.common.MeterValue
import java.math.BigDecimal
import java.time.ZonedDateTime

object TransactionEventFeature : Feature {
    override val name: String = "TransactionEvent"
    override val requestType: Class<out OcppRequest> = TransactionEventRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = TransactionEventResponse::class.java
}

data class TransactionEventRequest(
    val eventType: EventType,
    val transactionInfo: Transaction,
    /** The date and time at which this transaction event occurred. */
    val timestamp: ZonedDateTime,
    val triggerReason: TriggerReason,
    /**
     * Incremental sequence number, helps with determining if all messages of a transaction have been received.
     **/
    val seqNo: Long,
    val meterValue: List<MeterValue>? = null,
    /**
     * Indication that this transaction event happened when the Charging Station was offline.
     * Default = false, meaning: the event occurred when the Charging Station was online.
     **/
    val offline: Boolean = false,
    /**
     * If the Charging Station is able to report the number of phases used, then it SHALL provide it.
     * When omitted the CSMS may be able to determine the number of phases used via device management.
     **/
    val numberOfPhasesUsed: Long? = null,
    /**
     * The maximum current of the connected cable in Ampere (A).
     **/
    val cableMaxCurrent: Long? = null,
    /**
     * This contains the Id of the reservation that terminates as a result of this transaction.
     **/
    val reservationId: Long? = null,
    val evse: EVSE? = null,
    val idToken: IdToken? = null,
    val customData: CustomData? = null
) : OcppRequest {

    init {
        if (meterValue != null) {
            require(meterValue.isNotEmpty()) {
                "meterValue length < minimum 1 - ${meterValue.size}"
            }
        }
    }

    data class Transaction(
        /** This contains the Id of the transaction. */
        val transactionId: String,
        val chargingState: ChargingState? = null,
        /**
         * Contains the total time that energy flowed from EVSE to EV during the transaction (in seconds).
         * Note that timeSpentCharging is smaller or equal to the duration of the transaction.
         **/
        val timeSpentCharging: Long? = null,
        val stoppedReason: StoppedReason? = null,
        /**
         * The ID given to remote start request ("requeststarttransactionrequest, RequestStartTransactionRequest".
         * This enables to CSMS to match the started transaction to the given start request.
         **/
        val remoteStartId: Long? = null,
        val customData: CustomData? = null
    ) {

        init {
            require(transactionId.length <= 36) {
                "transactionId length > maximum 36 - ${transactionId.length}"
            }
        }

        enum class ChargingState {
            Charging,
            EVConnected,
            SuspendedEV,
            SuspendedEVSE,
            Idle
        }

        enum class StoppedReason {
            DeAuthorized,
            EmergencyStop,
            EnergyLimitReached,
            EVDisconnected,
            GroundFault,
            ImmediateReset,
            Local,
            LocalOutOfCredit,
            MasterPass,
            Other,
            OvercurrentFault,
            PowerLoss,
            PowerQuality,
            Reboot,
            Remote,
            SOCLimitReached,
            StoppedByEV,
            TimeLimitReached,
            Timeout
        }
    }

    enum class EventType {
        Ended,
        Started,
        Updated
    }

    enum class TriggerReason {
        Authorized,
        CablePluggedIn,
        ChargingRateChanged,
        ChargingStateChanged,
        Deauthorized,
        EnergyLimitReached,
        EVCommunicationLost,
        EVConnectTimeout,
        MeterValueClock,
        MeterValuePeriodic,
        TimeLimitReached,
        Trigger,
        UnlockCommand,
        StopAuthorized,
        EVDeparted,
        EVDetected,
        RemoteStop,
        RemoteStart,
        AbnormalCondition,
        SignedDataReceived,
        ResetCommand
    }
}

data class TransactionEventResponse(
    /**
     * SHALL only be sent when charging has ended.
     * Final total cost of this transaction, including taxes.
     * In the currency configured with the Configuration Variable: "configkey-currency,`Currency`".
     * When omitted, the transaction was NOT free.
     * To indicate a free transaction, the CSMS SHALL send 0.00.
     */
    val totalCost: BigDecimal? = null,
    /**
     * Priority from a business point of view.
     * Default priority is 0, The range is from -9 to 9.
     * Higher values indicate a higher priority.
     * The chargingPriority in [TransactionEventResponse] is temporarily, so it may not be set in the [IdTokenInfo] afterwards.
     * Also the chargingPriority in [TransactionEventResponse] overrules the one in [IdTokenInfo].
     **/
    val chargingPriority: Long? = null,
    val idTokenInfo: IdTokenInfo? = null,
    val updatedPersonalMessage: MessageContent? = null,
    val customData: CustomData? = null
) : OcppConfirmation
