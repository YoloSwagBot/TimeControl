package com.appstr.timecontrol.ui.game.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameSetupViewModel @Inject constructor(
    appli: Application
): AndroidViewModel(appli) {

}