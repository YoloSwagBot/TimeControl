package com.appstr.timecontrol.domain.usecases.dialogs.cancelgame

import com.appstr.timecontrol.domain.models.TimeControl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class OnDismissDialogCancelGameUseCase @Inject constructor() {
    operator fun invoke(
        _dialogCancelGameShowing: MutableStateFlow<TimeControl?>
    ){
        _dialogCancelGameShowing.update { null }
    }
}
