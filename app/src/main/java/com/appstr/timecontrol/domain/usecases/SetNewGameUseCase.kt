package com.appstr.timecontrol.domain.usecases

import com.appstr.timecontrol.domain.models.GameState
import com.appstr.timecontrol.domain.models.TimeControl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class SetNewGameUseCase @Inject constructor(
    val _gameState: MutableStateFlow<GameState>
) {
    operator fun invoke(
        timeControl: TimeControl?,
    ){
        _gameState.update { GameState(timeControl = timeControl) }
    }

    operator fun invoke(
        gameState: GameState
    ){
        _gameState.update { gameState }
    }
}
