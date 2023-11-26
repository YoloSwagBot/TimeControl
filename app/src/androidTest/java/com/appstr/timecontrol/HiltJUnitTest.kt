package com.appstr.timecontrol

import com.appstr.timecontrol.data.repositories.GameStateRepository
import com.appstr.timecontrol.domain.models.GameState
import com.appstr.timecontrol.util.defaultTimeControls
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject


@HiltAndroidTest
class HiltJUnitTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var gameStateRepo: GameStateRepository

    @Before
    fun setup(){
        hiltRule.inject()

        runTest {
            gameStateRepo.updateGameState(
                GameState(timeControl = defaultTimeControls[0])
            )
        }
    }


    @Test
    fun canWeReadDataFromDatabase() = runTest {
        launch {
            val gameState = gameStateRepo.getGameState()
            println("canWeReadDataFromDatabase() ---- gameState: $gameState")
            assertThat(gameState?.player1StartTime == 60000 && gameState.player2StartTime == 60000).isTrue()
        }
    }

}
