package com.euapps.kcurrencies

import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Currency object
 * @param amount the amount of the currency
 * @param code the ISO 4217 currency code
 */
open class Currency(val amount: Amount, val code: CurrencyCode) {

    companion object {
        /**
         * The number of digits to the right of the decimal point
         */
        var scale: Int = 2

        /**
         * The rounding algorithm to be used for an operation
         */
        var roundingMode: RoundingMode = RoundingMode.HALF_EVEN

        /**
         * A function that provides transformation rates between two currencies
         * By default it fetches exchange rate from the European Central Bank https://exchangeratesapi.io
         */
        var exchange: (base: CurrencyCode, currency: CurrencyCode) -> BigDecimal? = ::getRate
    }

    override fun equals(other: Any?) =
        other is Currency && code == other.code &&
                amount.amount.setScale(scale, roundingMode) == other.amount.amount.setScale(scale, roundingMode)

    override fun toString() = "${amount.amount.setScale(scale, roundingMode)} ${code.name}"

    override fun hashCode() = amount.amount.setScale(scale, roundingMode).hashCode() + code.hashCode()
}