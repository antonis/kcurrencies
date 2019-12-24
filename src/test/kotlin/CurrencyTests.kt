import com.euapps.kcurrencies.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.math.RoundingMode

class CurrencyTests {

    private fun rate(rate: Number) = { _: CurrencyCode, _: CurrencyCode -> BigDecimal(rate.toDouble()) }

    @Test
    fun `with rate 2,0 exchange 100 EUR for USD`() {
        Currency.exchange = rate(2)
        assertEquals(runBlocking { 100.EUR to USD }, 200.0.USD)
    }

    @Test
    fun `add 100 EUR to 10 EUR`() {
        assertEquals(runBlocking { 100.EUR + 10.EUR }, 110.0.EUR)
    }

    @Test
    fun `subtract 100 EUR from 1000 EUR`() {
        assertEquals(runBlocking { 1000.EUR - 10.EUR }, 990.0.EUR)
    }

    @Test
    fun `ten times 100 USD`() {
        assertEquals(runBlocking { 100.USD * 10 }, 1000.USD)
    }

    @Test
    fun `split 1000 CHF in two`() {
        assertEquals(runBlocking { 1000.CHF / 2 }, +(500.CHF))
    }

    @Test
    fun `division by zero`() {
        assertEquals(runBlocking { 100.USD / 0 }, null)
    }

    @Test
    fun `subtract 100 RON from 50 RON`() {
        assertEquals(runBlocking { 50.RON - 100.RON }, -(50.RON))
    }

    @Test
    fun `with rate 1,1 add 500 USD to 1000 EUR`() {
        Currency.exchange = rate(1.1)
        assertEquals(runBlocking { 1000.EUR + 500.USD }, 1550.EUR)
    }

    @Test
    fun `exchange 1 euro to euros (same currency)`() {
        assertEquals(runBlocking { 1.EUR to EUR }, 1.0.EUR)
    }

    @Test
    fun `with rate 2,0 add 500 USD to 1000 EUR and exchange for CHF with rate 3`() {
        Currency.exchange = rate(2)
        val sum = runBlocking { 1000.EUR + 500.USD }
        Currency.exchange = rate(3)
        assertEquals(runBlocking { sum to CHF }, 6000.0.CHF)
    }

    @Test
    fun `print 1000 EUR`() {
        assertEquals(1000.EUR.toString(), "1000.00 EUR")
    }

    @Test
    fun `print 9299,9235 EUR with 3 decimals accuracy`() {
        Currency.scale = 3
        assertEquals(9299.9235.EUR.toString(), "9299.924 EUR")
        Currency.scale = 2 //reset
    }

    @Test
    fun `round 99,2345 with 2 decimals accuracy and round down mode`() {
        Currency.scale = 2
        Currency.roundingMode = RoundingMode.DOWN
        assertEquals(99.235.EUR.toString(), "99.23 EUR")
        Currency.scale = 2 //reset
        Currency.roundingMode = RoundingMode.HALF_EVEN
    }

    @Test
    fun `zero EUR`() {
        assertEquals(0.EUR, EUR)
    }

    @Test
    fun `zero CAD`() {
        assertEquals(0.CAD, CAD)
    }

    @Test
    fun `zero HKD`() {
        assertEquals(0.HKD, HKD)
    }

    @Test
    fun `zero ISK`() {
        assertEquals(0.ISK, ISK)
    }

    @Test
    fun `zero PHP`() {
        assertEquals(0.PHP, PHP)
    }

    @Test
    fun `zero DKK`() {
        assertEquals(0.DKK, DKK)
    }

    @Test
    fun `zero HUF`() {
        assertEquals(0.HUF, HUF)
    }

    @Test
    fun `zero CZK`() {
        assertEquals(0.CZK, CZK)
    }

    @Test
    fun `zero AUD`() {
        assertEquals(0.AUD, AUD)
    }

    @Test
    fun `zero RON`() {
        assertEquals(0.RON, RON)
    }

    @Test
    fun `zero SEK`() {
        assertEquals(0.SEK, SEK)
    }

    @Test
    fun `zero IDR`() {
        assertEquals(0.IDR, IDR)
    }

    @Test
    fun `zero INR`() {
        assertEquals(0.INR, INR)
    }

    @Test
    fun `zero BRL`() {
        assertEquals(0.BRL, BRL)
    }

    @Test
    fun `zero RUB`() {
        assertEquals(0.RUB, RUB)
    }

    @Test
    fun `zero HRK`() {
        assertEquals(0.HRK, HRK)
    }

    @Test
    fun `zero JPY`() {
        assertEquals(0.JPY, JPY)
    }

    @Test
    fun `zero THB`() {
        assertEquals(0.THB, THB)
    }

    @Test
    fun `zero CHF`() {
        assertEquals(0.CHF, CHF)
    }

    @Test
    fun `zero SGD`() {
        assertEquals(0.SGD, SGD)
    }

    @Test
    fun `zero PLN`() {
        assertEquals(0.PLN, PLN)
    }

    @Test
    fun `zero BGN`() {
        assertEquals(0.BGN, BGN)
    }

    @Test
    fun `zero TRY`() {
        assertEquals(0.TRY, TRY)
    }

    @Test
    fun `zero CNY`() {
        assertEquals(0.CNY, CNY)
    }

    @Test
    fun `zero NOK`() {
        assertEquals(0.NOK, NOK)
    }

    @Test
    fun `zero NZD`() {
        assertEquals(0.NZD, NZD)
    }

    @Test
    fun `zero ZAR`() {
        assertEquals(0.ZAR, ZAR)
    }

    @Test
    fun `zero USD`() {
        assertEquals(0.USD, USD)
    }

    @Test
    fun `zero MXN`() {
        assertEquals(0.MXN, MXN)
    }

    @Test
    fun `zero ILS`() {
        assertEquals(0.ILS, ILS)
    }

    @Test
    fun `zero GBP`() {
        assertEquals(0.GBP, GBP)
    }

    @Test
    fun `zero KRW`() {
        assertEquals(0.KRW, KRW)
    }

    @Test
    fun `zero MYR`() {
        assertEquals(0.MYR, MYR)
    }


    @Test
    fun `Currency equality`() {
        assertEquals(1_000.EUR.hashCode(), 1_000.00.EUR.hashCode())
    }

    @Test
    fun `Currency equality is reflexive`() {
        assertEquals(1_000.01.EUR, 1_000.01.EUR)
    }

    @Test
    fun `Currency equality is symmetric`() {
        val c1 = 1_000.01.EUR
        val c2 = 1000.01.EUR
        assertEquals(c1 == c2, c2 == c1)
    }

    @Test
    fun `Currency inequality`() {
        assertNotEquals(1.USD, 2.USD)
    }

    @Test
    fun `Currency equality is transitive`() {
        val c1 = 1_000.0.EUR
        val c2 = 1000.00.EUR
        val c3 = 1000.EUR
        assertEquals(c1, c2)
        assertEquals(c2, c3)
        assertEquals(c1, c1)
    }
}