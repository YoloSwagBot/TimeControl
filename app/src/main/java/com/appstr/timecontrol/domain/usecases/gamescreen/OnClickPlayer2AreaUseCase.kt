package com.appstr.timecontrol.domain.usecases.gamescreen

import com.appstr.timecontrol.domain.models.Player
import com.appstr.timecontrol.ui.game.viewmodels.GameViewModel
import javax.inject.Inject

class OnClickPlayer2AreaUseCase @Inject constructor() {
    operator fun invoke(
        gameVM: GameViewModel
    ){
        gameVM.gState.let { game ->
            if (!game.isPaused && game.turn == Player.TWO){
                gameVM.gState = game.copy(
                        turn = Player.ONE,
                        player2MoveCount = game.player2MoveCount+1
                    )
            }
        }
    }
}
