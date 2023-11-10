package com.appstr.timecontrol.ui.game.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.appstr.timecontrol.util.second
import com.appstr.timecontrol.ui.game.model.GameState
import com.appstr.timecontrol.ui.game.model.Player
import com.appstr.timecontrol.util.hour
import com.appstr.timecontrol.util.minute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class GameViewModel(appli: Application) : AndroidViewModel(appli) {

    // Game State object
    private val _gameState = MutableStateFlow(GameState())
    val gameState = _gameState.asStateFlow()

    // Dialog cancel current game
    private val _dialogCancelGameShowing = MutableStateFlow(false)
    val dialogCancelGameShowing = _dialogCancelGameShowing.asStateFlow()

    // Dialog set player's time
    private val _dialogSetPlayersTimeShowing = MutableStateFlow<Player?>(null)
    val dialogSetPlayersTimeShowing = _dialogSetPlayersTimeShowing.asStateFlow()



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

    // Dialog Cancel Game
    fun showDialogCancelGame(){
        _gameState.update { it.copy(isPaused = true) }
        _dialogCancelGameShowing.update { true }
    }
    fun onDialogActionConfirmCancelGame(){


    }
    fun onDialogActionDismissCancelGame(){
        _dialogCancelGameShowing.update { false }
    }

    // Dialog Edit Time Game
    fun showDialogSetPlayersTime(player: Player){
        _gameState.update { it.copy(isPaused = true) }
        _dialogSetPlayersTimeShowing.update { player }
    }
    fun onDialogActionConfirmSetPlayersTime(
        player: Player,
        hours: Int,
        minutes: Int,
        seconds: Int
    ){
        _gameState.update {
            when (player){
                Player.ONE -> it.copy(player1CurrentTime = (hours * hour) + (minutes * minute) + (seconds * second))
                Player.TWO -> it.copy(player2CurrentTime = (hours * hour) + (minutes * minute) + (seconds * second))
            }
        }
        // check formatting then set and close
        _dialogSetPlayersTimeShowing.update { null }
    }
    fun onDialogActionDismissSetPlayersTime(){
        _dialogSetPlayersTimeShowing.update { null }
    }


}

