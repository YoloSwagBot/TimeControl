package com.appstr.timecontrol.viewmodel

import android.app.Application
import android.util.TimeUtils
import androidx.lifecycle.AndroidViewModel
import com.appstr.timecontrol.hour
import com.appstr.timecontrol.minute
import com.appstr.timecontrol.second
import com.appstr.timecontrol.ui.model.TimeControl
import com.appstr.timecontrol.ui.theme.black
import com.appstr.timecontrol.ui.theme.lightGreen
import com.appstr.timecontrol.ui.theme.white
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.text.SimpleDateFormat
import java.util.Date


class GameViewModel(appli: Application) : AndroidViewModel(appli) {

    /** Player 1 */
    private val _player1TimeLeft = MutableStateFlow(59* second)
    val player1TimeLeft = _player1TimeLeft.asSharedFlow()
    private val _player1TextColor = MutableStateFlow(black)
    val player1TextColor = _player1TextColor.asSharedFlow()
    private val _player1BackgroundColor = MutableStateFlow(white)
    val player1BackgroundColor = _player1BackgroundColor.asSharedFlow()

    /** Player 2 */
    private val _player2TimeLeft = MutableStateFlow(59*minute)
    val player2TimeLeft = _player2TimeLeft.asSharedFlow()
    private val _player2TextColor = MutableStateFlow(black)
    val player2TextColor = _player2TextColor.asSharedFlow()
    private val _player2BackgroundColor = MutableStateFlow(white)
    val player2BackgroundColor = _player2BackgroundColor.asSharedFlow()



    fun onPlayer1Click(){

    }

    fun onPlayer2Click(){

    }

}

