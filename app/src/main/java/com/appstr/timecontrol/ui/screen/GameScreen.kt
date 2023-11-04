package com.appstr.timecontrol.ui.screen

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.appstr.timecontrol.R
import com.appstr.timecontrol.ui.theme.blueGrey
import com.appstr.timecontrol.ui.theme.brown
import com.appstr.timecontrol.ui.theme.green
import com.appstr.timecontrol.ui.theme.grey
import com.appstr.timecontrol.ui.theme.lightBlue
import com.appstr.timecontrol.ui.theme.lightGreen


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
                    .clickable {

                    }
            ) {

            }
            Row(
                modifier = Modifier
                    .width(screenWidth)
                    .height(centerButtonsHeight)
                    .background(brown),
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
                        .clickable {

                        }
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_play),
                    contentDescription = "play",
                    colorFilter = ColorFilter.tint(green),
                    modifier = Modifier
                        .size(centerIconHeights)
                        .padding(4.dp)
                        .clickable {

                        }
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_reset_time),
                    contentDescription = "play",
                    colorFilter = ColorFilter.tint(lightBlue),
                    modifier = Modifier
                        .size(48.dp)
                        .padding(end = 16.dp)
                        .clickable {

                        }
                )
            }
            Box(
                modifier = Modifier
                    .width(screenWidth)
                    .height(botPlayerHeight)
                    .background(lightGreen)
                    .clickable {

                    }
            ) {

            }
        }

        // player 1 time set icon

        // player 2 time set icon

    }
}
