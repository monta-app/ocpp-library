package com.monta.library.ocpp.common.serialization

import com.fasterxml.jackson.databind.JsonNode

/**
 * I made up a word *_*
 */
interface Payloadable {
    val payload: JsonNode
}
