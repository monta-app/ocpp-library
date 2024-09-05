package com.monta.library.ocpp.v16

import java.time.ZonedDateTime

enum class AuthorizationStatus {
    /**
     * Identifier is allowed for charging.
     */
    Accepted,

    /**
     * Identifier has been blocked. Not allowed for charging.
     */
    Blocked,

    /**
     * Identifier has expired. Not allowed for charging.
     */
    Expired,

    /**
     * Identifier is unknown. Not allowed for charging.
     */
    Invalid,

    /**
     * Identifier is already involved in another transaction and multiple transactions are not allowed.
     * (Only relevant for a StartTransaction.req.)
     */
    ConcurrentTx
}

data class IdTagInfo(
    /**
     * Required
     *
     * This contains whether the idTag has been accepted or not by the Central System.
     */
    val status: AuthorizationStatus,
    /**
     * Optional
     *
     * This contains the date at which idTag should be removed from the Authorization Cache.
     */
    val expiryDate: ZonedDateTime? = null,
    /**
     * Optional
     *
     * This contains the parent-identifier.
     **/
    val parentIdTag: String? = null
)
