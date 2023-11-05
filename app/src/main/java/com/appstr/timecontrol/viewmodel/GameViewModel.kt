package com.appstr.timecontrol.viewmodel

import android.app.Application
import android.util.TimeUtils
import androidx.lifecycle.AndroidViewModel
import com.appstr.timecontrol.ui.model.TimeControl
import com.appstr.timecontrol.ui.theme.lightGreen
import com.appstr.timecontrol.ui.theme.white
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow


class GameViewModel(appli: Application) : AndroidViewModel(appli) {

    /** Player 1 */
    private val _player1TimeLeft = MutableStateFlow(0)
    val player1TimeLeft = _player1TimeLeft.asSharedFlow()
    private val _player1TextColor = MutableStateFlow(white)
    val player1TextColor = _player1TextColor.asSharedFlow()
    private val _player1BackgroundColor = MutableStateFlow(lightGreen)
    val player1BackgroundColor = _player1TextColor.asSharedFlow()

    /** Player 2 */
    private val _player2TimeLeft = MutableStateFlow(0)
    val player2TimeLeft = _player2TimeLeft.asSharedFlow()
    private val _player2TextColor = MutableStateFlow(white)
    val player2TextColor = _player2TextColor.asSharedFlow()
    private val _player2BackgroundColor = MutableStateFlow(lightGreen)
    val player2BackgroundColor = _player2TextColor.asSharedFlow()



    fun onPlayer1Click(){

    }

    fun onPlayer2Click(){

    }

    private fun formatTimeToText(timeControl: TimeControl, currentTime: Int): String {


        return ""
    }


}

