package com.monta.ocpp

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

object MontaSerialization {

    fun getDefaultMapper(
        serializeNulls: Boolean = false
    ): ObjectMapper {
        return withDefaults(
            objectMapper = ObjectMapper(),
            serializeNulls = serializeNulls
        )
    }

    fun withDefaults(
        objectMapper: ObjectMapper,
        serializeNulls: Boolean = false
    ): ObjectMapper {
        objectMapper.registerKotlinModule()
        objectMapper.registerModule(JavaTimeModule())
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        objectMapper.setSerializationInclusion(
            if (serializeNulls) {
                JsonInclude.Include.ALWAYS
            } else {
                JsonInclude.Include.NON_NULL
            }
        )
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        objectMapper.findAndRegisterModules()
        return objectMapper
    }
}
