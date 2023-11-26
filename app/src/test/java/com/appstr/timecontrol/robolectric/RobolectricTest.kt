package com.appstr.timecontrol.robolectric

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ActivityScenario
import com.appstr.timecontrol.ui.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowLog



@RunWith(RobolectricTestRunner::class)
class RobolectricTest {
    @get:Rule val composeTestRule = createComposeRule()


    @Before
    fun setup(){
        ShadowLog.stream = System.out
    }

    @Test
    fun `onClick setup time screen button, show SetupTimeScreen`(){
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            scenario.onActivity {
                composeTestRule.onNodeWithTag("SetupTimeScreen icon").performClick()
                composeTestRule.onNodeWithTag("SetupTimeScreen container").assertIsDisplayed()
            }
        }
    }


}