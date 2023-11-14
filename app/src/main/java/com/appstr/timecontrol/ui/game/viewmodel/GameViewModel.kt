package com.appstr.timecontrol.ui.game.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.appstr.timecontrol.TimeControlApp
import com.appstr.timecontrol.domain.data.repo.GameRepo
import com.appstr.timecontrol.ui.game.model.GameEndReason
import com.appstr.timecontrol.ui.game.model.GameState
import com.appstr.timecontrol.ui.game.model.Player
import com.appstr.timecontrol.ui.game.model.TimeControl
import com.appstr.timecontrol.ui.game.model.areBothTimesAboveZero
import com.appstr.timecontrol.util.hour
import com.appstr.timecontrol.util.minute
import com.appstr.timecontrol.util.second
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


@HiltViewModel
class GameViewModel(appli: Application) : AndroidViewModel(appli), DefaultLifecycleObserver {

    // Repo of Game object(s)
    val repo: GameRepo = (appli as TimeControlApp).gameRepo

    // Game State object
    private val _gameState = MutableStateFlow<GameState?>(null) // initial value is read from db
    val gameState = _gameState.asStateFlow()

    // Dialog cancel current game
    private val _dialogCancelGameShowing = MutableStateFlow<TimeControl?>(null)
    val dialogCancelGameShowing = _dialogCancelGameShowing.asStateFlow()

    // Dialog set player's time
    private val _dialogSetPlayersTimeShowing = MutableStateFlow<Player?>(null)
    val dialogSetPlayersTimeShowing = _dialogSetPlayersTimeShowing.asStateFlow()

    // Dialog End Game
    private val _dialogEndGameShowing = MutableStateFlow<Player?>(null)
    val dialogEndGameShowing = _dialogEndGameShowing.asStateFlow()

    // Screen, SetupTime
    private val _screenSetupTimeShowing = MutableStateFlow(false)
    val screenSetupTimeShowing = _screenSetupTimeShowing.asStateFlow()



    fun decrementTimeByTurn(){

        gameState.value?.let { gs ->
            _gameState.update {
                when (gs.turn){
                    Player.ONE -> {
                        gs.copy(
                            player1CurrentTime = (gs.player1CurrentTime - second).coerceAtLeast(0),
                            gameEndReason = if (gs.gameEndReason == null && gs.player1CurrentTime <= 1) {
                                GameEndReason.RanOutOfTime.RanOutOfTime_PLAYER_2_WINS
                            }else{
                                gs.gameEndReason
                            },
                            isPaused = gs.player1CurrentTime <= 1
                        )
                    }
                    Player.TWO -> {
                        gs.copy(
                            player2CurrentTime = (gs.player2CurrentTime - second).coerceAtLeast(0),
                            gameEndReason =  if (gs.gameEndReason == null && gs.player2CurrentTime <= 1) {
                                GameEndReason.RanOutOfTime.RanOutOfTime_PLAYER_1_WINS
                            }else{
                                gs.gameEndReason
                            },
                            isPaused = gs.player2CurrentTime <= 1
                        )
                    }
                }
            }
        }

    }

    fun pausePlayClicked(){

        gameState.value?.let { gs ->
            _gameState.update { gs.copy(isPaused =  !gs.isPaused) }
        }

    }

    fun onPlayer1Click(){

        gameState.value?.let { gs ->
            if (!gs.isPaused && gs.turn == Player.ONE) {
                _gameState.update {
                    gs.copy(
                        turn = Player.TWO,
                        player1MoveCount = gs.player1MoveCount+1
                    )
                }
            }
        }

    }

    fun onPlayer2Click(){

        gameState.value?.let { gs ->
            if (!gs.isPaused && gs.turn == Player.TWO){
                _gameState.update {
                    gs.copy(
                        turn = Player.ONE,
                        player2MoveCount = gs.player2MoveCount+1
                    )
                }
            }
        }

    }

    // Pause (ie: back button to close app)
    fun pauseGame(){
        _gameState.update { it?.copy(isPaused = true) }
    }

    // ====================================================================================

    fun setNewGame(timeControl: TimeControl){
        _gameState.update { GameState(timeControl = timeControl) }

        _screenSetupTimeShowing.update { false }
    }

    // ====================================================================================

    // Close SetupTime screen
    fun showSetupTimeScreen(){
        _screenSetupTimeShowing.update { true }
    }

    fun closeSetupTimeScreen(){
        _screenSetupTimeShowing.update { false }
    }

    // ==================================================================================== Dialogs

    // Dialog Cancel Game
    fun showDialogCancelGame(
        timeControl: TimeControl,
    ){
        _gameState.update { it?.copy(isPaused = true) }

        _dialogCancelGameShowing.update { timeControl }
    }
    fun onDialogActionConfirmCancelGame(){
        dialogCancelGameShowing.value?.let { tc ->
            setNewGame(tc)
        }

        _dialogCancelGameShowing.update { null }
        _screenSetupTimeShowing.update { false }
    }
    fun onDialogActionDismissCancelGame(){
        _dialogCancelGameShowing.update { null }
    }

    // Dialog Edit Time Game
    fun showDialogSetPlayersTime(player: Player){
        _gameState.update { it?.copy(isPaused = true) }

        _dialogSetPlayersTimeShowing.update { player }
    }
    fun onDialogActionConfirmSetPlayersTime(
        player: Player,
        hours: Int,
        minutes: Int,
        seconds: Int
    ){
        _gameState.update { gs ->
            when (player){
                Player.ONE -> gs?.copy(
                    player1CurrentTime = (hours * hour) + (minutes * minute) + (seconds * second),
                    gameEndReason = if (gs.areBothTimesAboveZero()) null else gs.gameEndReason
                )
                Player.TWO -> gs?.copy(
                    player2CurrentTime = (hours * hour) + (minutes * minute) + (seconds * second),
                    gameEndReason = if (gs.areBothTimesAboveZero()) null else gs.gameEndReason
                )
            }
        }

        _dialogSetPlayersTimeShowing.update { null }
    }
    fun onDialogActionDismissSetPlayersTime(){
        _dialogSetPlayersTimeShowing.update { null }
    }

    fun showDialogEndGame(player: Player){
        _gameState.update { it?.copy(isPaused = true) }

        _dialogEndGameShowing.update { player }
    }
    fun onDialogEndGameActionConfirm(
        player: Player,
        gameEndReason: GameEndReason
    ){
        _gameState.update { it?.copy(gameEndReason = gameEndReason) }

        _dialogEndGameShowing.update { null }
    }
    fun onDialogEndGameActionCancel(){
        _dialogEndGameShowing.update { null }
    }


    // ====================================================================================
    // Holds state through lifecycle changes, ie: app destruction and creation

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)

        viewModelScope.launch {
            _gameState.update { repo.getGameState() }
        }
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)

        gameState.value?.let {
            viewModelScope.launch {
                repo.updateGameState(it)
            }
        }
    }


}

