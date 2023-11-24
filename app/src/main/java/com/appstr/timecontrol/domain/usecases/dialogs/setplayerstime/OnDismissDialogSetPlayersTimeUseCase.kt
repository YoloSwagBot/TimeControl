package com.appstr.timecontrol.domain.usecases.dialogs.setplayerstime

import com.appstr.timecontrol.domain.models.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class OnDismissDialogSetPlayersTimeUseCase @Inject constructor() {
    operator fun invoke(
        _dialogSetPlayersTimeShowing: MutableStateFlow<Player?>
    ){
        _dialogSetPlayersTimeShowing.update { null }
    }
}
