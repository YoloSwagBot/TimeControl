package com.appstr.timecontrol.domain.usecases

import android.util.Log
import com.appstr.timecontrol.domain.models.GameState
import com.appstr.timecontrol.domain.models.TimeControl
import com.appstr.timecontrol.domain.models.toText
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class SetNewGameUseCase @Inject constructor() {
    operator fun invoke(
        timeControl: TimeControl,
        _gameState: MutableStateFlow<GameState?>
    ){
        Log.d("Carson", "SetNewGameUseCase ---- 00 ---- ${_gameState.value?.timeControl?.toText()}")
//        _gameState.emit(GameState(timeControl = timeControl))
        Log.d("Carson", "SetNewGameUseCase ---- 11 ---- ${_gameState.value?.timeControl?.toText()}")
    }
}
