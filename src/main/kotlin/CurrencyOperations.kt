package com.euapps.kcurrencies

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.math.BigDecimal

/**
 * Transforms a currency to the given a currency code
 * @param from base currency
 * @param to target currency code
 * @rate rate function that provides transformation rates between two currencies
 * @return the transformed currency
 */
suspend fun transform(
    from: Currency?,
    to: CurrencyCode,
    rate: (base: CurrencyCode, currency: CurrencyCode) -> BigDecimal? = Currency.exchange
): Currency? {
    if (from == null) return null
    if (from.code == to) return from
    return withContext(Dispatchers.IO) {
        rate(from.code, to)?.let {
            Currency((it * from.amount.amount).amount, to)
        }
    }
}

/**
 * Transforms a Currency to the currency passed as parameter
 * @param to the currency to transform to
 * @return the result of the transformation
 */
suspend infix fun Currency?.to(to: Currency): Currency? = transform(this, to.code)

/**
 * Adds a Currency instance ot the current object keeping the currency of the object
 * @param other augend
 * @return returns the result of the addition after transforming to the currency of the first operand if needed
 */
suspend operator fun Currency?.plus(other: Currency?) =
    if (this == null || other == null) null else transform(other, this.code)?.let {
        Currency((this.amount.amount + it.amount.amount).amount, this.code)
    }

/**
 * Subtracts a Currency instance from the current object keeping the currency of the object
 * @param other subtrahend
 * @return returns the result of the subtraction after transforming to the currency of the first operand if needed
 */
suspend operator fun Currency?.minus(other: Currency?) =
    if (this == null || other == null) null else transform(other, this.code)?.let {
        Currency((this.amount.amount - it.amount.amount).amount, this.code)
    }

/**
 * Multiplication of currency by number
 * @param mul multiplicand
 * @return the result of the multiplication
 */
operator fun Currency?.times(mul: Number?) =
    if (this == null || mul == null) null else {
        Currency((this.amount.amount * mul.amount.amount).amount, this.code)
    }

/**
 * Division of currency by number
 * @param div divisor
 * @return the result of the division
 */
operator fun Currency?.div(div: Number?) = if (this == null || div == null || div.toDouble() == 0.0) null else Currency(
    this.amount.amount.divide(BigDecimal(div.toDouble()), Currency.scale, Currency.roundingMode).amount,
    this.code
)

/**
 * Unary minus operator
 */
operator fun Currency?.unaryMinus() =
    if (this == null) null else Currency(this.amount.amount.negate().amount, this.code)

/**
 * Unary plus operator
 */
operator fun Currency?.unaryPlus() = this