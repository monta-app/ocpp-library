package com.monta.library.ocpp.profiles.serialization

import com.monta.library.ocpp.TestUtils
import com.monta.library.ocpp.common.serialization.Message
import com.monta.library.ocpp.common.serialization.MessageSerializer
import com.monta.library.ocpp.common.serialization.ParsingResult
import com.monta.library.ocpp.common.serialization.SerializationMode
import com.monta.library.ocpp.v16.error.OcppErrorResponderV16
import com.monta.library.ocpp.v16.firmware.UpdateFirmwareRequest
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class FirmwareSerializationTest : StringSpec({
    val messageSerializer = MessageSerializer(SerializationMode.OCPP_1_6, OcppErrorResponderV16)

    "parse a valid update firmware request" {
        val jsonString = TestUtils.getFileAsString("firmware/req.json")
        val parsingResult = messageSerializer.parse(jsonString)

        parsingResult.shouldBeInstanceOf<ParsingResult.Success<Message.Request>>()
        val ocppMessage = parsingResult.value
        ocppMessage.shouldBeInstanceOf<Message.Request>()
        ocppMessage.uniqueId shouldBe "78ee4811-a044-417e-9c70-b51c2c296bf9"
        ocppMessage.action shouldBe "UpdateFirmware"
        ocppMessage.payload shouldBe TestUtils.toJsonNode("{\"location\":\"http://localhost\",\"retries\":1,\"retrieveDate\":\"2022-09-14T09:49:04Z\",\"retryInterval\":1}")
    }

    "parse a valid update firmware response" {
        val jsonString = TestUtils.getFileAsString("firmware/res.json")
        val parsingResult = messageSerializer.parse(jsonString)

        parsingResult.shouldBeInstanceOf<ParsingResult.Success<Message.Response>>()
        val ocppMessage = parsingResult.value
        ocppMessage.shouldBeInstanceOf<Message.Response>()
        ocppMessage.uniqueId shouldBe "78ee4811-a044-417e-9c70-b51c2c296bf9"
        ocppMessage.payload shouldBe TestUtils.toJsonNode("{}")
    }

    "format currentTime without millis" {
        val now = ZonedDateTime.now(ZoneId.of("UTC"))
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX")
        val expected = now.format(formatter)

        val firmwareRequest = Message.Response(
            "123",
            messageSerializer.toPayload(
                UpdateFirmwareRequest(
                    location = "http://localhost",
                    retrieveDate = now
                )
            )
        )

        firmwareRequest.toJsonString(messageSerializer) shouldBe "[3,\"123\",{\"location\":\"http://localhost\",\"retrieveDate\":\"$expected\"}]"
    }
})
