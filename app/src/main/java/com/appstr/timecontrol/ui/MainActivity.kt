package com.appstr.timecontrol.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.appstr.timecontrol.ui.game.viewmodels.GameViewModel
import com.appstr.timecontrol.ui.theme.TimeControlTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TimeControlTheme {
                Navigation()
            }
        }

        // used to save/recreate game_state during app destruction/creation
        val gameVM: GameViewModel by viewModels()
        lifecycle.addObserver(gameVM)
    }

    override fun onDestroy() {
        super.onDestroy()

        // used to save/recreate game_state during app destruction/creation
        val gameVM: GameViewModel by viewModels()
        lifecycle.removeObserver(gameVM)
    }

}
