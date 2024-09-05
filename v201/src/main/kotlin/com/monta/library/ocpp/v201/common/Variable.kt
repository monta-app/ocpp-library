package com.monta.library.ocpp.v201.common

data class Variable(
    /** Name of the variable. Name should be taken from the list of standardized variable names whenever possible. Case Insensitive. strongly advised to use Camel Case. */
    val name: String,
    /** Name of instance in case the variable exists as multiple instances. Case Insensitive. strongly advised to use Camel Case. */
    val instance: String? = null,
    val customData: CustomData? = null
) {

    init {
        require(name.length <= 50) {
            "name length > maximum 50 - ${name.length}"
        }
        if (instance != null) {
            require(instance.length <= 50) {
                "instance length > maximum 50 - ${instance.length}"
            }
        }
    }
}
