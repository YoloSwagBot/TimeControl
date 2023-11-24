package com.appstr.timecontrol.domain.usecases.gamescreen

import com.appstr.timecontrol.domain.models.GameState
import com.appstr.timecontrol.domain.models.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class OnClickPlayer2AreaUseCase @Inject constructor() {
    operator fun invoke(
        _gameState: MutableStateFlow<GameState?>,
        gs: GameState?
    ){
        gs?.let { game ->
            if (!game.isPaused && game.turn == Player.TWO){
                _gameState.update {
                    game.copy(
                        turn = Player.ONE,
                        player2MoveCount = game.player2MoveCount+1
                    )
                }
            }
        }
    }
}
