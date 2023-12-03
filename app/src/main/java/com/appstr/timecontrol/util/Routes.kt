package com.appstr.timecontrol.util

import androidx.navigation.NavController
import com.appstr.timecontrol.domain.models.Player
import com.appstr.timecontrol.domain.models.TimeControl
import com.appstr.timecontrol.domain.models.toInt


sealed class Screen(val route: String) {
    data object GameScreen: Screen("GameScreen")
    data object SetupTimeScreen: Screen("SetupTimeScreen")

}

sealed class Dialog(){
    sealed class AskCancelCurrentGame: Dialog(){
        companion object {
            val args = "/{startValue}/{increment}"
            val route = "DialogAskCancelCurrentGame$args"
            fun createRoute(tc: TimeControl): String = "DialogAskCancelCurrentGame/${tc.startValue}/${tc.increment}"
        }
    }
    sealed class SetPlayerTime: Dialog(){
        companion object {
            val args = "/{player}/{playerTime}"
            val route = "DialogSetPlayerTime$args"
            fun createRoute(player: Player, playerTime: Int): String = "DialogSetPlayerTime/${player.toInt()}/$playerTime"
        }
    }
}


//fun NavController.navigateToGameScreen(){
//    navigate(Screen.GameScreen.route)
//}
fun NavController.navigateToSetupTimeScreen(){
    navigate(Screen.SetupTimeScreen.route)
}

/**
 * Dialog_AskCancelCurrentGame
 */

fun NavController.addDialog_AskCancelCurrentGame(tc: TimeControl){
    navigate(Dialog.AskCancelCurrentGame.createRoute(tc))
}

/**
 * Dialog_SetPlayerTime
 */
fun NavController.addDialog_SetPlayerTime(player: Player, playerTime: Int){
    navigate(Dialog.SetPlayerTime.createRoute(player, playerTime))
}
