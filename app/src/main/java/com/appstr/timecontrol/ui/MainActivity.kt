package com.appstr.timecontrol.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.appstr.timecontrol.domain.models.TimeControl
import com.appstr.timecontrol.domain.models.toPlayer
import com.appstr.timecontrol.ui.game.dialogs.DialogArgsAskCancelCurrentGame
import com.appstr.timecontrol.ui.game.dialogs.DialogArgsSetPlayerTime
import com.appstr.timecontrol.ui.game.dialogs.DialogAskCancelCurrentGame
import com.appstr.timecontrol.ui.game.dialogs.DialogSetPlayerTime
import com.appstr.timecontrol.ui.game.screens.GameScreen
import com.appstr.timecontrol.ui.game.screens.SetupTimeScreen
import com.appstr.timecontrol.ui.game.viewmodels.GameViewModel
import com.appstr.timecontrol.ui.theme.TimeControlTheme
import com.appstr.timecontrol.util.Dialog
import com.appstr.timecontrol.util.Screen
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

//         used to save/recreate game_state during app destruction/creation
        val gameVM: GameViewModel by viewModels()
        lifecycle.addObserver(gameVM)
    }

    override fun onDestroy() {
        super.onDestroy()

        // used to save/recreate game_state during app destruction/creation
        val gameVM: GameViewModel by viewModels()
        lifecycle.removeObserver(gameVM)
    }

}

@Composable
fun Navigation(){
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val navController = rememberNavController()
        val gameVM: GameViewModel = hiltViewModel()

        NavHost(
            navController = navController,
            startDestination = Screen.GameScreen.route
        ){
            /* Screens */
            composable(route = Screen.GameScreen.route){
                GameScreen(
                    navController = navController,
                    gameVM = gameVM
                )
            }
            composable(route = Screen.SetupTimeScreen.route){
                SetupTimeScreen(
                    navController = navController,
                    gameVM = gameVM,
                )
            }

            /* Dialogs */
            dialog(
                route = Dialog.SetPlayerTime.route,
                arguments = listOf(
                    navArgument(DialogArgsSetPlayerTime.argPlayer){ type = NavType.IntType },
                    navArgument(DialogArgsSetPlayerTime.argPlayerTime){ type = NavType.IntType }
                )
            ){ backStackEntry ->
                val player = backStackEntry.arguments?.getInt(DialogArgsSetPlayerTime.argPlayer) ?: -1
                val playerTime = backStackEntry.arguments?.getInt(DialogArgsSetPlayerTime.argPlayerTime) ?: -1
                DialogSetPlayerTime(
                    navController = navController,
                    player = player.toPlayer(),
                    playerTime = playerTime,
                    gameVM = gameVM
                )
            }
            dialog(
                route = Dialog.AskCancelCurrentGame.route,
                arguments = listOf(
                    navArgument(DialogArgsAskCancelCurrentGame.argStartValue){ type = NavType.IntType},
                    navArgument(DialogArgsAskCancelCurrentGame.argIncrement){ type = NavType.IntType}
                )
            ){ backStackEntry ->
                val startValue = backStackEntry.arguments?.getInt(DialogArgsAskCancelCurrentGame.argStartValue) ?: -1
                val increment = backStackEntry.arguments?.getInt(DialogArgsAskCancelCurrentGame.argIncrement) ?: -1
                DialogAskCancelCurrentGame(
                    navController = navController,
                    timeControlToSet = TimeControl(startValue, increment),
                    gameVM = gameVM
                )
            }
        }
    }
}