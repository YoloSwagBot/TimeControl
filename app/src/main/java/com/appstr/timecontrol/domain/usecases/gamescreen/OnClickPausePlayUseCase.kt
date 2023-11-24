package com.appstr.timecontrol.domain.usecases.gamescreen

import com.appstr.timecontrol.domain.models.GameState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class OnClickPausePlayUseCase @Inject constructor(){
    operator fun invoke(
        _gameState: MutableStateFlow<GameState?>,
        gs: GameState?
    ){
        gs?.let { game ->
            _gameState.update { game.copy(isPaused = !game.isPaused) }
        }
    }
}