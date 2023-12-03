package com.appstr.timecontrol.domain.usecases

import com.appstr.timecontrol.domain.models.GameState
import com.appstr.timecontrol.domain.models.TimeControl
import com.appstr.timecontrol.ui.game.viewmodels.GameViewModel
import javax.inject.Inject

class SetNewGameUseCase @Inject constructor() {
    operator fun invoke(
        timeControl: TimeControl,
        gameVM: GameViewModel
    ){
//        Log.d("Carson", "SetNewGameUseCase ---- 00 ---- ${_gameState.value?.timeControl?.toText()}")
        gameVM.gState = GameState(timeControl = timeControl)
//        Log.d("Carson", "SetNewGameUseCase ---- 11 ---- ${_gameState.value?.timeControl?.toText()}")
    }
}
