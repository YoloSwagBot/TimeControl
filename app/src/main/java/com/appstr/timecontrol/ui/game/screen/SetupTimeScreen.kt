package com.appstr.timecontrol.ui.game.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.appstr.timecontrol.ui.game.dialog.DialogCheckCancelCurrentGame
import com.appstr.timecontrol.ui.game.model.TimeControl
import com.appstr.timecontrol.ui.game.model.toText
import com.appstr.timecontrol.ui.game.viewmodel.GameViewModel
import com.appstr.timecontrol.ui.theme.black
import com.appstr.timecontrol.ui.theme.green
import com.appstr.timecontrol.ui.theme.lightGreen
import com.appstr.timecontrol.ui.theme.lightGreen900
import com.appstr.timecontrol.ui.theme.white
import com.appstr.timecontrol.util.defaultSelectedItem
import com.appstr.timecontrol.util.defaultTimeControls


@Composable
fun SetupTimeScreen(
    gameVM: GameViewModel = viewModel()
){

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(color = white)
            .clickable(enabled = false) {}
    ) {
        // List
        var checkedPosition by rememberSaveable { mutableIntStateOf(defaultSelectedItem) }

        LazyColumn(
            contentPadding = PaddingValues(top = 56.dp, bottom = 80.dp)
        ){
            itemsIndexed(defaultTimeControls){ p, item ->
                TimeControlListItem(
                    item,
                    p == defaultTimeControls.lastIndex,
                    p == checkedPosition,
                    onClick = {
                        checkedPosition = p
                    }
                )
            }
        }

        Toolbar()

        ElevatedButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = (-8).dp)
                .clip(shape = RoundedCornerShape(16.dp))
                .padding(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = green),
            elevation = ButtonDefaults.elevatedButtonElevation(
                defaultElevation = 8.dp,
                pressedElevation = 16.dp
            ),
            onClick = {
                gameVM.showDialogCancelGame(
                    defaultTimeControls[checkedPosition]
                )
            }
        ){
            Text(
                modifier = Modifier.padding(start = 64.dp, end = 64.dp),
                text = "Setup Game",
                fontSize = 22.sp,
                color = white
            )
        }

        val dialogCancelGameShowing by gameVM.dialogCancelGameShowing.collectAsState()
        if (dialogCancelGameShowing != null){
            DialogCheckCancelCurrentGame()
        }
    }
    BackHandler {
        gameVM.closeSetupTimeScreen()
    }

}

// Toolbar, (1) back_button, (2) title, (3) +custom button
@Composable
private fun Toolbar(
    gameVM: GameViewModel = viewModel()
){
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
                    enabled = true,
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
                    enabled = true,
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
}


@Composable
private fun TimeControlListItem(
    timeControl: TimeControl,
    isLast: Boolean,
    selected: Boolean,
    onClick: () -> Unit
){
    val rowHeight = 48.dp
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(rowHeight + 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(rowHeight)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(
                        color = lightGreen,
                        bounded = true
                    ),
                    onClick = {
                        onClick.invoke()
                    }
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = timeControl.toText(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = black
            )
            RadioButton(
                selected = selected,
                onClick = {
                    onClick.invoke()
                }
            )
        }
        if (!isLast){
            Divider(thickness = 1.dp, color = lightGreen900)
        }
    }
}
