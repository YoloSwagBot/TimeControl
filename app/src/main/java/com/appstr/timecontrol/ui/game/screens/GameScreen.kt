package com.appstr.timecontrol.ui.game.screens

import android.util.Log
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.appstr.timecontrol.R
import com.appstr.timecontrol.domain.models.GameState
import com.appstr.timecontrol.domain.models.Player
import com.appstr.timecontrol.domain.models.canStart
import com.appstr.timecontrol.domain.models.doesntExist
import com.appstr.timecontrol.domain.models.exists
import com.appstr.timecontrol.domain.models.formatTimeToText
import com.appstr.timecontrol.domain.models.isNotOver
import com.appstr.timecontrol.domain.models.isOver
import com.appstr.timecontrol.domain.models.toText
import com.appstr.timecontrol.ui.game.viewmodels.GameViewModel
import com.appstr.timecontrol.ui.theme.black
import com.appstr.timecontrol.ui.theme.blueGrey
import com.appstr.timecontrol.ui.theme.blueGrey50
import com.appstr.timecontrol.ui.theme.brown300
import com.appstr.timecontrol.ui.theme.green
import com.appstr.timecontrol.ui.theme.lightGreen
import com.appstr.timecontrol.ui.theme.lightGreen400
import com.appstr.timecontrol.ui.theme.lightGreen900
import com.appstr.timecontrol.ui.theme.red200
import com.appstr.timecontrol.ui.theme.red500
import com.appstr.timecontrol.ui.theme.teal
import com.appstr.timecontrol.ui.theme.white
import com.appstr.timecontrol.util.GAMESCREEN_PLAYER1_BACKGROUND
import com.appstr.timecontrol.util.GAMESCREEN_PLAYER1_MOVE_LABEL
import com.appstr.timecontrol.util.GAMESCREEN_PLAYER1_TIME_LABEL
import com.appstr.timecontrol.util.GAMESCREEN_PLAYER2_BACKGROUND
import com.appstr.timecontrol.util.GAMESCREEN_PLAYER2_MOVE_LABEL
import com.appstr.timecontrol.util.GAMESCREEN_PLAYER2_TIME_LABEL
import com.appstr.timecontrol.util.GAMESCREEN_SETUPTIMESCREEN_ICON
import com.appstr.timecontrol.util.addDialog_SetPlayerTime
import com.appstr.timecontrol.util.navigateToSetupTimeScreen
import kotlinx.coroutines.delay


@Composable
fun GameScreen(
    navController: NavController,
    gameVM: GameViewModel
){

    val gState by gameVM.gameState.collectAsState()
    Log.d("Carson", "GameScreen ---- 00 ---- gState ---- ${gState.timeControl?.toText()} ---- ${gState.hashCode()}")

    // decrement time
    if (gState.isNotOver() && !gState.isPaused){
        LaunchedEffect(Unit){
            while(true){
                delay(1000)
                gameVM.decrementTimeUseCase()
            }
        }
    }

    BoxWithConstraints() {
        val screenWidth = maxWidth
        val centerButtonsHeight = 64.dp
        val playerAreaHeight = ((maxHeight - centerButtonsHeight) / 2)

        Column {
            Player2Area(
                gameState = gState,
                screenWidth = screenWidth,
                topPlayerAreaHeight = playerAreaHeight,
                gameVM = gameVM
            )
            ButtonsRow(
                navController = navController,
                gameState = gState,
                screenWidth = screenWidth,
                centerButtonsRowHeight = centerButtonsHeight,
                gameVM = gameVM
            )
            Player1Area(
                gameState = gState,
                screenWidth = screenWidth,
                botPlayerHeight = playerAreaHeight,
                gameVM = gameVM
            )
        }

        BottomRow(
            navController = navController,
            gameState = gState,
            maxWidth = maxWidth,
            maxHeight = maxHeight,
            player = Player.TWO
        )
        BottomRow(
            navController = navController,
            gameState = gState,
            maxWidth = maxWidth,
            maxHeight = maxHeight,
            player = Player.ONE
        )

    }

    BackHandler(false) {
        gameVM.pauseGameUseCase()
    }
}

@Composable
fun ButtonsRow(
    navController: NavController,
    gameState: GameState?,
    screenWidth: Dp,
    centerButtonsRowHeight: Dp,
    gameVM: GameViewModel
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
                            gameVM.onClickPausePlayUseCase()
                        }
                    )
            )
        }
        Image(
            painter = painterResource(id = R.drawable.ic_time_control_setup),
            contentDescription = "play",
            colorFilter = ColorFilter.tint(lightGreen900),
            modifier = Modifier
                .testTag(GAMESCREEN_SETUPTIMESCREEN_ICON)
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
                        navController.navigateToSetupTimeScreen()
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
    gameVM: GameViewModel
){
    Box(
        modifier = Modifier
            .testTag(GAMESCREEN_PLAYER1_BACKGROUND)
            .width(screenWidth)
            .height(topPlayerAreaHeight)
            .background(
                when {
                    gameState?.isOver() ?: false -> brown300
                    gameState?.turn == Player.TWO && gameState.player2CurrentTime < gameState.player2StartTime * .1 -> red200
                    gameState?.turn == Player.TWO && gameState.player2CurrentTime < gameState.player2StartTime * .05 -> red500
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
                        gameVM.onClickPlayer2AreaUseCase()
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
                    .testTag(GAMESCREEN_PLAYER2_MOVE_LABEL)
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
                .testTag(GAMESCREEN_PLAYER2_TIME_LABEL)
                .rotate(180f)
                .align(Alignment.Center)
        )
    }
}

@Composable
fun Player1Area(
    gameState: GameState?,
    screenWidth: Dp,
    botPlayerHeight: Dp,
    gameVM: GameViewModel
){
    Box(
        modifier = Modifier
            .testTag(GAMESCREEN_PLAYER2_BACKGROUND)
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
                        gameVM.onClickPlayer1AreaUseCase()
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
                    .testTag(GAMESCREEN_PLAYER1_MOVE_LABEL)
                    .align(Alignment.TopStart)
                    .padding(8.dp)
            )
        }
        Text(
            text = gameState?.player1CurrentTime.formatTimeToText(),
            fontSize = 80.sp,
            color = black,
            modifier = Modifier
                .testTag(GAMESCREEN_PLAYER1_TIME_LABEL)
                .align(Alignment.Center)
        )
    }
}

@Composable
fun BottomRow(
    navController: NavController,
    gameState: GameState?,
    maxWidth: Dp,
    maxHeight: Dp,
    player: Player
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
                        Log.d("Carson", "GameScreen ---- setPlayerTime ---- 00")
                        gameState?.run {
                            Log.d("Carson", "GameScreen ---- setPlayerTime ---- 11")
                            navController.addDialog_SetPlayerTime(
                                player = player,
                                playerTime = when (player) {
                                    Player.ONE -> {
                                        player1CurrentTime
                                    }

                                    Player.TWO -> {
                                        player2CurrentTime
                                    }
                                }
                            )
                        }
                    }
                ),
            colorFilter = ColorFilter.tint(color = lightGreen900)
        )
    }
}
