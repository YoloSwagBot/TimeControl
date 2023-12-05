package com.appstr.timecontrol.domain.usecases.gamescreen

import com.appstr.timecontrol.domain.models.GameState
import com.appstr.timecontrol.domain.models.Player
import com.appstr.timecontrol.domain.models.isOver
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class OnClickPlayer1AreaUseCase @Inject constructor(
    val _gameState: MutableStateFlow<GameState>
) {
    operator fun invoke(){
        _gameState.value.let { if (it.isPaused || it.isOver() || it.turn != Player.ONE) return }

        _gameState.update { game ->
            game.copy(
                turn = Player.TWO,
                player1MoveCount = game.player1MoveCount+1,
                player1CurrentTime = game.player1CurrentTime + (1000*(game.timeControl?.increment ?: 0))
            )
        }
    }
}