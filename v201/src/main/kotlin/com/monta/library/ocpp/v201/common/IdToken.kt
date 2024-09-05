package com.monta.library.ocpp.v201.common

data class IdToken(
    val additionalInfo: List<AdditionalInfo>? = null,
    /** IdToken is case-insensitive. Might hold the hidden id of an RFID tag, but can for example also contain a UUID. */
    val idToken: String,
    val type: Type,
    val customData: CustomData? = null
) {

    init {
        if (additionalInfo != null) {
            require(additionalInfo.isNotEmpty()) {
                "additionalInfo length < minimum 1 - ${additionalInfo.size}"
            }
        }
        require(idToken.length <= 36) {
            "idToken length > maximum 36 - ${idToken.length}"
        }
    }

    @Suppress("EnumEntryName")
    enum class Type {
        // A centrally, in the CSMS (or other server) generated id (for example used for a remotely started transaction that is activated by SMS).
        // No format defined, might be a UUID.
        Central,

        // Electro-mobility account id as defined in ISO 15118
        eMAID,

        // ISO 14443 UID of RFID card.
        // It is represented as an array of 4 or 7 bytes in hexadecimal representation.
        ISO14443,

        // ISO 15693 UID of RFID card.
        // It is represented as an array of 8 bytes in hexadecimal representation.
        ISO15693,

        // User use a private key-code to authorize a charging transaction.
        // For example: Pin-code.
        KeyCode,

        // A locally generated id (e.g. internal id created by the Charging Station).
        // No format defined, might be a UUID
        Local,
        MacAddress,

        // Transaction is started and no authorization possible.
        // Charging Station only has a start button or mechanical key etc. IdToken field SHALL be left empty.
        NoAuthorization
    }
}
