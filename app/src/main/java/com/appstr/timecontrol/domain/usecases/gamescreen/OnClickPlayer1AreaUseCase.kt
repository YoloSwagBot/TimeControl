package com.appstr.timecontrol.domain.usecases.gamescreen

import com.appstr.timecontrol.domain.models.Player
import com.appstr.timecontrol.domain.models.isNotOver
import com.appstr.timecontrol.ui.game.viewmodels.GameViewModel
import javax.inject.Inject

class OnClickPlayer1AreaUseCase @Inject constructor() {
    operator fun invoke(
        gameVM: GameViewModel
    ){
        gameVM.gState.apply {
            if (!isPaused && isNotOver() &&  turn == Player.ONE){
                turn = Player.TWO
                player1MoveCount += 1
            }
        }
    }
}