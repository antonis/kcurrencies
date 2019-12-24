# kcurrencies
[![Kotlin](https://img.shields.io/badge/Kotlin-1.3.61-blue.svg)](https://kotlinlang.org)

A simple currencies DSL implemented in Kotlin. It provides operations and exchange between currencies.
By default it fetches exchange rates using the European Central Bank API https://exchangeratesapi.io but the user may add any other source

## Installation

Gradle

```groovy
TODO
```

Maven

```xml
TODO
```

## Supported Currencies
At this point the following 33 currencies are supported as defined by their ISO 4217 code:

EUR, CAD, HKD, ISK, PHP, DKK, HUF, CZK, AUD, RON, SEK, IDR, INR, BRL, RUB, HRK, JPY, THB, CHF, SGD, PLN, BGN, TRY, CNY, NOK, NZD, ZAR, USD, MXN, ILS, GBP, KRW, MYR

## Usage

You may define a Currency instance using any number followed by a dot(.) and the ISO 4217 currency code
```kotlin
100.EUR
988.21.USD
1_238.91.CHF
```

You can convert between two currencies by using the to keyword followed by the ISO 4217 currency code
```kotlin
100.EUR to MXN
988.21.USD to EUR
1_238.91.CHF to USD
```
Note that any conversion is a suspend function since it performs an API call and should be run in a proper coroutine scope.

You may perform addition, subtraction multiplication and division of currencies using numeric operators.
```kotlin
100.EUR + 10.EUR
1000.EUR - 10.EUR
100.USD * 10
1000.CHF / 2
```

Operations between two different currencies convert the second operand to the currency of the first and perform the operation.
```kotlin
1000.EUR + 500.USD
1000.USD - 10.EUR
```

## Configuration

By default the number of digits to the right of the decimal point (scale) is two(2). This may change statically like bellow:
```kotlin
Currency.scale = 3
```

By default the rounding mode is [**Round half to even** (known as bankers' rounding)](https://en.wikipedia.org/wiki/Rounding#Round_half_to_even). This can be changed like bellow
```kotlin
Currency.roundingMode = RoundingMode.DOWN
```

By default the currency rates are fetched from the European Central Bank API https://exchangeratesapi.io. The user may change this by providing a custom function. For example the code bellow returns a rate 2 for any provided currencies:
```kotlin
Currency.exchange = { base: CurrencyCode, currency: CurrencyCode -> BigDecimal(2.0) }
```