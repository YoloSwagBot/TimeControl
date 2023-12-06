package com.appstr.timecontrol.ui.game.viewmodels

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class GameViewModel @Inject constructor(
    val repo: GameStateRepository,
) : ViewModel(), DefaultLifecycleObserver {

    private val _gameState = MutableStateFlow(GameState())
    val gameState = _gameState.asStateFlow()

    val decrementTimeUseCase = DecrementTimeUseCase(_gameState)
    val onClickPausePlayUseCase = OnClickPausePlayUseCase(_gameState)
    val onClickPlayer1AreaUseCase = OnClickPlayer1AreaUseCase(_gameState)
    val onClickPlayer2AreaUseCase = OnClickPlayer2AreaUseCase(_gameState)
    val pauseGameUseCase = PauseGameUseCase(_gameState)

    val setNewGameUseCase = SetNewGameUseCase(_gameState)

    val setPlayersTimeUseCase = SetPlayersTimeUseCase(_gameState)

    val retrieveGameStateUseCase = RetrieveGameStateUseCase(_gameState)
    val saveGameStateUseCase = SaveGameStateUseCase(_gameState)

    // ====================================================================================
    // Holds state through lifecycle changes, ie: app destruction and creation

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)

        retrieveGameStateUseCase(repo, viewModelScope)
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)

        saveGameStateUseCase(repo, viewModelScope)
    }

}
