package com.appstr.timecontrol.ui.game.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appstr.timecontrol.data.repositories.GameStateRepository
import com.appstr.timecontrol.domain.models.GameState
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
import javax.inject.Inject


@HiltViewModel
class GameViewModel @Inject constructor(
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

) : ViewModel(), DefaultLifecycleObserver {

    var gState = mutableStateOf(GameState())

    // ====================================================================================
    // Holds state through lifecycle changes, ie: app destruction and creation

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)

        retrieveGameStateUseCase(repo, viewModelScope, this)
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)

        saveGameStateUseCase(repo, viewModelScope, gState.value)
    }

}
