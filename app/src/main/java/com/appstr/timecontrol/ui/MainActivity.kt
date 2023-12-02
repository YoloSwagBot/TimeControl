package com.appstr.timecontrol.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.appstr.timecontrol.ui.game.dialogs.DialogAskCancelCurrentGame
import com.appstr.timecontrol.ui.game.dialogs.DialogSetPlayerTime
import com.appstr.timecontrol.ui.game.screens.GameScreen
import com.appstr.timecontrol.ui.game.screens.SetupTimeScreen
import com.appstr.timecontrol.ui.game.viewmodels.GameViewModel
import com.appstr.timecontrol.ui.theme.TimeControlTheme
import com.appstr.timecontrol.util.Dialog
import com.appstr.timecontrol.util.Screen
import com.appstr.timecontrol.util.getArgs_AskCancelCurrentGame
import com.appstr.timecontrol.util.getArgs_SetPlayerTime
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TimeControlTheme {
                Navigation()
            }
        }

        // used to save/recreate game_state during app destruction/creation
//        val gameVM: GameViewModel by viewModels()
//        lifecycle.addObserver(gameVM)
    }

    override fun onDestroy() {
        super.onDestroy()

        // used to save/recreate game_state during app destruction/creation
//        val gameVM: GameViewModel by viewModels()
//        lifecycle.removeObserver(gameVM)
    }

}

@Composable
fun Navigation(
    gameVM: GameViewModel = hiltViewModel()
){
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val navController = rememberNavController()



        NavHost(
            navController = navController,
            startDestination = Screen.GameScreen.route
        ){
            /* Screens */
            composable(route = Screen.GameScreen.route){
                GameScreen(
                    navController = navController,
                    gState = gameVM.gState
                )
            }
            composable(route = Screen.SetupTimeScreen.route){
                SetupTimeScreen(navController = navController)
            }

            /* Dialogs */
            dialog(route = Dialog.SetPlayerTime.route){ entry ->
                entry.getArgs_SetPlayerTime()?.let { args ->
                    args.gameState?.let {
                        DialogSetPlayerTime(
                            navController = navController,
                            gameState = args.gameState,
                            player = args.player
                        )
                    }
                }
            }
            dialog(route = Dialog.AskCancelCurrentGame.route){ entry ->
                entry.getArgs_AskCancelCurrentGame()?.let { args->
                    DialogAskCancelCurrentGame(
                        navController = navController,
                        timeControlToSet = args.timeControlToSet
                    )
                }
            }
        }
    }
}