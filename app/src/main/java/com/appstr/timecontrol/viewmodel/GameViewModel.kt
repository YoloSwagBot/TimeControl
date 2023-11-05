package com.appstr.timecontrol.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.appstr.timecontrol.minute
import com.appstr.timecontrol.ui.model.GameState
import com.appstr.timecontrol.ui.theme.black
import com.appstr.timecontrol.ui.theme.white
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class GameViewModel(appli: Application) : AndroidViewModel(appli) {

    // Game State object
    private val _gameState = MutableStateFlow(GameState())
    val gameState = _gameState.asStateFlow()

    /** Player 1 */
    private val _player1TimeLeft = MutableStateFlow(minute)
    val player1TimeLeft = _player1TimeLeft.asStateFlow()
    private val _player1TextColor = MutableStateFlow(black)
    val player1TextColor = _player1TextColor.asStateFlow()
    private val _player1BackgroundColor = MutableStateFlow(white)
    val player1BackgroundColor = _player1BackgroundColor.asStateFlow()

    /** Player 2 */
    private val _player2TimeLeft = MutableStateFlow(minute)
    val player2TimeLeft = _player2TimeLeft.asStateFlow()
    private val _player2TextColor = MutableStateFlow(black)
    val player2TextColor = _player2TextColor.asStateFlow()
    private val _player2BackgroundColor = MutableStateFlow(white)
    val player2BackgroundColor = _player2BackgroundColor.asStateFlow()

    fun decrementTimeByTurn(turn: Int){
        when (turn){
            1 -> {
                _gameState.value.player1CurrentTime-1000
            }
            2 -> {
                _gameState.value.player2CurrentTime-1000
            }
        }
    }

    fun pausePlayClicked(){
        _gameState.update { currentState ->
            currentState.copy(
                isPaused = !currentState.isPaused
            )
        }
    }

    fun onPlayer1Click(){
        _gameState.value.apply {
            if (!this.isPaused && this.turn == 1){
                _gameState.update { currentState ->
                    currentState.copy(
                        turn = 2
                    )
                }
            }
        }
    }

    fun onPlayer2Click(){
        _gameState.value.apply {
            if (!this.isPaused && this.turn == 2){
                _gameState.update { currentState ->
                    currentState.copy(
                        turn = 1
                    )
                }
            }
        }
    }

}

