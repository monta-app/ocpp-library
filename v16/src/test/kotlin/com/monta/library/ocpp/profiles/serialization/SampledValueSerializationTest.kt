package com.monta.library.ocpp.profiles.serialization

import com.monta.library.ocpp.common.serialization.Message
import com.monta.library.ocpp.common.serialization.MessageSerializer
import com.monta.library.ocpp.common.serialization.SerializationMode
import com.monta.library.ocpp.v16.SampledValue
import com.monta.library.ocpp.v16.core.MeterValue
import com.monta.library.ocpp.v16.core.MeterValuesRequest
import com.monta.library.ocpp.v16.error.OcppErrorResponderV16
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.string.shouldNotContain
import java.time.ZonedDateTime

class SampledValueSerializationTest : StringSpec({
    "serialized SampledValue should not include internal enums" {
        val messageSerializer = MessageSerializer(SerializationMode.OCPP_1_6, OcppErrorResponderV16)
        val value = SampledValue("31")
        val meterValues = Message.Request(
            "123",
            "MeterValues",
            messageSerializer.toPayload(
                MeterValuesRequest(1, 1, listOf(MeterValue(ZonedDateTime.now(), listOf(value))))
            )
        )
        val serialized = meterValues.toJsonString(messageSerializer)
        serialized shouldNotContain Regex("contextType|formatType|measurandType|locationType|unitType")
    }
})
