package com.monta.library.ocpp.v16.core

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v16.IdTagInfo
import java.time.ZonedDateTime

object StartTransactionFeature : Feature {
    override val name: String = "StartTransaction"
    override val requestType: Class<out OcppRequest> = StartTransactionRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = StartTransactionConfirmation::class.java
}

/**
 * This section contains the field definition of the [StartTransactionRequest] PDU sent by the
 * Charge Point to the Central System.
 */
data class StartTransactionRequest(
    /**
     * TODO validate that connector is 0 or greater
     *
     * Required
     *
     * This identifies which connector of the Charge Point is used.
     **/
    val connectorId: Int,
    /**
     * String[20]
     *
     * Required
     *
     * This contains the identifier for which a transaction has to be started.
     **/
    val idTag: String,
    /**
     * Required
     *
     * This contains the meter value in Wh for the connector at start of the transaction.
     **/
    val meterStart: Int,
    /**
     * Optional
     *
     * This contains the id of the reservation that terminates as a result of this transaction.
     **/
    val reservationId: Int? = null,
    /**
     * Required
     *
     * This contains the date and time on which the transaction is started.
     **/
    val timestamp: ZonedDateTime
) : OcppRequest

/**
 * This contains the field definition of the[StartTransactionConfirmation] PDU sent by the Central System
 * to the Charge Point in response to a [StartTransactionRequest] PDU.
 */
data class StartTransactionConfirmation(
    /**
     * Required
     * This contains information about authorization status, expiry and parent id.
     **/
    val idTagInfo: IdTagInfo,
    /**
     * Required
     * This contains the transaction id supplied by the Central System.
     **/
    val transactionId: Int
) : OcppConfirmation
