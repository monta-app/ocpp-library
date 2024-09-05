package com.monta.library.ocpp.v201.common

import java.time.ZonedDateTime

data class IdTokenInfo(
    val status: AuthorizationStatus,
    /** ID_ Token. Expiry. Date_ Time
     urn:x-oca:ocpp:uid:1:569373
     Date and Time after which the token must be considered invalid. */
    val cacheExpiryDateTime: ZonedDateTime? = null,
    /** Priority from a business point of view. Default priority is 0, The range is from -9 to 9. Higher values indicate a higher priority. The chargingPriority in "transactioneventresponse,TransactionEventResponse" overrules this one. */
    val chargingPriority: Long? = null,
    /** ID_ Token. Language1. Language_ Code
     urn:x-oca:ocpp:uid:1:569374
     Preferred user interface language of identifier user. Contains a language code as defined in "ref-RFC5646,[RFC5646]". */
    val language1: String? = null,
    /** Only used when the IdToken is only valid for one or more specific EVSEs, not for the entire Charging Station. */
    val evseId: List<Long>? = null,
    val groupIdToken: IdToken? = null,
    /** ID_ Token. Language2. Language_ Code
     urn:x-oca:ocpp:uid:1:569375
     Second preferred user interface language of identifier user. Don’t use when language1 is omitted, has to be different from language1. Contains a language code as defined in "ref-RFC5646,[RFC5646]". */
    val language2: String? = null,
    val personalMessage: MessageContent? = null,
    val customData: CustomData? = null
) {
    init {
        if (language1 != null) {
            require(language1.length <= 8) {
                "language1 length > maximum 8 - ${language1.length}"
            }
        }
        if (evseId != null) {
            require(evseId.isNotEmpty()) {
                "evseId length < minimum 1 - ${evseId.size}"
            }
        }
        if (language2 != null) {
            require(language2.length <= 8) {
                "language2 length > maximum 8 - ${language2.length}"
            }
        }
    }
}
