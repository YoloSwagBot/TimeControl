package com.appstr.timecontrol.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.appstr.timecontrol.minute
import com.appstr.timecontrol.second
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


    fun decrementTimeByTurn(){
        Log.d("CarsonDebug", "decrementTimeByTurn - t==1 - X")
        _gameState.apply {
            when (value.turn){
                1 -> {
                    update { currentState ->
                        currentState.copy(
                            player1CurrentTime = (currentState.player1CurrentTime - second).coerceAtLeast(0),
                            isGameOver = currentState.player1CurrentTime <= 1,
                            isPaused = currentState.player1CurrentTime <= 1
                        )
                    }
                }
                2 -> {
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

