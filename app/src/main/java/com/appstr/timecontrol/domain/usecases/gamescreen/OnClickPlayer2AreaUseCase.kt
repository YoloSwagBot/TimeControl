package com.appstr.timecontrol.domain.usecases.gamescreen

import com.appstr.timecontrol.domain.models.Player
import com.appstr.timecontrol.domain.models.isNotOver
import com.appstr.timecontrol.ui.game.viewmodels.GameViewModel
import javax.inject.Inject

class OnClickPlayer2AreaUseCase @Inject constructor() {
    operator fun invoke(
        gameVM: GameViewModel
    ){
        gameVM.gState.apply {
            if (!isPaused && isNotOver() && turn == Player.TWO){
                turn = Player.ONE
                player2MoveCount += 1
            }
        }
    }
}
