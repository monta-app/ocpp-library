package com.monta.library.ocpp.profiles.serialization

import com.monta.library.ocpp.TestUtils
import com.monta.library.ocpp.common.serialization.Message
import com.monta.library.ocpp.common.serialization.MessageSerializer
import com.monta.library.ocpp.common.serialization.ParsingResult
import com.monta.library.ocpp.common.serialization.SerializationMode
import com.monta.library.ocpp.v16.core.StatusNotificationRequest
import com.monta.library.ocpp.v16.error.OcppErrorResponderV16
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class StatusNotificationSerializationTest : StringSpec({
    val messageSerializer = MessageSerializer(SerializationMode.OCPP_1_6, OcppErrorResponderV16)

    "parse a valid status notification request" {
        val jsonString = TestUtils.getFileAsString("status_notification/req.json")
        val parsingResult = messageSerializer.parse(jsonString)

        parsingResult.shouldBeInstanceOf<ParsingResult.Success<Message.Request>>()
        val ocppMessage = parsingResult.value
        ocppMessage.shouldBeInstanceOf<Message.Request>()
        ocppMessage.uniqueId shouldBe "78ee4811-a044-417e-9c70-b51c2c296bf9"
        ocppMessage.action shouldBe "StatusNotification"
        ocppMessage.payload shouldBe TestUtils.toJsonNode("{\"connectorId\":1,\"errorCode\":\"NoError\",\"status\":\"Available\"}")
    }

    "parse a valid status notification request with a timestamp" {
        val jsonString = TestUtils.getFileAsString("status_notification/req-timestamp.json")
        val parsingResult = messageSerializer.parse(jsonString)

        parsingResult.shouldBeInstanceOf<ParsingResult.Success<Message.Request>>()
        val ocppMessage = parsingResult.value
        ocppMessage.shouldBeInstanceOf<Message.Request>()
        val expected = messageSerializer.deserializePayload(ocppMessage, StatusNotificationRequest::class.java)
        expected.shouldBeInstanceOf<ParsingResult.Success<StatusNotificationRequest>>()
        ocppMessage.uniqueId shouldBe "2024120922534800000049"
        ocppMessage.action shouldBe "StatusNotification"
        ocppMessage.payload shouldBe TestUtils.toJsonNode("{\"connectorId\":1,\"status\":\"Available\",\"errorCode\":\"NoError\",\"info\":\"eCp_12V\",\"timestamp\":\"2024-12-09T22:53:49.000Z\"}")
    }

    "parse a valid status notification request with a timestamp without a timezone should fail" {
        val jsonString = TestUtils.getFileAsString("status_notification/req-timestamp-no-timezone.json")
        val parsingResult = messageSerializer.parse(jsonString)

        parsingResult.shouldBeInstanceOf<ParsingResult.Success<Message.Request>>()
        val ocppMessage = parsingResult.value
        ocppMessage.shouldBeInstanceOf<Message.Request>()
        val expected = messageSerializer.deserializePayload(ocppMessage, StatusNotificationRequest::class.java)
        expected.shouldBeInstanceOf<ParsingResult.Failure<StatusNotificationRequest>>()
        ocppMessage.uniqueId shouldBe "2024120922534800000049"
        ocppMessage.action shouldBe "StatusNotification"
        ocppMessage.payload shouldBe TestUtils.toJsonNode("{\"connectorId\":1,\"status\":\"Available\",\"errorCode\":\"NoError\",\"info\":\"eCp_12V\",\"timestamp\":\"2024-12-09T22:53:49.000\"}")
    }

    "parse a valid status notification response" {
        val jsonString = TestUtils.getFileAsString("status_notification/res.json")
        val parsingResult = messageSerializer.parse(jsonString)

        parsingResult.shouldBeInstanceOf<ParsingResult.Success<Message.Response>>()
        val ocppMessage = parsingResult.value
        ocppMessage.shouldBeInstanceOf<Message.Response>()
        ocppMessage.uniqueId shouldBe "78ee4811-a044-417e-9c70-b51c2c296bf9"
        ocppMessage.payload shouldBe TestUtils.toJsonNode("{}")
    }

    "parse a valid status notification error" {
        val jsonString = TestUtils.getFileAsString("status_notification/err.json")
        val parsingResult = messageSerializer.parse(jsonString)

        parsingResult.shouldBeInstanceOf<ParsingResult.Success<Message.Error>>()
        val ocppMessage = parsingResult.value
        ocppMessage.shouldBeInstanceOf<Message.Error>()
        ocppMessage.uniqueId shouldBe "78ee4811-a044-417e-9c70-b51c2c296bf9"
        ocppMessage.errorCode shouldBe "This is a code"
        ocppMessage.errorDescription shouldBe "This is a description"
        ocppMessage.errorDetails shouldBe "{}"
    }
})
