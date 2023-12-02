package com.appstr.timecontrol.screens

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.appstr.timecontrol.di.DatabaseModule
import com.appstr.timecontrol.ui.MainActivity
import com.appstr.timecontrol.ui.game.screens.GameScreen
import com.appstr.timecontrol.ui.game.screens.SetupTimeScreen
import com.appstr.timecontrol.ui.theme.TimeControlTheme
import com.appstr.timecontrol.util.Screen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule

@HiltAndroidTest
@UninstallModules(DatabaseModule::class)
class TestGameScreen {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()


    @Before
    fun setup(){
        hiltRule.inject()
        composeRule.setContent {
            val navContrller = rememberNavController()
            TimeControlTheme {
                NavHost(
                    navController = navContrller,
                    startDestination = Screen.GameScreen.route
                ){
                    composable(route = Screen.GameScreen.route){
                        GameScreen(navController = navContrller)
                    }
                    composable(route = Screen.SetupTimeScreen.route){
                        SetupTimeScreen(navController = navContrller)
                    }
                }
            }
        }
    }


}