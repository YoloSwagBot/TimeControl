package com.appstr.timecontrol.ui.game.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.appstr.timecontrol.ui.game.viewmodel.GameViewModel
import com.appstr.timecontrol.ui.theme.green
import com.appstr.timecontrol.ui.theme.white


@Composable
fun SetupTimeScreen(

    gameVM: GameViewModel = viewModel()
){

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(color = white)
            .clickable(enabled = false){}
    ) {
        // Toolbar, (1) back_button, (2) title, (3) +custom button
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(green)
        ) {
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .offset(x = 16.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(
                            color = white,
                            bounded = false,
                            radius = 24.dp
                        ),
                        onClick = {
                            gameVM.closeSetupTimeScreen()
                        }
                    ),
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back",
                tint = white
            )
            Text(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .offset(x = 56.dp),
                text = "Time Control",
                fontSize = 20.sp,
                color = white
            )
            Row(
                modifier = Modifier
                    .height(40.dp)
                    .align(Alignment.CenterEnd)
                    .offset(x = (-8).dp)
                    .border(
                        width = 1.dp,
                        color = white,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .clip(RoundedCornerShape(20.dp))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(
                            color = white,
                            bounded = true,
                        ),
                        onClick = {

                        }
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.padding(start = 8.dp),
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add",
                    tint = white
                )
                Text(
                    modifier = Modifier.padding(start = 4.dp, end = 12.dp),
                    text = "Custom",
                    color = white
                )
            }
        }
        // List of TimeControls to select from, including custom times

        // bottom button
        ElevatedButton(
            modifier = Modifier
                .offset(y = (-8).dp)
                .align(Alignment.BottomCenter)
                .clip(shape = RoundedCornerShape(16.dp))
                .padding(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = green),
            elevation = ButtonDefaults.elevatedButtonElevation(
                defaultElevation = 8.dp,
                pressedElevation = 16.dp
            ),
            onClick = {

            }
        ){
            Text(
                modifier = Modifier.padding(start = 64.dp, end = 64.dp),
                text = "Setup Game",
                fontSize = 22.sp,
                color = white
            )
        }
    }

    BackHandler {
        gameVM.closeSetupTimeScreen()
    }

}
