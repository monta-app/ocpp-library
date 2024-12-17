package com.monta.library.ocpp.common.profile

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class OcppRequestTest : StringSpec({

    "action name for request classes following the convention of ending in 'Request'" {
        class TestRequest : OcppRequest
        TestRequest().actionName() shouldBe "Test"
    }

    "action name for request classes that does not follow the standard conventions" {
        class TestRequestWithSuffix : OcppRequest
        TestRequestWithSuffix().actionName() shouldBe "TestRequestWithSuffix"
    }

    "action name for request classes with simple name" {
        class Test : OcppRequest
        Test().actionName() shouldBe "Test"
    }

    "action name for request classes named 'Request'" {
        class Request : OcppRequest
        Request().actionName() shouldBe "Request"
    }
})
