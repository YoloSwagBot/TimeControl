package com.appstr.timecontrol.domain.usecases.gamescreen

import com.appstr.timecontrol.ui.game.viewmodels.GameViewModel
import javax.inject.Inject

class OnClickPausePlayUseCase @Inject constructor(){
    operator fun invoke(
        gameVM: GameViewModel
    ){
        gameVM.gState.isPaused = !gameVM.gState.isPaused
    }
}