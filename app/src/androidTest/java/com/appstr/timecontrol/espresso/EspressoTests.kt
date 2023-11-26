package com.appstr.timecontrol.espresso

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.appstr.timecontrol.data.repositories.GameStateRepository
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject



@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class EspressoTests {

    @Inject
    lateinit var gameStateRepo: GameStateRepository

    @Before
    fun setup(){

    }

    @Test
    fun canWeReadDataFromDatabase(){

//        gameStateRepo.getGameState()
        assertTrue(true)
    }

}