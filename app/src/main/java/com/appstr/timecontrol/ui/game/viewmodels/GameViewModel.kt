package com.appstr.timecontrol.ui.game.viewmodels

import android.app.Application
import android.util.Log
import androidx.compose.foundation.gestures.ModifierLocalScrollableContainerProvider.value
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.appstr.timecontrol.data.repositories.GameStateRepository
import com.appstr.timecontrol.domain.models.GameState
import com.appstr.timecontrol.domain.models.Player
import com.appstr.timecontrol.domain.models.TimeControl
import com.appstr.timecontrol.domain.models.toText
import com.appstr.timecontrol.domain.usecases.RetrieveGameStateUseCase
import com.appstr.timecontrol.domain.usecases.SaveGameStateUseCase
import com.appstr.timecontrol.domain.usecases.SetNewGameUseCase
import com.appstr.timecontrol.domain.usecases.dialogs.setplayerstime.SetPlayersTimeUseCase
import com.appstr.timecontrol.domain.usecases.gamescreen.DecrementTimeUseCase
import com.appstr.timecontrol.domain.usecases.gamescreen.OnClickPausePlayUseCase
import com.appstr.timecontrol.domain.usecases.gamescreen.OnClickPlayer1AreaUseCase
import com.appstr.timecontrol.domain.usecases.gamescreen.OnClickPlayer2AreaUseCase
import com.appstr.timecontrol.domain.usecases.gamescreen.PauseGameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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

    val setPlayersTimeUseCase: SetPlayersTimeUseCase,

    val retrieveGameStateUseCase: RetrieveGameStateUseCase,
    val saveGameStateUseCase: SaveGameStateUseCase

) : AndroidViewModel(appli), DefaultLifecycleObserver {

    // Game State object
    var gState = mutableStateOf<GameState>(GameState())
        get() = {

            return value
        }
        private set(value){

            field = value
        }

//    private val _gameState: MutableStateFlow<GameState?> = MutableStateFlow(null)
//    val gameState = _gameState.asStateFlow()



    fun decrementTimeByTurn() = Unit //decrementTimeUseCase(_gameState, gameState.value)

    fun pausePlayClicked() = Unit //onClickPausePlayUseCase(_gameState, gameState.value)

    fun onPlayer1Click() = Unit //onClickPlayer1AreaUseCase(_gameState, gameState.value)

    fun onPlayer2Click() = Unit //onClickPlayer2AreaUseCase(_gameState, gameState.value)

    // Pause (ie: back button to close app)
    fun pauseGame() = Unit //pauseGameUseCase(_gameState)

    // ====================================================================================

    fun setNewGame(timeControl: TimeControl){
        CoroutineScope(Dispatchers.Main).launch{
            Log.d("Carson", "GameViewModel ---- setNewGame(tc) ---- 00 ---- ${gState.timeControl?.toText()} ---- ${gState.hashCode()}")
            gState = gState.copy(timeControl = timeControl.copy())
            Log.d("Carson", "GameViewModel ---- setNewGame(tc) ---- 11 ---- ${gState.timeControl?.toText()} ---- ${gState.hashCode()}")
            Log.d("Carson", "GameViewModel ---- setNewGame(tc) ---- 22 ---- ${gState.timeControl?.toText()} ---- ${gState.hashCode()}")
            delay(2000)
            Log.d("Carson", "GameViewModel ---- setNewGame(tc) ---- 33 ---- ${gState.timeControl?.toText()} ---- ${gState.hashCode()}")
        }

//        Log.d("Carson", "GameViewModel ---- setNewGame(tc) ---- 12 ---- ${gameState.value?.timeControl?.toText() ?: -2}")
    }

    // ====================================================================================

//    // Close SetupTime screen
//    fun showSetupTimeScreen() = showScreenSetupTimeUseCase(_screenSetupTimeShowing)
//
//    fun closeSetupTimeScreen() = closeScreenSetupTimeUseCase(_screenSetupTimeShowing)
//
//    // ==================================================================================== Dialogs
//
//    // Dialog Cancel Game
//    fun showDialogCancelGame(timeControl: TimeControl) = showDialogCancelGameUseCase(timeControl, _gameState, _dialogCancelGameShowing)
//    fun onConfirmDialogCancelGame(){
//        onConfirmDialogCancelGameUseCase(
//            _dialogCancelGameShowing.value,
//            setNewGameUseCase,
//            _gameState,
//            _dialogCancelGameShowing,
//            _screenSetupTimeShowing
//        )
//    }
//    fun onDismissDialogCancelGame() = onDismissDialogCancelGameUseCase(_dialogCancelGameShowing)
//
//    // Dialog Edit Time Game
//    fun showDialogSetPlayersTime(player: Player) = showDialogSetPlayersTimeUseCase(player, _gameState, _dialogSetPlayersTimeShowing)
//    fun onDialogActionDismissSetPlayersTime() = onDismissDialogSetPlayersTimeUseCase(_dialogSetPlayersTimeShowing)
//    fun onConfirmDialogSetPlayersTime(
//        player: Player,
//        hours: Int,
//        minutes: Int,
//        seconds: Int
//    ) = onConfirmDialogSetPlayersTimeUseCase(player, hours, minutes, seconds, _gameState, _dialogSetPlayersTimeShowing)
    fun setPlayersTime(
        player: Player,
        hours: String,
        mins: String,
        secs: String
    ) = Unit //setPlayersTimeUseCase(player, hours, mins, secs, _gameState)

    // ====================================================================================
    // Holds state through lifecycle changes, ie: app destruction and creation

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)

        //retrieveGameStateUseCase(repo, viewModelScope, _gameState)
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)

        //saveGameStateUseCase(repo, viewModelScope, gameState.value)
    }


}

