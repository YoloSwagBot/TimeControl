package com.appstr.timecontrol.domain.usecases.gamescreen

import com.appstr.timecontrol.domain.models.GameState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class PauseGameUseCase @Inject constructor() {
    operator fun invoke(
        _gameState: MutableStateFlow<GameState?>
    ){
        _gameState.update { it?.copy(isPaused = true) }
    }
}