package com.appstr.timecontrol.domain.usecases.gamescreen

import com.appstr.timecontrol.domain.models.Player
import com.appstr.timecontrol.ui.game.viewmodels.GameViewModel
import javax.inject.Inject

class OnClickPlayer1AreaUseCase @Inject constructor() {
    operator fun invoke(
        gameVM: GameViewModel
    ){
        gameVM.gState.let { game ->
            if (!game.isPaused && game.turn == Player.ONE) {
                gameVM.gState = game.copy(
                        turn = Player.TWO,
                        player1MoveCount = game.player1MoveCount+1
                    )
            }
        }
    }
}