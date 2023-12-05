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
        timeControl: TimeControl,
    ){
//        Log.d("Carson", "SetNewGameUseCase ---- 00 ---- ${_gameState.value?.timeControl?.toText()}")
        _gameState.update { GameState(timeControl = timeControl) }
//        Log.d("Carson", "SetNewGameUseCase ---- 11 ---- ${_gameState.value?.timeControl?.toText()}")
    }
}
