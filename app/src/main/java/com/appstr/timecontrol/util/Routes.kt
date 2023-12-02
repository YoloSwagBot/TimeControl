package com.appstr.timecontrol.util

import android.os.Build
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.appstr.timecontrol.domain.models.doesntExist
import com.appstr.timecontrol.ui.game.dialogs.DialogArgsAskCancelCurrentGame
import com.appstr.timecontrol.ui.game.dialogs.DialogArgsSetPlayerTime


sealed class Screen(val route: String) {
    data object GameScreen: Screen("GameScreen")
    data object SetupTimeScreen: Screen("SetupTimeScreen")

}

sealed class Dialog(val route: String){
    data object AskCancelCurrentGame: Dialog("DialogCheckCancelCurrentGame")
    data object SetPlayerTime: Dialog("DialogSetPlayerTime")
}


fun NavController.navigateToGameScreen(){
    navigate(Screen.GameScreen.route)
}
fun NavController.navigateToSetupTimeScreen(){
    navigate(Screen.SetupTimeScreen.route)
}

/**
 * Dialog_AskCancelCurrentGame
 */

fun NavController.addDialog_AskCancelCurrentGame(args: DialogArgsAskCancelCurrentGame){
    currentBackStackEntry?.arguments?.putSerializable(DialogArgsAskCancelCurrentGame.key, args)
    navigate(Dialog.AskCancelCurrentGame.route)
}
fun NavBackStackEntry.getArgs_AskCancelCurrentGame(): DialogArgsAskCancelCurrentGame? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
        arguments?.getSerializable(DialogArgsAskCancelCurrentGame.key, DialogArgsAskCancelCurrentGame::class.java)
    }else{
        arguments?.getSerializable(DialogArgsAskCancelCurrentGame.key) as DialogArgsAskCancelCurrentGame
    }
}

/**
 * Dialog_SetPlayerTime
 */
fun NavController.addDialog_SetPlayerTime(args: DialogArgsSetPlayerTime){
    if (args.gameState.doesntExist()) return

    currentBackStackEntry?.arguments?.putSerializable(DialogArgsSetPlayerTime.key, args)
    navigate(Dialog.SetPlayerTime.route)
}
fun NavBackStackEntry.getArgs_SetPlayerTime(): DialogArgsSetPlayerTime? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
        arguments?.getSerializable(DialogArgsSetPlayerTime.key, DialogArgsSetPlayerTime::class.java)
    }else{
        arguments?.getSerializable(DialogArgsSetPlayerTime.key) as DialogArgsSetPlayerTime
    }
}



