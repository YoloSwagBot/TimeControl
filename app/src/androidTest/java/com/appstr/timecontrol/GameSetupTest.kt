package com.appstr.timecontrol

import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class GameSetupTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    // ========================================================================================== GameState_tests

    @Test
    fun checkUI_GameState_isNull() {

    }

    @Test
    fun check_changeGameState_fromNull(){

    }

    @Test
    fun check_changeGameState_fromCurrentGameState(){

    }

    // ========================================================================================== Dialog_tests

    @Test
    fun check_gameTimeChangedOnDialog_Player1(){

    }

    @Test
    fun check_gameTimeChangedOnDialog_Player2(){

    }

    @Test
    fun check_gameEndsWhenTimeRunsOut() {

    }

}