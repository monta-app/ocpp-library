package com.monta.library.ocpp.v201.common.chargingprofile

import com.monta.library.ocpp.v201.common.CustomData

data class SalesTariff(
    /**
     * SalesTariff identifier used to identify one sales tariff.
     * An SAID remains a unique identifier for one schedule throughout a charging session.
     **/
    val id: Long,
    val salesTariffEntry: List<Entry>,
    /**
     * A human readable title/short description of the sales tariff e.g. for HMI display purposes.
     **/
    val salesTariffDescription: String? = null,
    /**
     * Defines the overall number of distinct price levels used across all provided SalesTariff elements.
     **/
    val numEPriceLevels: Long? = null,
    val customData: CustomData? = null
) {

    init {
        if (salesTariffDescription != null) {
            require(salesTariffDescription.length <= 32) {
                "salesTariffDescription length > maximum 32 - ${salesTariffDescription.length}"
            }
        }
        require(salesTariffEntry.size in 1..1024) {
            "salesTariffEntry length not in range 1..1024 - ${salesTariffEntry.size}"
        }
    }

    data class Entry(
        val relativeTimeInterval: RelativeTimeInterval,
        /**
         * Defines the price level of this SalesTariffEntry (referring to NumEPriceLevels).
         * Small values for the EPriceLevel represent a cheaper TariffEntry.
         * Large values for the EPriceLevel represent a more expensive TariffEntry.
         **/
        val ePriceLevel: Long? = null,
        val consumptionCost: List<ConsumptionCost>? = null,
        val customData: CustomData? = null
    ) {

        init {
            if (ePriceLevel != null) {
                require(ePriceLevel >= 0L) {
                    "ePriceLevel < minimum 0 - $ePriceLevel"
                }
            }
            if (consumptionCost != null) {
                require(consumptionCost.size in 1..3) {
                    "consumptionCost length not in range 1..3 - ${consumptionCost.size}"
                }
            }
        }
    }

    data class RelativeTimeInterval(
        /**
         * Start of the interval, in seconds from NOW.
         **/
        val start: Long,
        /**
         * Duration of the interval, in seconds.
         **/
        val duration: Long? = null,
        val customData: CustomData? = null
    )
}
