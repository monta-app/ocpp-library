package com.monta.library.ocpp.v201.common

data class MessageContent(
    val format: MessageFormat,
    /** Message language identifier. Contains a language code as defined in "RFC5646". */
    val language: String? = null,
    /** Message contents. */
    val content: String,
    val customData: CustomData? = null
) {

    init {
        if (language != null) {
            require(language.length <= 8) {
                "language length > maximum 8 - ${language.length}"
            }
        }
        require(content.length <= 512) {
            "content length > maximum 512 - ${content.length}"
        }
    }
}
