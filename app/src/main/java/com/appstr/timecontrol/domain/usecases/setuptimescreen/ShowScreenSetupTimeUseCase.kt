package com.appstr.timecontrol.domain.usecases.setuptimescreen

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class ShowScreenSetupTimeUseCase @Inject constructor() {
    operator fun invoke(
        _screenSetupTimeShowing: MutableStateFlow<Boolean>
    ){
        _screenSetupTimeShowing.update { true }
    }
}
