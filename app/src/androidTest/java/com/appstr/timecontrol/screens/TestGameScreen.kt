package com.appstr.timecontrol.screens

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.appstr.timecontrol.data.daos.GameStateDao
import com.appstr.timecontrol.data.repositories.GameStateRepository
import com.appstr.timecontrol.domain.models.formatTimeToText
import com.appstr.timecontrol.domain.models.isOver
import com.appstr.timecontrol.ui.MainActivity
import com.appstr.timecontrol.ui.game.viewmodels.GameViewModel
import com.appstr.timecontrol.util.GAMESCREEN_PLAYER1_TIME_LABEL
import com.appstr.timecontrol.util.GAMESCREEN_PLAYER2_TIME_LABEL
import com.appstr.timecontrol.util.defaultTimeControls
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@HiltAndroidTest
class TestGameScreen {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var repoDao: GameStateDao
    private lateinit var gameViewModel: GameViewModel


    @Before
    fun setup(){
        hiltRule.inject()

        repoDao = Mockito.mock(GameStateDao::class.java)
        gameViewModel = GameViewModel(
            repo = GameStateRepository(repoDao)
        )
    }

    @Test
    fun test_0emptyGameState(){
        gameViewModel.setNewGameUseCase(null)

        assertThat(gameViewModel.gameState.value.player1CurrentTime.formatTimeToText() == "?").isTrue()
        assertThat(gameViewModel.gameState.value.player2CurrentTime.formatTimeToText() == "?").isTrue()

        // fucking trash test cases
        composeRule.onNodeWithTag(GAMESCREEN_PLAYER1_TIME_LABEL, useUnmergedTree = true).assertTextEquals("?")
        composeRule.onNodeWithTag(GAMESCREEN_PLAYER2_TIME_LABEL, useUnmergedTree = true).assertTextEquals("?")
    }


    @OptIn(ExperimentalTestApi::class)
    @Test
    fun test_GameState_changes_labels(){
        gameViewModel.setNewGameUseCase(null)
        composeRule.activity.reportFullyDrawn()

        assertThat(gameViewModel.gameState.value.player1CurrentTime.formatTimeToText() == "?").isTrue()
        assertThat(gameViewModel.gameState.value.player2CurrentTime.formatTimeToText() == "?").isTrue()

//         trash test cases
//        composeRule.onNodeWithTag(GAMESCREEN_PLAYER1_TIME_LABEL, useUnmergedTree = true).assertTextEquals("?")
//        composeRule.onNodeWithTag(GAMESCREEN_PLAYER2_TIME_LABEL, useUnmergedTree = true).assertTextEquals("?")

//        gameViewModel.setNewGameUseCase(GameState(timeControl = defaultTimeControls[1])) // 2min, 1inc
//        composeRule.onRoot(useUnmergedTree = true).printToLog("Carson", 999)

//        runTest {
//            composeRule.waitUntilAtLeastOneExists(SemanticsMatcher.expectValue(), 10000L)
//            composeRule.onNodeWithTag(GAMESCREEN_PLAYER1_TIME_LABEL, useUnmergedTree = true).text("2:00")
//            composeRule.onNodeWithTag(GAMESCREEN_PLAYER2_TIME_LABEL, useUnmergedTree = true).assertTextEquals("2:00")
//        }
    }

    @Test
    fun test_1MinuteGame_60MovesEach(){
        composeRule.mainClock.autoAdvance = false

        gameViewModel.setNewGameUseCase(timeControl = defaultTimeControls[0])

        // start game, check it is NOT paused
        gameViewModel.onClickPausePlayUseCase()
        assertThat(gameViewModel.gameState.value.isPaused).isFalse()

        // simulate each player switching moves 10 times, 1 second apart.
        //      - should end with player 2 having 1 second left, player 1 loses on time
        repeat(60){
            gameViewModel.apply {
                decrementTimeUseCase()
                onClickPlayer1AreaUseCase()

                decrementTimeUseCase()
                onClickPlayer2AreaUseCase()
            }
        }

//        runTest {
//            composeRule.onNodeWithTag(GAMESCREEN_PLAYER1_TIME_LABEL, useUnmergedTree = true).assertTextEquals("0:00")
//        }
        println("p1Time: ${gameViewModel.gameState.value.player1CurrentTime} ---- p2Time: ${gameViewModel.gameState.value.player2CurrentTime}")

        assertThat(gameViewModel.gameState.value.isOver()).isTrue()

        assertThat(gameViewModel.gameState.value.player1CurrentTime == 0).isTrue()
        assertThat(gameViewModel.gameState.value.player2CurrentTime == 1000).isTrue()
    }

}
