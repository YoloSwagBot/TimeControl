package com.appstr.timecontrol.domain.usecases.gamescreen

import com.appstr.timecontrol.domain.models.Player
import com.appstr.timecontrol.domain.models.isNotOver
import com.appstr.timecontrol.ui.game.viewmodels.GameViewModel
import javax.inject.Inject

class OnClickPlayer2AreaUseCase @Inject constructor() {
    operator fun invoke(
        gameVM: GameViewModel
    ){
        gameVM.gState.value.let {
            if (!it.isPaused && it.isNotOver() && it.turn == Player.TWO){
                it.turn = Player.ONE
                it.player2MoveCount += 1
                if ((it.timeControl?.increment ?: 0) > 0){
                    it.player2CurrentTime += (1000*(it.timeControl?.increment ?: 0))
                }
            }
        }
    }
}
