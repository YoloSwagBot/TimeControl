package com.appstr.timecontrol.domain.usecases.gamescreen

import com.appstr.timecontrol.domain.models.isOver
import com.appstr.timecontrol.ui.game.viewmodels.GameViewModel
import javax.inject.Inject

class OnClickPausePlayUseCase @Inject constructor(){
    operator fun invoke(
        gameVM: GameViewModel
    ){
        if (gameVM.gState.value.isOver()) return
//        Log.d("Carson", "OnClickPausePlayUseCase ---- ")
        gameVM.gState.value = gameVM.gState.value.copy(isPaused = !gameVM.gState.value.isPaused)
    }
}