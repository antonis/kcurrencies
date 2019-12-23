package com.euapps.kcurrencies

import java.math.BigDecimal

suspend infix fun Currency?.to(to: Currency): Currency? = this?.transform(this, to.symbol)

suspend operator fun Currency?.plus(other: Currency?) =
    if (this == null || other == null) null else transform(other, this.symbol)?.let {
        Currency((this.amount.amount + it.amount.amount).amount, this.symbol)
    }

suspend operator fun Currency?.minus(other: Currency?) =
    if (this == null || other == null) null else transform(other, this.symbol)?.let {
        Currency((this.amount.amount - it.amount.amount).amount, this.symbol)
    }

operator fun Currency?.times(mul: Number?) =
    if (this == null || mul == null) null else {
        Currency((this.amount.amount * mul.amount.amount).amount, this.symbol)
    }

operator fun Currency?.div(div: Number?) = if (this == null || div == null) null else Currency(
    this.amount.amount.divide(BigDecimal(div.toDouble()), Currency.scale, Currency.roundingMode).amount,
    this.symbol
)

operator fun Currency?.unaryMinus() =
    if (this == null) null else Currency(this.amount.amount.negate().amount, this.symbol)

operator fun Currency?.unaryPlus() = this