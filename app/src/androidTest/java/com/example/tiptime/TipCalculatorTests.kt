package com.example.tiptime

import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.NumberFormat

class TipCalculatorTests {

    @Test
    fun calculateTip_twentyPercentWithoutRounding() {
        val expected = NumberFormat.getCurrencyInstance().format(2.0)

        val actual = calculateTip(
            amount = 10.0,
            tipPercent = 20.0,
            roundUp = false
        )

        assertEquals(expected, actual)
    }
    @Test
    fun calculateTip_roundsFractionUp() {
        val expected = NumberFormat.getCurrencyInstance().format(2.0)

        val actual = calculateTip(
            amount = 11.0,
            tipPercent = 15.0,
            roundUp = true
        )

        assertEquals(expected, actual)
    }

    @Test
    fun calculateTip_zeroAmountReturnsZero() {
        val expected = NumberFormat.getCurrencyInstance().format(0.0)

        val actual = calculateTip(
            amount = 0.0,
            tipPercent = 20.0,
            roundUp = false
        )

        assertEquals(expected, actual)
    }
}