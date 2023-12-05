package com.appstr.timecontrol.domain.usecases.gamescreen

import com.appstr.timecontrol.domain.models.GameState
import com.appstr.timecontrol.domain.models.Player
import com.appstr.timecontrol.domain.models.isOver
import com.appstr.timecontrol.util.second
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class DecrementTimeUseCase @Inject constructor(
    val _gameState: MutableStateFlow<GameState>
) {
    operator fun invoke(){
        _gameState.update { game ->
            if (game.isOver()) return
            when (game.turn){
                Player.ONE -> {
                    game.copy(
                        player1CurrentTime = (game.player1CurrentTime - second).coerceAtLeast(0),
                        isPaused = game.player1CurrentTime <= 1
                    )
                }
                Player.TWO -> {
                    game.copy(
                        player2CurrentTime = (game.player2CurrentTime - second).coerceAtLeast(0),
                        isPaused = game.player2CurrentTime <= 1
                    )
                }
            }
        }
    }
}