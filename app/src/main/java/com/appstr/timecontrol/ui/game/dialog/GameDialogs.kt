package com.appstr.timecontrol.ui.game.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.appstr.timecontrol.ui.game.model.Player
import com.appstr.timecontrol.ui.game.viewmodel.GameViewModel


@Composable
fun DialogEditPlayerTime(
    player: Player,
    gameVM: GameViewModel = viewModel()
){
    Picker
}

@Composable
fun DialogCheckCancelCurrentGame(

    gameVM: GameViewModel = viewModel()
){
    AlertDialog(
        title = {
            Text(text = "Cancel current game?")
        },
        dismissButton = {
            TextButton(
                onClick = {
                    gameVM.onDialogActionDismissCancelGame()
                }
            ) {
                Text(text = "Cancel")
            }
        },
        onDismissRequest = {
            gameVM.onDialogActionDismissCancelGame()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    gameVM.onDialogActionConfirmCancelGame()
                }
            ) {
                Text("Confirm")
            }
        }
    )
}



