package com.euapps.kcurrencies

import java.math.BigDecimal

/**
 * BigDecimal wrapper representing Amount
 */
data class Amount(val amount: BigDecimal)

/**
 * Number extension to get an Amount instance from any number
 */
val Number.amount: Amount
    get() = Amount(
        when (this) {
            is Double -> this.toBigDecimal()
            is Float -> this.toBigDecimal()
            is Int -> this.toBigDecimal()
            is Long -> this.toBigDecimal()
            else -> this.toDouble().toBigDecimal()
        }
    )