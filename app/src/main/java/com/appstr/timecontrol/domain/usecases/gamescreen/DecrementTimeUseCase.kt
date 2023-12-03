package com.appstr.timecontrol.domain.usecases.gamescreen

import com.appstr.timecontrol.domain.models.Player
import com.appstr.timecontrol.ui.game.viewmodels.GameViewModel
import com.appstr.timecontrol.util.second
import javax.inject.Inject

class DecrementTimeUseCase @Inject constructor() {
    operator fun invoke(
        gameVM: GameViewModel
    ){
        gameVM.gState.let { game ->
            when (game.turn){
                Player.ONE -> {
                    gameVM.gState = game.copy(
                        player1CurrentTime = (game.player1CurrentTime - second).coerceAtLeast(0),
                        isPaused = game.player1CurrentTime <= 1
                    )
                }
                Player.TWO -> {
                    gameVM.gState = game.copy(
                        player2CurrentTime = (game.player2CurrentTime - second).coerceAtLeast(0),
                        isPaused = game.player2CurrentTime <= 1
                    )
                }
            }
        }
    }
}