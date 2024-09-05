package com.monta.library.ocpp.v201.blocks

import com.monta.library.ocpp.common.serialization.Message
import com.monta.library.ocpp.common.serialization.MessageSerializer
import com.monta.library.ocpp.common.serialization.SerializationMode
import com.monta.library.ocpp.v201.blocks.provisioning.BootNotificationResponse
import com.monta.library.ocpp.v201.error.OcppErrorResponderV201
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class TimestampSerializationTest : StringSpec({
    val messageSerializer = MessageSerializer(SerializationMode.OCPP_2, OcppErrorResponderV201)

    "format currentTime with millis" {
        val now = ZonedDateTime.now(ZoneId.of("UTC"))
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")
        val expected = now.format(formatter)

        val bootNotificationConfirmation = Message.Response(
            "123",
            messageSerializer.toPayload(
                BootNotificationResponse(
                    currentTime = now,
                    interval = 240,
                    status = BootNotificationResponse.Status.Accepted
                )
            )
        )

        bootNotificationConfirmation.toJsonString(messageSerializer) shouldBe "[3,\"123\",{\"currentTime\":\"$expected\",\"interval\":240,\"status\":\"Accepted\"}]"
    }

    "include trailing zeros" {
        val now = ZonedDateTime.now(ZoneId.of("UTC")).truncatedTo(ChronoUnit.SECONDS)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")
        val expected = now.format(formatter)
        val bootNotificationConfirmation = Message.Response(
            "123",
            messageSerializer.toPayload(
                BootNotificationResponse(
                    currentTime = now,
                    interval = 240,
                    status = BootNotificationResponse.Status.Accepted
                )
            )
        )

        bootNotificationConfirmation.toJsonString(messageSerializer) shouldBe "[3,\"123\",{\"currentTime\":\"$expected\",\"interval\":240,\"status\":\"Accepted\"}]"
    }
})
