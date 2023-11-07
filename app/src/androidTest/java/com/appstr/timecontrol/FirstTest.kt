package com.appstr.timecontrol

import androidx.compose.ui.test.junit4.createComposeRule
import com.appstr.timecontrol.ui.screen.GameScreen
import org.junit.Rule
import org.junit.Test

class FirstTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun enterFormula_showsFormula() {
        rule.setContent { GameScreen() }


    }

}