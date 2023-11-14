package com.appstr.timecontrol.ui.game.screen

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.activity.compose.BackHandler
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
import androidx.compose.material.ripple.rememberRipple
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
import com.appstr.timecontrol.ui.game.dialog.DialogEndGame
import com.appstr.timecontrol.ui.game.dialog.DialogSetPlayerTime
import com.appstr.timecontrol.ui.game.model.GameState
import com.appstr.timecontrol.ui.game.model.Player
import com.appstr.timecontrol.ui.game.model.canStart
import com.appstr.timecontrol.ui.game.model.doesntExist
import com.appstr.timecontrol.ui.game.model.exists
import com.appstr.timecontrol.ui.game.model.formatTimeToText
import com.appstr.timecontrol.ui.game.model.isNotOver
import com.appstr.timecontrol.ui.game.model.isOver
import com.appstr.timecontrol.ui.game.viewmodel.GameViewModel
import com.appstr.timecontrol.ui.theme.black
import com.appstr.timecontrol.ui.theme.blueGrey
import com.appstr.timecontrol.ui.theme.blueGrey50
import com.appstr.timecontrol.ui.theme.brown
import com.appstr.timecontrol.ui.theme.brown300
import com.appstr.timecontrol.ui.theme.green
import com.appstr.timecontrol.ui.theme.lightGreen
import com.appstr.timecontrol.ui.theme.lightGreen400
import com.appstr.timecontrol.ui.theme.lightGreen900
import com.appstr.timecontrol.ui.theme.red200
import com.appstr.timecontrol.ui.theme.red500
import com.appstr.timecontrol.ui.theme.teal
import com.appstr.timecontrol.ui.theme.white
import kotlinx.coroutines.delay


@Composable
fun GameScreen(
    gameVM: GameViewModel = viewModel()
){

    // TODO -- fix orientation to support landscape
    val context = LocalContext.current
    (context as? Activity)?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    val gameState = gameVM.gameState.collectAsState(null)

    // decrement time
    gameState.value?.let { game ->
        if (game.isNotOver() && !game.isPaused){
            LaunchedEffect(Unit){
                while(true){
                    delay(1000)
                    gameVM.decrementTimeByTurn()
                }
            }
        }
    }

    BoxWithConstraints() {
        val screenWidth = maxWidth
        val centerButtonsHeight = 64.dp
        val playerAreaHeight = ((maxHeight - centerButtonsHeight) / 2)

        Column {
            Player2Area(gameState = gameState.value, screenWidth = screenWidth, topPlayerAreaHeight = playerAreaHeight)
            ButtonsRow(gameState = gameState.value, screenWidth = screenWidth, centerButtonsRowHeight = centerButtonsHeight)
            Player1Area(gameState = gameState.value, screenWidth = screenWidth, botPlayerHeight = playerAreaHeight)
        }

        BottomRow(
            gameState = gameState.value,
            maxWidth = maxWidth,
            maxHeight = maxHeight,
            player = Player.TWO
        )
        BottomRow(
            gameState = gameState.value,
            maxWidth = maxWidth,
            maxHeight = maxHeight,
            player = Player.ONE
        )

    }


    val dialogSetPlayerTime by gameVM.dialogSetPlayersTimeShowing.collectAsState()
    dialogSetPlayerTime?.let { pl ->
        gameState.value?.let { gs ->
            DialogSetPlayerTime(gameState = gs, player = pl)
        }
    }

    val dialogEndGame by gameVM.dialogEndGameShowing.collectAsState()
    dialogEndGame?.let { pl ->
        gameState.value?.let { gs ->
            DialogEndGame(gameState = gs, player = pl)
        }
    }

    val showSetupTimeScreen by gameVM.screenSetupTimeShowing.collectAsState()
    if (showSetupTimeScreen){
        SetupTimeScreen()
    }

    BackHandler(false) {
        gameState.value?.let { gameVM.pauseGame() }
    }
}

@Composable
fun ButtonsRow(
    gameState: GameState?,
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
//                    onClick = {  }
//                )

        )
        // pause/play button
        if (gameState.canStart() && gameState.isNotOver()){
            Image(
                painter = painterResource(id = when(gameState?.isPaused == true){
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
                            if (gameState.isNotOver()) {
                                gameVM.pausePlayClicked()
                            }
                        }
                    )
            )
        }
        Image(
            painter = painterResource(id = R.drawable.ic_time_control_setup),
            contentDescription = "play",
            colorFilter = ColorFilter.tint(lightGreen900),
            modifier = Modifier
                .size(48.dp)
                .padding(end = 16.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(
                        color = lightGreen,
                        bounded = false,
                        radius = 32.dp
                    ),
                    onClick = {
                        gameVM.showSetupTimeScreen()
                    }
                )
        )
    }
}

@Composable
fun Player2Area(
    gameState: GameState?,
    screenWidth: Dp,
    topPlayerAreaHeight: Dp,
    gameViewModel: GameViewModel = viewModel()
){
    Box(
        modifier = Modifier
            .width(screenWidth)
            .height(topPlayerAreaHeight)
            .background(
                when {
                    gameState?.isOver() ?: false -> brown300
                    gameState?.turn == Player.TWO && gameState.player1CurrentTime < gameState.player1StartTime * .1 -> red200
                    gameState?.turn == Player.TWO && gameState.player1CurrentTime < gameState.player1StartTime * .05 -> red500
                    gameState?.isPaused == true -> white
                    gameState?.turn == Player.TWO -> lightGreen400
                    else -> white
                }
            )
            .clickable(
                enabled = gameState?.isPaused == false,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(color = teal),
                onClick = {
                    if (gameState.isNotOver()) {
                        gameViewModel.onPlayer2Click()
                    }
                }
            )
    ) {
        // move count Label
        if (gameState.exists()){
            Text(
                text = (gameState?.player2MoveCount ?: 0).toString(),
                fontSize = 16.sp,
                color = black,
                modifier = Modifier
                    .rotate(180f)
                    .align(Alignment.BottomStart)
                    .padding(8.dp)
            )
        }
        // player's time - label
        Text(
            text = gameState?.player2CurrentTime.formatTimeToText(),
            fontSize = 80.sp,
            color = black,
            modifier = Modifier
                .rotate(180f)
                .align(Alignment.Center)
        )
        // game is over - label
//        if (gameState.isOver()){
//            Text(
//                text = gameState?.gameEndReason?.toExplanation() ?: "Game Over: null",
//                fontSize = 16.sp,
//                color = black,
//                modifier = Modifier
//                    .rotate(180f)
//                    .align(Alignment.BottomCenter)
//                    .padding(start = 64.dp, top = 8.dp, end = 64.dp)
//            )
//        }
    }
}

@Composable
fun Player1Area(
    gameState: GameState?,
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
                    gameState?.isOver() ?: false -> brown300
                    gameState?.turn == Player.ONE && gameState.player1CurrentTime < gameState.player1StartTime * .1 -> red200
                    gameState?.turn == Player.ONE && gameState.player1CurrentTime < gameState.player1StartTime * .05 -> red500
                    gameState?.isPaused == true -> white
                    gameState?.turn == Player.ONE -> lightGreen400
                    else -> white
                }
            )
            .clickable(
                enabled = gameState?.isPaused == false,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(color = teal),
                onClick = {
                    if (gameState.isNotOver()) {
                        gameViewModel.onPlayer1Click()
                    }
                }
            )
    ) {
        if (gameState.exists()){
            Text(
                text = (gameState?.player1MoveCount ?: 0).toString(),
                fontSize = 16.sp,
                color = black,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(8.dp)
            )
        }
        Text(
            text = gameState?.player1CurrentTime.formatTimeToText(),
            fontSize = 80.sp,
            color = black,
            modifier = Modifier.align(Alignment.Center)
        )
        // game is over - label
//        if (gameState.isOver()){
//            Text(
//                text = gameState?.gameEndReason?.toExplanation() ?: "Game Over: null",
//                fontSize = 16.sp,
//                color = black,
//                modifier = Modifier
//                    .align(Alignment.TopCenter)
//                    .padding(start = 64.dp, top = 8.dp, end = 64.dp)
//            )
//        }
    }
}

@Composable
fun BottomRow(
    gameState: GameState?,
    maxWidth: Dp,
    maxHeight: Dp,
    player: Player,
    gameVM: GameViewModel = viewModel()
){

    val modifier = when (player){
        Player.ONE -> {
            Modifier
                .width(maxWidth)
                .height(if (gameState.doesntExist() || gameState.isOver()) 0.dp else 64.dp)
                .offset(y = maxHeight - 48.dp)
        }
        Player.TWO -> {
            Modifier
                .width(maxWidth)
                .height(if (gameState.doesntExist() || gameState.isOver()) 0.dp else 64.dp)
                .rotate(180f)
                .offset(y = (16).dp)
        }
    }

    BoxWithConstraints(
        modifier = modifier,
    ) {
        // End-Game button
        Image(
            modifier = Modifier
                .size(0.dp)
                .offset(x = 8.dp, y = 16.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(
                        color = brown,
                        bounded = false,
                        radius = 32.dp
                    ),
                    onClick = {
                        gameVM.showDialogEndGame(player)
                    }
                ),
            painter = painterResource(id = R.drawable.ic_door),
            contentDescription = "End Game",
            colorFilter = ColorFilter.tint(color = brown)
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
                        gameState?.let { gameVM.showDialogSetPlayersTime(player) }
                    }
                ),
            colorFilter = ColorFilter.tint(color = lightGreen900)
        )
    }
}
