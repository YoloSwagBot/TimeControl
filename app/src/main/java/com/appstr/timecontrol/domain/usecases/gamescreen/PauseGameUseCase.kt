package com.appstr.timecontrol.domain.usecases.gamescreen

import com.appstr.timecontrol.domain.models.GameState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class PauseGameUseCase @Inject constructor(
    val _gameState: MutableStateFlow<GameState>
) {
    operator fun invoke(){
        _gameState.update { game ->
            game.copy(isPaused = true)
        }
    }
}
