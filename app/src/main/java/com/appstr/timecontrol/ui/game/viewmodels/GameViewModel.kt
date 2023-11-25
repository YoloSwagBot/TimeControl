package com.appstr.timecontrol.ui.game.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.appstr.timecontrol.data.repositories.GameStateRepository
import com.appstr.timecontrol.domain.models.GameState
import com.appstr.timecontrol.domain.models.Player
import com.appstr.timecontrol.domain.models.TimeControl
import com.appstr.timecontrol.domain.usecases.RetrieveGameStateUseCase
import com.appstr.timecontrol.domain.usecases.SaveGameStateUseCase
import com.appstr.timecontrol.domain.usecases.SetNewGameUseCase
import com.appstr.timecontrol.domain.usecases.dialogs.cancelgame.OnConfirmDialogCancelGameUseCase
import com.appstr.timecontrol.domain.usecases.dialogs.cancelgame.OnDismissDialogCancelGameUseCase
import com.appstr.timecontrol.domain.usecases.dialogs.cancelgame.ShowDialogCancelGameUseCase
import com.appstr.timecontrol.domain.usecases.dialogs.setplayerstime.OnConfirmDialogSetPlayersTimeUseCase
import com.appstr.timecontrol.domain.usecases.dialogs.setplayerstime.OnDismissDialogSetPlayersTimeUseCase
import com.appstr.timecontrol.domain.usecases.dialogs.setplayerstime.ShowDialogSetPlayersTimeUseCase
import com.appstr.timecontrol.domain.usecases.gamescreen.DecrementTimeUseCase
import com.appstr.timecontrol.domain.usecases.gamescreen.OnClickPausePlayUseCase
import com.appstr.timecontrol.domain.usecases.gamescreen.OnClickPlayer1AreaUseCase
import com.appstr.timecontrol.domain.usecases.gamescreen.OnClickPlayer2AreaUseCase
import com.appstr.timecontrol.domain.usecases.gamescreen.PauseGameUseCase
import com.appstr.timecontrol.domain.usecases.setuptimescreen.CloseScreenSetupTimeUseCase
import com.appstr.timecontrol.domain.usecases.setuptimescreen.ShowScreenSetupTimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class GameViewModel @Inject constructor(
    appli: Application,
    val repo: GameStateRepository,

    val decrementTimeUseCase: DecrementTimeUseCase,
    val onClickPausePlayUseCase: OnClickPausePlayUseCase,
    val onClickPlayer1AreaUseCase: OnClickPlayer1AreaUseCase,
    val onClickPlayer2AreaUseCase: OnClickPlayer2AreaUseCase,
    val pauseGameUseCase: PauseGameUseCase,

    val setNewGameUseCase: SetNewGameUseCase,
    val showScreenSetupTimeUseCase: ShowScreenSetupTimeUseCase,
    val closeScreenSetupTimeUseCase: CloseScreenSetupTimeUseCase,

    val showDialogSetPlayersTimeUseCase: ShowDialogSetPlayersTimeUseCase,
    val onConfirmDialogSetPlayersTimeUseCase: OnConfirmDialogSetPlayersTimeUseCase,
    val onDismissDialogSetPlayersTimeUseCase: OnDismissDialogSetPlayersTimeUseCase,

    val showDialogCancelGameUseCase: ShowDialogCancelGameUseCase,
    val onDismissDialogCancelGameUseCase: OnDismissDialogCancelGameUseCase,
    val onConfirmDialogCancelGameUseCase: OnConfirmDialogCancelGameUseCase,

    val retrieveGameStateUseCase: RetrieveGameStateUseCase,
    val saveGameStateUseCase: SaveGameStateUseCase

) : AndroidViewModel(appli), DefaultLifecycleObserver {

    // Game State object
    private val _gameState = MutableStateFlow<GameState?>(null)
    val gameState = _gameState.asStateFlow()

    // Dialog cancel current game
    private val _dialogCancelGameShowing = MutableStateFlow<TimeControl?>(null)
    val dialogCancelGameShowing = _dialogCancelGameShowing.asStateFlow()

    // Dialog set player's time
    private val _dialogSetPlayersTimeShowing = MutableStateFlow<Player?>(null)
    val dialogSetPlayersTimeShowing = _dialogSetPlayersTimeShowing.asStateFlow()

    // Screen, SetupTime
    private val _screenSetupTimeShowing = MutableStateFlow(false)
    val screenSetupTimeShowing = _screenSetupTimeShowing.asStateFlow()



    fun decrementTimeByTurn() = decrementTimeUseCase(_gameState, gameState.value)

    fun pausePlayClicked() = onClickPausePlayUseCase(_gameState, gameState.value)

    fun onPlayer1Click() = onClickPlayer1AreaUseCase(_gameState, gameState.value)

    fun onPlayer2Click() = onClickPlayer2AreaUseCase(_gameState, gameState.value)

    // Pause (ie: back button to close app)
    fun pauseGame() = pauseGameUseCase(_gameState)

    // ====================================================================================

    fun setNewGame(timeControl: TimeControl){
        setNewGameUseCase(timeControl, _gameState)
        closeScreenSetupTimeUseCase(_screenSetupTimeShowing)
    }

    // ====================================================================================

    // Close SetupTime screen
    fun showSetupTimeScreen() = showScreenSetupTimeUseCase(_screenSetupTimeShowing)

    fun closeSetupTimeScreen() = closeScreenSetupTimeUseCase(_screenSetupTimeShowing)

    // ==================================================================================== Dialogs

    // Dialog Cancel Game
    fun showDialogCancelGame(
        timeControl: TimeControl,
    ){
        showDialogCancelGameUseCase(timeControl, _gameState, _dialogCancelGameShowing)
    }
    fun onConfirmDialogCancelGame(){
        onConfirmDialogCancelGameUseCase(
            _dialogCancelGameShowing.value,
            setNewGameUseCase,
            _gameState,
            _dialogCancelGameShowing,
            _screenSetupTimeShowing
        )
    }
    fun onDismissDialogCancelGame() = onDismissDialogCancelGameUseCase(_dialogCancelGameShowing)

    // Dialog Edit Time Game
    fun showDialogSetPlayersTime(player: Player) = showDialogSetPlayersTimeUseCase(player, _gameState, _dialogSetPlayersTimeShowing)
    fun onDialogActionDismissSetPlayersTime() = onDismissDialogSetPlayersTimeUseCase(_dialogSetPlayersTimeShowing)
    fun onConfirmDialogSetPlayersTime(
        player: Player,
        hours: Int,
        minutes: Int,
        seconds: Int
    ) = onConfirmDialogSetPlayersTimeUseCase(player, hours, minutes, seconds, _gameState, _dialogSetPlayersTimeShowing)


    // ====================================================================================
    // Holds state through lifecycle changes, ie: app destruction and creation

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)

        retrieveGameStateUseCase(repo, viewModelScope, _gameState)
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)

        saveGameStateUseCase(repo, viewModelScope, gameState.value)
    }


}

