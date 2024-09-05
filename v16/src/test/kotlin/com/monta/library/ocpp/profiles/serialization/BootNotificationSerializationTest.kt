package com.monta.library.ocpp.profiles.serialization

import com.monta.library.ocpp.common.serialization.Message
import com.monta.library.ocpp.common.serialization.MessageSerializer
import com.monta.library.ocpp.common.serialization.SerializationMode
import com.monta.library.ocpp.v16.core.BootNotificationConfirmation
import com.monta.library.ocpp.v16.core.RegistrationStatus
import com.monta.library.ocpp.v16.error.OcppErrorResponderV16
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class BootNotificationSerializationTest : StringSpec({
    val messageSerializer = MessageSerializer(SerializationMode.OCPP_1_6, OcppErrorResponderV16)

    "format currentTime without millis" {
        val now = ZonedDateTime.now(ZoneId.of("UTC"))
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX")
        val expected = now.format(formatter)

        val bootNotificationConfirmation = Message.Response(
            "123",
            messageSerializer.toPayload(
                BootNotificationConfirmation(
                    currentTime = now,
                    interval = 240,
                    status = RegistrationStatus.Accepted
                )
            )
        )

        bootNotificationConfirmation.toJsonString(messageSerializer) shouldBe "[3,\"123\",{\"currentTime\":\"$expected\",\"interval\":240,\"status\":\"Accepted\"}]"
    }
})
