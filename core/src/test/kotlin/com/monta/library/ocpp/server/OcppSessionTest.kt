package com.monta.library.ocpp.server

import com.monta.library.ocpp.common.session.OcppSession
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.comparables.shouldBeEqualComparingTo
import io.kotest.matchers.comparables.shouldBeLessThan

class OcppSessionTest : StringSpec({

    "test compare to" {
        val sessionIndex1 = OcppSession.Index()
        val sessionIndex2 = OcppSession.Index()
        sessionIndex1 shouldBeEqualComparingTo sessionIndex1
        sessionIndex1 shouldBeLessThan sessionIndex2
        sessionIndex1.timestamp shouldBeLessThan sessionIndex2.timestamp
    }
})
