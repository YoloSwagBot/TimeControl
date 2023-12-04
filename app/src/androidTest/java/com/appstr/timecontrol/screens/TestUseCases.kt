package com.appstr.timecontrol.screens

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.appstr.timecontrol.data.daos.GameStateDao
import com.appstr.timecontrol.data.repositories.GameStateRepository
import com.appstr.timecontrol.domain.models.GameState
import com.appstr.timecontrol.domain.models.Player
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
import com.appstr.timecontrol.util.defaultSelectedItem
import com.appstr.timecontrol.util.defaultTimeControls
import com.appstr.timecontrol.util.second
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito


@HiltAndroidTest
class TestUseCases {

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
    fun test_UseCase_DecrementPlayer1Time(){
        gameViewModel.gState = GameState(timeControl = defaultTimeControls[defaultSelectedItem]).apply {
            turn = Player.ONE
        }
        val initialGameState = gameViewModel.gState // default turn is Player1

        gameViewModel.decrementTimeUseCase(gameVM = gameViewModel)
        assertThat(initialGameState.player1CurrentTime == gameViewModel.gState.player1CurrentTime + second).isTrue()
    }

    @Test
    fun test_UseCase_DecrementPlayer2Time(){
        gameViewModel.gState = GameState(timeControl = defaultTimeControls[defaultSelectedItem]).apply {
            turn = Player.TWO
        }
        val initialGameState = gameViewModel.gState // default turn is Player1

        gameViewModel.decrementTimeUseCase(gameVM = gameViewModel)
        assertThat(initialGameState.player2CurrentTime == gameViewModel.gState.player2CurrentTime + second).isTrue()
    }


    @Test
    fun test_UseCase_OnClickPausePlay_PauseToPlay(){
        gameViewModel.gState = GameState(timeControl = defaultTimeControls[defaultSelectedItem])

        gameViewModel.onClickPausePlayUseCase(gameViewModel)

        assertThat(gameViewModel.gState.isPaused).isFalse()
    }

    @Test
    fun test_UseCase_OnClickPausePlay_PlayToPause(){
        gameViewModel.gState = GameState(timeControl = defaultTimeControls[defaultSelectedItem], isPaused = false)

        gameViewModel.onClickPausePlayUseCase(gameViewModel)

        assertThat(gameViewModel.gState.isPaused).isTrue()
    }

    @Test
    fun test_UseCase_OnClickPausePlay_LetTimeRun_Player1(){
        gameViewModel.gState = GameState(timeControl = defaultTimeControls[defaultSelectedItem])
        val initialGameState = gameViewModel.gState.copy()

        gameViewModel.onClickPausePlayUseCase(gameViewModel)
        repeat(5){
            gameViewModel.decrementTimeUseCase(gameViewModel)
        }

        assertThat(gameViewModel.gState.isPaused).isFalse()
        assertThat(gameViewModel.gState.player1CurrentTime == initialGameState.player1CurrentTime-5000).isTrue()
    }

    @Test
    fun test_UseCase_OnClickPausePlay_LetTimeRun_Player2(){
        gameViewModel.gState = GameState(timeControl = defaultTimeControls[defaultSelectedItem], turn = Player.TWO)
        val initialGameState = gameViewModel.gState.copy()

        gameViewModel.onClickPausePlayUseCase(gameViewModel)
        repeat(5){
            gameViewModel.decrementTimeUseCase(gameViewModel)
        }

        assertThat(gameViewModel.gState.isPaused).isFalse()
        assertThat(gameViewModel.gState.player2CurrentTime == initialGameState.player2CurrentTime-5000).isTrue()
    }

    @Test
    fun test_UseCase_OnPlayer1AreaClicked_gamePaused(){
        // test turn changes AND color of backgrounds change
        gameViewModel.gState = GameState(
            timeControl = defaultTimeControls[defaultSelectedItem],
            isPaused = true
        )
        val initialGameState = gameViewModel.gState.copy()

        gameViewModel.onClickPlayer1AreaUseCase(gameViewModel)
        gameViewModel.onClickPlayer1AreaUseCase(gameViewModel)
        gameViewModel.onClickPlayer1AreaUseCase(gameViewModel)


        assertThat(gameViewModel.gState.turn == Player.ONE).isTrue()
        assertThat(gameViewModel.gState.player1MoveCount == initialGameState.player1MoveCount).isTrue()
    }

    @Test
    fun test_UseCase_OnPlayer1AreaClicked(){
        // test turn changes AND color of backgrounds change
        gameViewModel.gState = GameState(
            timeControl = defaultTimeControls[defaultSelectedItem],
            isPaused = false
        )
        val initialGameState = gameViewModel.gState.copy()

        gameViewModel.onClickPlayer1AreaUseCase(gameViewModel)

        assertThat(gameViewModel.gState.turn == Player.TWO).isTrue()
        assertThat(gameViewModel.gState.player1MoveCount == initialGameState.player1MoveCount+1).isTrue()
    }

    @Test
    fun test_UseCase_OnPlayer1AreaClicked_withIncrement(){
        // test turn changes AND color of backgrounds change
        gameViewModel.gState = GameState(
            timeControl = defaultTimeControls[defaultSelectedItem+1], // should be 10 mins + 5
            isPaused = false
        )
        val initialGameState = gameViewModel.gState.copy()

        gameViewModel.onClickPlayer1AreaUseCase(gameViewModel)

        assertThat(gameViewModel.gState.turn == Player.TWO).isTrue()
        assertThat(gameViewModel.gState.player1MoveCount == initialGameState.player1MoveCount+1).isTrue()
        assertThat(gameViewModel.gState.player1CurrentTime == initialGameState.player1CurrentTime+(1000*(gameViewModel.gState.timeControl?.increment?:0))).isTrue()
    }

    @Test
    fun test_UseCase_OnPlayer2AreaClicked_gamePaused(){
        // test turn changes AND color of backgrounds change
        gameViewModel.gState = GameState(
            timeControl = defaultTimeControls[defaultSelectedItem],
            turn = Player.TWO,
            isPaused = true
        )
        val initialGameState = gameViewModel.gState.copy()

        gameViewModel.onClickPlayer2AreaUseCase(gameViewModel)
        gameViewModel.onClickPlayer2AreaUseCase(gameViewModel)
        gameViewModel.onClickPlayer2AreaUseCase(gameViewModel)


        assertThat(gameViewModel.gState.turn == Player.TWO).isTrue()
        assertThat(gameViewModel.gState.player2MoveCount == initialGameState.player2MoveCount).isTrue()
    }

    @Test
    fun test_UseCase_OnPlayer2AreaClicked(){
        // test turn changes AND color of backgrounds change
        gameViewModel.gState = GameState(
            timeControl = defaultTimeControls[defaultSelectedItem],
            turn = Player.TWO,
            isPaused = false
        )
        val initialGameState = gameViewModel.gState.copy()

        gameViewModel.onClickPlayer2AreaUseCase(gameViewModel)


        assertThat(gameViewModel.gState.turn == Player.ONE).isTrue()
        assertThat(gameViewModel.gState.player2MoveCount == initialGameState.player2MoveCount+1).isTrue()
    }

    @Test
    fun test_UseCase_OnPlayer2AreaClicked_withIncrement(){
        // test turn changes AND color of backgrounds change
        gameViewModel.gState = GameState(
            timeControl = defaultTimeControls[defaultSelectedItem+1], // should be 10 mins + 5
            turn = Player.TWO,
            isPaused = false
        )
        val initialGameState = gameViewModel.gState.copy()

        gameViewModel.onClickPlayer2AreaUseCase(gameViewModel)


        assertThat(gameViewModel.gState.turn == Player.ONE).isTrue()
        assertThat(gameViewModel.gState.player2MoveCount == initialGameState.player2MoveCount+1).isTrue()
        assertThat(gameViewModel.gState.player2CurrentTime == initialGameState.player2CurrentTime+(1000*(gameViewModel.gState.timeControl?.increment?:0))).isTrue()

    }

    @Test
    fun test_UseCase_PauseGame(){
        gameViewModel.gState = GameState(timeControl = defaultTimeControls[defaultSelectedItem], isPaused = false)

        gameViewModel.pauseGameUseCase(gameViewModel)

        assertThat(gameViewModel.gState.isPaused).isTrue()
    }


}

