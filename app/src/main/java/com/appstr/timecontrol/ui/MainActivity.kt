package com.appstr.timecontrol.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.appstr.timecontrol.ui.game.screens.GameScreen
import com.appstr.timecontrol.ui.game.viewmodels.GameViewModel
import com.appstr.timecontrol.ui.theme.TimeControlTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TimeControlTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GameScreen()
                }
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
