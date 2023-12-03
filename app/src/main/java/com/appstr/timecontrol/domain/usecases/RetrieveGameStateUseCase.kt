package com.appstr.timecontrol.domain.usecases

import com.appstr.timecontrol.data.repositories.GameStateRepository
import com.appstr.timecontrol.domain.models.GameState
import com.appstr.timecontrol.ui.game.viewmodels.GameViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class RetrieveGameStateUseCase @Inject constructor() {
    operator fun invoke(
        repo: GameStateRepository,
        scope: CoroutineScope,
        gameVM: GameViewModel
    ){
        scope.launch(Dispatchers.IO) {
            gameVM.gState = repo.getGameState() ?: GameState()
        }
    }
}
