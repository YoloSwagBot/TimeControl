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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.appstr.timecontrol.R
import com.appstr.timecontrol.ui.theme.black
import com.appstr.timecontrol.ui.theme.blueGrey
import com.appstr.timecontrol.ui.theme.blueGrey300
import com.appstr.timecontrol.ui.theme.brown
import com.appstr.timecontrol.ui.theme.green
import com.appstr.timecontrol.ui.theme.grey
import com.appstr.timecontrol.ui.theme.lightBlue
import com.appstr.timecontrol.ui.theme.lightGreen
import com.appstr.timecontrol.ui.theme.lightGreen900
import com.appstr.timecontrol.ui.theme.teal
import com.appstr.timecontrol.ui.theme.white


@Composable
fun GameScreen(

){
    val context = LocalContext.current
    (context as? Activity)?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT


    BoxWithConstraints(

    ) {
        val screenWidth = maxWidth
        val centerButtonsHeight = 64.dp
        val topPlayerHeight = ((maxHeight - centerButtonsHeight) / 2)
        val botPlayerHeight = topPlayerHeight

        val centerIconHeights = 56.dp

        Column(

        ) {
            Box(
                modifier = Modifier
                    .width(screenWidth)
                    .height(topPlayerHeight)
                    .background(lightGreen)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(color = teal),
                        onClick = { /* wooo*/ }
                    )
            ) {
                var player1TimeLeft = ""
                Text(
                    text = player1TimeLeft
                )
            }
            Row(
                modifier = Modifier
                    .width(screenWidth)
                    .height(centerButtonsHeight)
                    .background(white),
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
                    painter = painterResource(id = R.drawable.ic_play),
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
                            onClick = { /* wooo*/ }
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
            Box(
                modifier = Modifier
                    .width(screenWidth)
                    .height(botPlayerHeight)
                    .background(lightGreen)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(color = teal),
                        onClick = { /* wooo*/ }
                    )
            ) {
                var player2TimeLeft = ""
                Text(
                    text = player2TimeLeft
                )
            }
        }

        // player 1 time set icon
        Image(
            painter = painterResource(id = R.drawable.ic_change_time),
            contentDescription = "edit player's time",
            modifier = Modifier
                .size(24.dp)
                .offset(x = 8.dp, y = maxHeight-32.dp),
            colorFilter = ColorFilter.tint(color = lightGreen900)
        )
        // player 2 time set icon
        Image(
            painter = painterResource(id = R.drawable.ic_change_time),
            contentDescription = "edit player's time",
            modifier = Modifier
                .size(24.dp)
                .offset(x = maxWidth-32.dp, y = 8.dp)
                .rotate(180f),
            colorFilter = ColorFilter.tint(color = lightGreen900)
        )
    }
}
