package com.appstr.timecontrol.ui.game.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.appstr.timecontrol.util.second
import com.appstr.timecontrol.ui.game.model.GameState
import com.appstr.timecontrol.ui.game.model.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class GameViewModel(appli: Application) : AndroidViewModel(appli) {

    // Game State object
    private val _gameState = MutableStateFlow(GameState())
    val gameState = _gameState.asStateFlow()

    // Dialog actions(history screen), cancel/dismiss
    private val _dialogStateCancelGameShowing = MutableStateFlow(false)
    val dialogStateCancelGameShowing = _dialogStateCancelGameShowing.asStateFlow()

    fun decrementTimeByTurn(){
        _gameState.apply {
            when (value.turn){
                Player.ONE -> {
                    update { currentState ->
                        currentState.copy(
                            player1CurrentTime = (currentState.player1CurrentTime - second).coerceAtLeast(0),
                            isGameOver = currentState.player1CurrentTime <= 1,
                            isPaused = currentState.player1CurrentTime <= 1
                        )
                    }
                }
                Player.TWO -> {
                    update { currentState ->
                        currentState.copy(
                            player2CurrentTime = (currentState.player2CurrentTime - second).coerceAtLeast(0),
                            isGameOver = currentState.player1CurrentTime <= 1,
                            isPaused = currentState.player1CurrentTime <= 1
                        )
                    }
                }
            }
        }
    }

    fun pausePlayClicked(){
        _gameState.update { currentState ->
            currentState.copy(
                isPaused =  !currentState.isPaused
            )
        }
    }

    fun onPlayer1Click(){
        _gameState.value.apply {
            if (!this.isPaused && this.turn == Player.ONE){
                _gameState.update { currentState ->
                    currentState.copy(
                        turn = Player.TWO
                    )
                }
            }
        }
    }

    fun onPlayer2Click(){
        _gameState.value.apply {
            if (!this.isPaused && this.turn == Player.TWO){
                _gameState.update { currentState ->
                    currentState.copy(
                        turn = Player.ONE
                    )
                }
            }
        }
    }

    // Dialog Actions
    fun showDialogCancelGame(){
        _gameState.update { it.copy(isPaused = true) }
        _dialogStateCancelGameShowing.update { true }
    }
    fun onDialogActionConfirmCancelGame(){

    }
    fun onDialogActionDismissCancelGame(){
        _dialogStateCancelGameShowing.update { false }
    }
}

