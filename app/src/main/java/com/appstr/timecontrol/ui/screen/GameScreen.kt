package com.appstr.timecontrol.ui.screen

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
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.appstr.timecontrol.R
import com.appstr.timecontrol.hour
import com.appstr.timecontrol.ui.model.GameState
import com.appstr.timecontrol.ui.theme.black
import com.appstr.timecontrol.ui.theme.blueGrey
import com.appstr.timecontrol.ui.theme.blueGrey50
import com.appstr.timecontrol.ui.theme.green
import com.appstr.timecontrol.ui.theme.grey
import com.appstr.timecontrol.ui.theme.lightBlue
import com.appstr.timecontrol.ui.theme.lightGreen
import com.appstr.timecontrol.ui.theme.lightGreen900
import com.appstr.timecontrol.ui.theme.teal
import com.appstr.timecontrol.ui.theme.white
import com.appstr.timecontrol.util.formatTimeToText
import com.appstr.timecontrol.viewmodel.GameViewModel
import kotlinx.coroutines.delay


@Composable
fun GameScreen(
    gameViewModel: GameViewModel = viewModel()
){

    val context = LocalContext.current
    (context as? Activity)?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    val gameState = gameViewModel.gameState.collectAsState()

    // decrement time
    LaunchedEffect(Unit){
        while(!gameState.value.isPaused){
            gameViewModel.decrementTimeByTurn(gameState.value.turn)
            delay(1000)
        }
    }

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

    }
}

@Composable
fun ButtonsRow(
    gameState: GameState,
    screenWidth: Dp,
    centerButtonsRowHeight: Dp,
    gameViewModel: GameViewModel = viewModel()
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
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(
                        color = blueGrey,
                        bounded = false,
                        radius = 32.dp
                    ),
                    onClick = { /* wooo*/ }
                )
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
                        gameViewModel.pausePlayClicked()
                    }
                )
        )
        Image(
            painter = painterResource(id = R.drawable.ic_reset_time),
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
                    onClick = { /* wooo*/ }
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

    Box(
        modifier = Modifier
            .width(screenWidth)
            .height(topPlayerAreaHeight)
            .background(
                when {
                    gameState.isPaused -> white
                    gameState.turn == 2 -> lightGreen
                    else -> white
                }
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(color = teal),
                onClick = {
                    gameViewModel.onPlayer2Click()
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
        // player 2 time set icon
        Image(
            painter = painterResource(id = R.drawable.ic_change_time),
            contentDescription = "edit player's time",
            modifier = Modifier
                .size(24.dp)
                .offset(x = (screenWidth / 2) - 24.dp, y = -(topPlayerAreaHeight / 2) + 24.dp)
                .rotate(180f)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(
                        color = lightGreen900,
                        bounded = false,
                        radius = 32.dp
                    ),
                    onClick = { /* wooo*/ }
                ),
            colorFilter = ColorFilter.tint(color = lightGreen900)
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
                    gameState.isPaused -> white
                    gameState.turn == 1 -> lightGreen
                    else -> white
                }
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(color = teal),
                onClick = {
                    gameViewModel.onPlayer1Click()
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
        // player 1 time set icon
        Image(
            painter = painterResource(id = R.drawable.ic_change_time),
            contentDescription = "edit player's time",
            modifier = Modifier
                .size(24.dp)
                .offset(x = (botPlayerHeight / 2) - 8.dp, y = (botPlayerHeight / 2) - 24.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(
                        color = lightGreen900,
                        bounded = false,
                        radius = 32.dp
                    ),
                    onClick = {

                    }
                ),
            colorFilter = ColorFilter.tint(color = lightGreen900)
        )
    }
}

