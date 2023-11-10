package com.appstr.timecontrol.ui.game.screen

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.appstr.timecontrol.R
import com.appstr.timecontrol.ui.game.dialog.DialogCheckCancelCurrentGame
import com.appstr.timecontrol.ui.game.dialog.DialogSetPlayerTime
import com.appstr.timecontrol.ui.game.model.GameState
import com.appstr.timecontrol.ui.game.model.Player
import com.appstr.timecontrol.ui.theme.black
import com.appstr.timecontrol.ui.theme.blueGrey
import com.appstr.timecontrol.ui.theme.blueGrey50
import com.appstr.timecontrol.ui.theme.green
import com.appstr.timecontrol.ui.theme.lightBlue
import com.appstr.timecontrol.ui.theme.lightGreen
import com.appstr.timecontrol.ui.theme.lightGreen900
import com.appstr.timecontrol.ui.theme.red200
import com.appstr.timecontrol.ui.theme.red500
import com.appstr.timecontrol.ui.theme.teal
import com.appstr.timecontrol.ui.theme.white
import com.appstr.timecontrol.util.formatTimeToText
import com.appstr.timecontrol.ui.game.viewmodel.GameViewModel
import kotlinx.coroutines.delay


@Composable
fun GameScreen(
    gameVM: GameViewModel = viewModel()
){

    val context = LocalContext.current
    (context as? Activity)?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    val gameState = gameVM.gameState.collectAsState()


    BoxWithConstraints(

    ) {
        val screenWidth = maxWidth
        val centerButtonsHeight = 64.dp
        val playerAreaHeight = ((maxHeight - centerButtonsHeight) / 2)

        Column {
            Player2Area(gameState = gameState.value, screenWidth = screenWidth, topPlayerAreaHeight = playerAreaHeight)
            ButtonsRow(gameState = gameState.value, screenWidth = screenWidth, centerButtonsRowHeight = centerButtonsHeight)
            Player1Area(gameState = gameState.value, screenWidth = screenWidth, botPlayerHeight = playerAreaHeight)
        }
        BottomRow(
            modifier = Modifier
                .width(maxWidth)
                .height(64.dp)
                .rotate(180f)
                .offset(y = (16).dp),
            player = Player.TWO
        )
        BottomRow(
            modifier = Modifier
                .width(maxWidth)
                .height(64.dp)
                .offset(y = maxHeight - 48.dp),
            player = Player.ONE
        )

    }

    val dialogCancelGame by gameVM.dialogCancelGameShowing.collectAsState()
    if (dialogCancelGame){
        DialogCheckCancelCurrentGame()
    }

    val dialogSetPlayerTime by gameVM.dialogSetPlayersTimeShowing.collectAsState()
    dialogSetPlayerTime?.let { DialogSetPlayerTime(gameState = gameState.value, player = it) }

}

@Composable
fun ButtonsRow(
    gameState: GameState,
    screenWidth: Dp,
    centerButtonsRowHeight: Dp,
    gameVM: GameViewModel = viewModel()
){
    val centerIconHeights = 56.dp

    Row(
        modifier = Modifier
            .width(screenWidth)
            .height(centerButtonsRowHeight)
            .background(blueGrey50),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_settings),
            contentDescription = "play",
            colorFilter = ColorFilter.tint(blueGrey),
            modifier = Modifier
                .size(48.dp)
                .padding(start = 16.dp)
                .alpha(0f)
//                .clickable(
//                    interactionSource = remember { MutableInteractionSource() },
//                    indication = rememberRipple(
//                        color = blueGrey,
//                        bounded = false,
//                        radius = 32.dp
//                    ),
//                    onClick = { /* wooo*/ }
//                )

        )
        Image(
            painter = painterResource(id = when(gameState.isPaused){
                true -> R.drawable.ic_play
                false -> R.drawable.ic_pause
            }),
            contentDescription = "play",
            colorFilter = ColorFilter.tint(green),
            modifier = Modifier
                .size(centerIconHeights)
                .padding(4.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(
                        color = green,
                        bounded = false,
                        radius = 32.dp
                    ),
                    onClick = {
                        if (!gameState.isGameOver) {
                            gameVM.pausePlayClicked()
                        }
                    }
                )
        )
        Image(
            painter = painterResource(id = R.drawable.ic_time_control_setup),
            contentDescription = "play",
            colorFilter = ColorFilter.tint(lightBlue),
            modifier = Modifier
                .size(48.dp)
                .padding(end = 16.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(
                        color = lightBlue,
                        bounded = false,
                        radius = 32.dp
                    ),
                    onClick = {
                        gameVM.showDialogCancelGame()
                    }
                )
        )
    }
}

@Composable
fun Player2Area(
    gameState: GameState,
    screenWidth: Dp,
    topPlayerAreaHeight: Dp,
    gameViewModel: GameViewModel = viewModel()
){

    // decrement time
    if (!gameState.isGameOver && !gameState.isPaused){
        LaunchedEffect(Unit){
            while(true){
                delay(1000)
                gameViewModel.decrementTimeByTurn()
            }
        }
    }

    Box(
        modifier = Modifier
            .width(screenWidth)
            .height(topPlayerAreaHeight)
            .background(
                when {
                    gameState.turn == Player.TWO && gameState.player1CurrentTime < gameState.player1StartTime * .1 -> red200
                    gameState.turn == Player.TWO && gameState.player1CurrentTime < gameState.player1StartTime * .05 -> red500
                    gameState.isPaused -> white
                    gameState.turn == Player.TWO -> lightGreen
                    else -> white
                }
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(color = teal),
                onClick = {
                    if (!gameState.isGameOver) {
                        gameViewModel.onPlayer2Click()
                    }
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = gameState.player2CurrentTime.formatTimeToText(),
            fontSize = 80.sp,
            color = when {
                gameState.isPaused -> black
                else -> black
            },
            modifier = Modifier.rotate(180f)
        )
    }
}

@Composable
fun Player1Area(
    gameState: GameState,
    screenWidth: Dp,
    botPlayerHeight: Dp,
    gameViewModel: GameViewModel = viewModel()
){

    Box(
        modifier = Modifier
            .width(screenWidth)
            .height(botPlayerHeight)
            .background(
                when {
                    gameState.turn == Player.ONE && gameState.player1CurrentTime < gameState.player1StartTime * .1 -> red200
                    gameState.turn == Player.ONE && gameState.player1CurrentTime < gameState.player1StartTime * .05 -> red500
                    gameState.isPaused -> white
                    gameState.turn == Player.ONE -> lightGreen
                    else -> white
                }
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(color = teal),
                onClick = {
                    if (!gameState.isGameOver) {
                        gameViewModel.onPlayer1Click()
                    }
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = gameState.player1CurrentTime.formatTimeToText(),
            fontSize = 80.sp,
            color = when {
                gameState.isPaused -> black
                else -> black
            }
        )
    }
}

@Composable
fun BottomRow(
    modifier: Modifier,
    player: Player,
    gameVM: GameViewModel = viewModel()
){
    BoxWithConstraints(
        modifier = modifier,
    ) {
        // Call mate button
        ElevatedButton(
            modifier = Modifier.padding(start = 4.dp),
            colors = ButtonDefaults.buttonColors(containerColor = green),
            onClick = {

            }
        ) {
            Text(text = "Call Mate")
        }
        // Forfeit button
        Text(
            modifier = Modifier
                .wrapContentSize()
                .offset(x = (maxWidth / 2) - 28.dp, y = 4.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(
                        color = red500,
                        radius = 64.dp
                    ),
                    onClick = {

                    }
                )
                .padding(8.dp),
            text = "Resign"
        )
        // player 1 time set icon
        Image(
            painter = painterResource(id = R.drawable.ic_change_time),
            contentDescription = "edit player's time",
            modifier = Modifier
                .size(24.dp)
                .offset(x = maxWidth - 32.dp, y = 15.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(
                        color = lightGreen900,
                        bounded = false,
                        radius = 32.dp
                    ),
                    onClick = {
                        gameVM.showDialogSetPlayersTime(player)
                    }
                ),
            colorFilter = ColorFilter.tint(color = lightGreen900)
        )
    }
}
