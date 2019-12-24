package com.euapps.kcurrencies

import com.google.gson.Gson
import com.google.gson.JsonObject
import java.math.BigDecimal

/**
 * Fetches exchange rate from the European Central Bank https://exchangeratesapi.io
 * @param base base currency
 * @param currency target currency
 * @return The exchange rate
 */
fun getRate(base: CurrencyCode, currency: CurrencyCode): BigDecimal? = try {
    if (base == currency) BigDecimal(1.0) else {
        val url = "https://api.exchangeratesapi.io/latest?base=$base&symbols=$currency"
        val json = java.net.URL(url).readText()
        val jsonObject = Gson().fromJson<JsonObject>(json, JsonObject::class.java)
        jsonObject["rates"].asJsonObject[currency.name].asBigDecimal
    }
} catch (e: Exception) {
    null
}