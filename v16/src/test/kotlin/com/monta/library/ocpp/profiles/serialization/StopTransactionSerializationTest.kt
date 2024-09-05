package com.monta.library.ocpp.profiles.serialization

import com.monta.library.ocpp.TestUtils
import com.monta.library.ocpp.common.serialization.Message
import com.monta.library.ocpp.common.serialization.MessageSerializer
import com.monta.library.ocpp.common.serialization.ParsingResult
import com.monta.library.ocpp.common.serialization.SerializationMode
import com.monta.library.ocpp.v16.core.StopTransactionRequest
import com.monta.library.ocpp.v16.error.OcppErrorResponderV16
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class StopTransactionSerializationTest : StringSpec({
    val messageSerializer = MessageSerializer(SerializationMode.OCPP_1_6, OcppErrorResponderV16)

    "parse a siemens stoptransaction with partly invalid transaction data (CPIBUG-61)" {
        val jsonString = TestUtils.getFileAsString("core/stoptransaction-siemens.json")
        val parsingResult = messageSerializer.parse(jsonString)

        parsingResult.shouldBeInstanceOf<ParsingResult.Success<Message.Request>>()
        val ocppMessage = parsingResult.value
        ocppMessage.shouldBeInstanceOf<Message.Request>()
        ocppMessage.uniqueId shouldBe "1700035413777"
        ocppMessage.action shouldBe "StopTransaction"

        val stopTransactionParsingResult = messageSerializer.deserializePayload(ocppMessage, StopTransactionRequest::class.java)
        stopTransactionParsingResult.shouldBeInstanceOf<ParsingResult.Success<StopTransactionRequest>>()
        val stopTransaction = stopTransactionParsingResult.value

        val transactionData = stopTransaction.transactionData.shouldNotBeNull()
        transactionData.size shouldBe 2
        transactionData.forEach {
            it.timestamp.shouldBeNull()
            it.sampledValue.shouldBeEmpty()
        }
    }
})
