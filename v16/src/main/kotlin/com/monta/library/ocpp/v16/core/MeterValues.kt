package com.monta.library.ocpp.v16.core

import com.monta.library.ocpp.common.profile.Feature
import com.monta.library.ocpp.common.profile.OcppConfirmation
import com.monta.library.ocpp.common.profile.OcppRequest
import com.monta.library.ocpp.v16.SampledValue
import java.time.ZonedDateTime

object MeterValuesFeature : Feature {
    override val name: String = "MeterValues"
    override val requestType: Class<out OcppRequest> = MeterValuesRequest::class.java
    override val confirmationType: Class<out OcppConfirmation> = MeterValuesConfirmation::class.java
}

/**
 * Collection of one or more sampled values in [MeterValuesRequest]
 * All sampled values in a MeterValue are sampled at the same point in time.
 */
data class MeterValue(
    /**
     * Required.
     *
     * BUT - Some charge points has bugs and sends messages without this sometimes.
     * So have to be declared nullable to be able to deserialize messages from these.
     *
     * Timestamp for measured value(s).
     **/
    val timestamp: ZonedDateTime?,
    /**
     * Required
     *
     * One or more measured values
     **/
    val sampledValue: List<SampledValue>
)

/**
 * This contains the field definition of the [MeterValuesRequest] PDU sent by the Charge Point to the Central System.
 */
data class MeterValuesRequest(
    /**
     * Required
     *
     * This contains a number (>0) designating a connector of the Charge Point.
     * ‘0’ (zero) is used to designate the main PowerMeter.
     **/
    val connectorId: Int,
    /**
     * Optional
     *
     * The transaction to which these meter samples are related.
     **/
    val transactionId: Int? = null,
    /**
     * Required
     *
     * The sampled meter values with timestamps.
     **/
    val meterValue: List<MeterValue>
) : OcppRequest

/**
 * This contains the field definition of the [MeterValuesConfirmation] PDU sent by the Central System
 * to the Charge Point in response to a [MeterValuesRequest] PDU.
 * No fields are defined.
 */
object MeterValuesConfirmation : OcppConfirmation
