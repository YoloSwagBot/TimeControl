package com.appstr.timecontrol.screens

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.appstr.timecontrol.data.daos.GameStateDao
import com.appstr.timecontrol.data.repositories.GameStateRepository
import com.appstr.timecontrol.domain.models.GameState
import com.appstr.timecontrol.domain.models.isOver
import com.appstr.timecontrol.domain.usecases.RetrieveGameStateUseCase
import com.appstr.timecontrol.domain.usecases.SaveGameStateUseCase
import com.appstr.timecontrol.domain.usecases.SetNewGameUseCase
import com.appstr.timecontrol.domain.usecases.dialogs.setplayerstime.SetPlayersTimeUseCase
import com.appstr.timecontrol.domain.usecases.gamescreen.DecrementTimeUseCase
import com.appstr.timecontrol.domain.usecases.gamescreen.OnClickPausePlayUseCase
import com.appstr.timecontrol.domain.usecases.gamescreen.OnClickPlayer1AreaUseCase
import com.appstr.timecontrol.domain.usecases.gamescreen.OnClickPlayer2AreaUseCase
import com.appstr.timecontrol.domain.usecases.gamescreen.PauseGameUseCase
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
            repo = GameStateRepository(repoDao),
            decrementTimeUseCase = DecrementTimeUseCase(),
            onClickPausePlayUseCase = OnClickPausePlayUseCase(),
            onClickPlayer1AreaUseCase = OnClickPlayer1AreaUseCase(),
            onClickPlayer2AreaUseCase = OnClickPlayer2AreaUseCase(),
            pauseGameUseCase = PauseGameUseCase(),
            setNewGameUseCase = SetNewGameUseCase(),
            setPlayersTimeUseCase = SetPlayersTimeUseCase(),
            retrieveGameStateUseCase = RetrieveGameStateUseCase(),
            saveGameStateUseCase = SaveGameStateUseCase()
        )
    }

    @Test
    fun testEmptyGameTimeLabels(){
        composeRule.onNodeWithTag(GAMESCREEN_PLAYER1_TIME_LABEL, useUnmergedTree = true).assertTextEquals("?")
        composeRule.onNodeWithTag(GAMESCREEN_PLAYER2_TIME_LABEL, useUnmergedTree = true).assertTextEquals("?")
    }

    @Test
    fun test_1MinuteGame_60MovesEach(){
        gameViewModel.gState = GameState(timeControl = defaultTimeControls[0])

        // start game, check it is NOT paused
        gameViewModel.onClickPausePlayUseCase(gameViewModel)
        assertThat(gameViewModel.gState.isPaused).isFalse()

        // simulate each player switching moves 10 times, 1 second apart.
        //      - should end with player 2 having 1 second left, player 1 loses on time
        repeat(60){
            gameViewModel.apply {
                decrementTimeUseCase(gameViewModel)
                onClickPlayer1AreaUseCase(gameViewModel)

                decrementTimeUseCase(gameViewModel)
                onClickPlayer2AreaUseCase(gameViewModel)
            }
        }

        println("p1Time: ${gameViewModel.gState.player1CurrentTime} ---- p2Time: ${gameViewModel.gState.player2CurrentTime}")

        assertThat(gameViewModel.gState.isOver()).isTrue()

        assertThat(gameViewModel.gState.player1CurrentTime == 0).isTrue()
        assertThat(gameViewModel.gState.player2CurrentTime == 1000).isTrue()
    }


}
