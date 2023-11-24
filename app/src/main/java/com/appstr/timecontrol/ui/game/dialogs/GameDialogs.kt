package com.appstr.timecontrol.ui.game.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.appstr.timecontrol.domain.models.GameState
import com.appstr.timecontrol.domain.models.Player
import com.appstr.timecontrol.ui.game.viewmodels.GameViewModel
import com.appstr.timecontrol.ui.theme.red
import com.appstr.timecontrol.ui.theme.white
import com.appstr.timecontrol.util.hoursFrom
import com.appstr.timecontrol.util.isValidHours
import com.appstr.timecontrol.util.isValidMinutes
import com.appstr.timecontrol.util.isValidSeconds
import com.appstr.timecontrol.util.minutesFrom
import com.appstr.timecontrol.util.secondsFrom
import com.appstr.timecontrol.util.timeISValid


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogSetPlayerTime(
    gameState: GameState,
    player: Player,
    gameVM: GameViewModel = viewModel()
){

    Dialog(
        onDismissRequest = { gameVM.onDialogActionDismissSetPlayersTime() }
    ) {
        val playerHours = when (player){
            Player.ONE -> gameState.player1CurrentTime.hoursFrom()
            Player.TWO -> gameState.player2CurrentTime.hoursFrom()
        }
        val playerMinutes = when (player){
            Player.ONE -> gameState.player1CurrentTime.minutesFrom()
            Player.TWO -> gameState.player2CurrentTime.minutesFrom()
        }
        val playerSeconds = when (player){
            Player.ONE -> gameState.player1CurrentTime.secondsFrom()
            Player.TWO -> gameState.player2CurrentTime.secondsFrom()
        }
        var hours by rememberSaveable { mutableStateOf(playerHours) }
        var minutes by rememberSaveable { mutableStateOf(playerMinutes) }
        var seconds by rememberSaveable { mutableStateOf(playerSeconds) }

        var errorMessage by rememberSaveable { mutableStateOf("") }


        Column(
            modifier = Modifier
                .padding(16.dp)
                .clip(shape = RoundedCornerShape(16.dp))
                .background(white)
                .rotate(
                    when (player) {
                        Player.ONE -> 0f
                        Player.TWO -> 180f
                    }
                )
        ) {
            // title
            when (player){
                Player.ONE -> {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = "Edit Player 1's Time"
                    )
                }
                Player.TWO -> {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = "Edit Player 2's Time"
                    )
                }
            }
            // time edittexts
            Row(
                modifier = Modifier.wrapContentSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .width(56.dp),
                    value = hours,
                    onValueChange = {
                        hours = when {
                            it.length > 2 -> it.substring(0 .. 1)
                            else -> it
                        }
                    },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Text(
                    modifier = Modifier.padding(4.dp),
                    text = ":",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold
                )
                OutlinedTextField(
                    modifier = Modifier
                        .width(56.dp),
                    value = minutes,
                    onValueChange = {
                        minutes = when {
                            it.length > 2 -> it.substring(0 .. 1)
                            else -> it
                        }
                    },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Text(
                    modifier = Modifier.padding(4.dp),
                    text = ":",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold
                )
                OutlinedTextField(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .width(56.dp),
                    value = seconds,
                    onValueChange = {
                        seconds = when {
                            it.length > 2 -> it.substring(0 .. 1)
                            else -> it
                        }
                    },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
            // Error message
            Text(
                text = errorMessage,
                color = red,
                fontSize = 20.sp
            )
            // cancel/continue buttons
            Row(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .align(alignment = Alignment.End),
            ) {
                TextButton(onClick = { gameVM.onDialogActionDismissSetPlayersTime() }) {
                    Text(text = "Cancel")
                }
                TextButton(
                    onClick = {
                        when {
                            (!hours.isValidHours() || !minutes.isValidMinutes() || !seconds.isValidSeconds())
                                    || !timeISValid(hours, minutes, seconds)
                                -> errorMessage = "INVALID FORMAT"
                            else -> gameVM.onDialogActionConfirmSetPlayersTime(
                                player,
                                hours.toIntOrNull() ?: 0,
                                minutes.toIntOrNull() ?: 0,
                                seconds.toIntOrNull() ?: 0
                            )
                        }
                    }
                ) {
                    Text(text = "Confirm")
                }
            }
        }
    }
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
