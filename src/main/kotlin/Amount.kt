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
    get() = Amount(BigDecimal(this.toDouble()))