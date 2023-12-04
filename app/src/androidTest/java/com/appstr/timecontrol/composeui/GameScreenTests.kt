package com.appstr.timecontrol.composeui

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.appstr.timecontrol.util.GAMESCREEN_PLAYER1_MOVE_LABEL
import com.appstr.timecontrol.util.GAMESCREEN_PLAYER1_TIME_LABEL
import com.appstr.timecontrol.util.GAMESCREEN_PLAYER2_MOVE_LABEL
import com.appstr.timecontrol.util.GAMESCREEN_PLAYER2_TIME_LABEL
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
class GameScreenTests {

//    @get:Rule
//    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createComposeRule()

//    @get:Rule
//    val rule: RuleChain = RuleChain
//        .outerRule(hiltRule)
//        .around(composeTestRule)

//    @Inject
//    lateinit var gameStateRepo: GameStateRepository

    @Before
    fun setup(){
//        hiltRule.inject()
//        runTest {
//            gameStateRepo.deleteAllGames()
//        }

        composeTestRule.setContent {
//            GameScreen(Mockito.mock(GameViewModel::class.java))
        }

    }

    @Test
    fun testInitialGameScreenState() {
        composeTestRule.onNodeWithTag(GAMESCREEN_PLAYER1_TIME_LABEL).assertTextEquals("?")
        composeTestRule.onNodeWithTag(GAMESCREEN_PLAYER2_TIME_LABEL).assertTextEquals("?")
        composeTestRule.onNodeWithTag(GAMESCREEN_PLAYER1_MOVE_LABEL).assertTextEquals("0")
        composeTestRule.onNodeWithTag(GAMESCREEN_PLAYER2_MOVE_LABEL).assertTextEquals("0")
//        val gameState = gameStateRepo.getGameState()
//        assertThat(gameState).isNull()
//        assertThat(gameState?.player1MoveCount == 0 && gameState.player2MoveCount == 0).isTrue()
//        assertThat(gameState?.player1StartTime.formatTimeToText() == "?" && gameState?.player2StartTime.formatTimeToText() == "?").isTrue()
        assert(true)
    }

//    @Test
//    fun testSettingNewGameStateInRepoChangesUI() = runTest{
//        gameStateRepo.deleteAllGames()
//        // read current UI time labels == "?"
//        val gameState = gameStateRepo.getGameState()
//        assertThat(gameState).isNotNull()
//        assertThat(gameState?.turn == Player.ONE).isTrue()
//        assertThat(gameState?.player1MoveCount == 0 && gameState.player2MoveCount == 0).isTrue()
//        assertThat(gameState?.player1StartTime.formatTimeToText() == "?" && gameState?.player2StartTime.formatTimeToText() == "?").isTrue()
//        // set new GameState in Repo
//        gameStateRepo.updateGameState(GameState(timeControl = defaultTimeControls[0]))
//        assertThat(gameState).isNotNull()
//        // read current UI time labels == "xyz" time
//        assertThat(gameState).isNotNull()
//        assertThat(gameState?.turn == Player.ONE).isTrue()
//        assertThat(gameState?.player1MoveCount == 0 && gameState.player2MoveCount == 0).isTrue()
//        assertThat(gameState?.player1StartTime.formatTimeToText() == "?" && gameState?.player2StartTime.formatTimeToText() == "?").isTrue()
//    }

    @Test
    fun testClickingOnPlayer1AreaAndGameStateInRepo() = runTest {
        assert(false)
    }

    @Test
    fun testClickingOnPlayer2AreaWhenTurnIsPlayer1() = runTest {
        assert(false)
    }

    @Test
    fun testClickingOnPlayer1AreaAndThenPlayer2AreaAndRepoHasCorrectState() = runTest {
        assert(false)
    }

    @Test
    fun testClickingBackAndForth100TimesSetsCorrectMoveValues() = runTest {
        assert(false)
    }

    @Test
    fun testPlayer1ForceSystemClockAhead10HoursGameEndsAndCorrectGameStateInRepo() = runTest {
        assert(false)
    }

    @Test
    fun testPlayer2ForceSystemClockAhead10HoursGameEndsAndCorrectGameStateInRepo() = runTest {
        assert(false)
    }

}