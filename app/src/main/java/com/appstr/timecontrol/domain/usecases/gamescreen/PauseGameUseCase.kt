package com.appstr.timecontrol.domain.usecases.gamescreen

import com.appstr.timecontrol.ui.game.viewmodels.GameViewModel
import javax.inject.Inject

class PauseGameUseCase @Inject constructor() {
    operator fun invoke(
        gameVM: GameViewModel
    ){
        gameVM.gState.value = gameVM.gState.value.copy(isPaused = true)
    }
}
