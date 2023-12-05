package com.appstr.timecontrol.domain.usecases

import com.appstr.timecontrol.data.repositories.GameStateRepository
import com.appstr.timecontrol.domain.models.GameState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class RetrieveGameStateUseCase @Inject constructor(
    val _gameState: MutableStateFlow<GameState>
) {
    operator fun invoke(
        repo: GameStateRepository,
        scope: CoroutineScope,
    ){
        scope.launch(Dispatchers.IO) {
            _gameState.update { repo.getGameState() ?: GameState() }
        }
    }
}
