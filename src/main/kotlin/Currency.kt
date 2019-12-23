package com.euapps.kcurrencies

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import java.math.RoundingMode

enum class CurrencySymbol {
    EUR, CAD, HKD, ISK, PHP, DKK, HUF, CZK, AUD, RON, SEK,
    IDR, INR, BRL, RUB, HRK, JPY, THB, CHF, SGD, PLN, BGN,
    TRY, CNY, NOK, NZD, ZAR, USD, MXN, ILS, GBP, KRW, MYR
}

open class Currency(val amount: Amount, val symbol: CurrencySymbol) {

    companion object {
        var scale: Int = 2
        var roundingMode: RoundingMode = RoundingMode.HALF_EVEN
        var exchange: (base: CurrencySymbol, currency: CurrencySymbol) -> BigDecimal? = ::getRate
    }

    suspend fun transform(
        from: Currency,
        to: CurrencySymbol,
        rate: (base: CurrencySymbol, currency: CurrencySymbol) -> BigDecimal? = exchange
    ): Currency? {
        if (from.symbol == to) return from
        return withContext(Dispatchers.IO) {
            rate(from.symbol, to)?.let {
                Currency((it * from.amount.amount).amount, to)
            }
        }
    }

    override fun equals(other: Any?) =
        other is Currency && symbol == other.symbol && amount.amount == other.amount.amount

    override fun toString() = "${amount.amount.setScale(scale, roundingMode)} ${symbol.name}"

    override fun hashCode() = amount.hashCode() + symbol.hashCode()
}

data class Amount(val amount: BigDecimal)

val Number.amount: Amount
    get() = Amount(BigDecimal(this.toDouble()))