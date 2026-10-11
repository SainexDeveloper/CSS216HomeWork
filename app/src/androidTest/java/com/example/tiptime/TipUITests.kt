package com.example.tiptime

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.tiptime.ui.theme.TipTimeTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.text.NumberFormat

@RunWith(AndroidJUnit4::class)
class TipUITests {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun showApp() {
        composeTestRule.setContent {
            TipTimeTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    TipTimeLayout()
                }
            }
        }
    }

    @Test
    fun enteringAmountAndPercentageShowsCorrectTip() {
        composeTestRule
            .onNodeWithText("Bill Amount")
            .performTextInput("10")

        composeTestRule
            .onNodeWithText("Tip Percentage")
            .performTextInput("20")

        val expected = NumberFormat.getCurrencyInstance().format(2.0)

        composeTestRule
            .onNodeWithText("Tip Amount: $expected")
            .assertExists()
    }

    @Test
    fun enablingRoundUpUpdatesDisplayedTip() {
        composeTestRule
            .onNodeWithText("Bill Amount")
            .performTextInput("11")

        composeTestRule
            .onNodeWithText("Tip Percentage")
            .performTextInput("15")

        val beforeRounding =
            NumberFormat.getCurrencyInstance().format(1.65)

        composeTestRule
            .onNodeWithText("Tip Amount: $beforeRounding")
            .assertExists()

        composeTestRule
            .onNode(isToggleable())
            .performScrollTo()
            .performClick()

        val afterRounding =
            NumberFormat.getCurrencyInstance().format(2.0)

        composeTestRule
            .onNodeWithText("Tip Amount: $afterRounding")
            .assertExists()
    }
}