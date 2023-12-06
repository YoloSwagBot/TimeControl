package com.appstr.timecontrol.screens

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.appstr.timecontrol.data.daos.GameStateDao
import com.appstr.timecontrol.data.repositories.GameStateRepository
import com.appstr.timecontrol.domain.models.GameState
import com.appstr.timecontrol.domain.models.Player
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
            repo = GameStateRepository(repoDao)
        )
    }

    @Test
    fun test_UseCase_DecrementPlayer1Time(){
        gameViewModel.setNewGameUseCase(timeControl = defaultTimeControls[defaultSelectedItem])

        val initialGameState = gameViewModel.gameState.value // default turn is Player1

        gameViewModel.decrementTimeUseCase()
        assertThat(initialGameState.player1CurrentTime == gameViewModel.gameState.value.player1CurrentTime + second).isTrue()
    }

    @Test
    fun test_UseCase_DecrementPlayer2Time(){
        gameViewModel.setNewGameUseCase(
            GameState(
                timeControl = defaultTimeControls[defaultSelectedItem],
                turn = Player.TWO
            )
        )


        val initialGameState = gameViewModel.gameState.value // default turn is Player1

        gameViewModel.decrementTimeUseCase()
        assertThat(initialGameState.player2CurrentTime == gameViewModel.gameState.value.player2CurrentTime + second).isTrue()
    }


    @Test
    fun test_UseCase_OnClickPausePlay_PauseToPlay(){
        gameViewModel.setNewGameUseCase(timeControl = defaultTimeControls[defaultSelectedItem])

        gameViewModel.onClickPausePlayUseCase()

        assertThat(gameViewModel.gameState.value.isPaused).isFalse()
    }

    @Test
    fun test_UseCase_OnClickPausePlay_PlayToPause(){
        gameViewModel.setNewGameUseCase(GameState(timeControl = defaultTimeControls[defaultSelectedItem], isPaused = false))

        gameViewModel.onClickPausePlayUseCase()

        assertThat(gameViewModel.gameState.value.isPaused).isTrue()
    }

    @Test
    fun test_UseCase_OnClickPausePlay_LetTimeRun_Player1(){
        gameViewModel.setNewGameUseCase(GameState(timeControl = defaultTimeControls[defaultSelectedItem]))
        val initialGameState = gameViewModel.gameState.value.copy()

        gameViewModel.onClickPausePlayUseCase()
        repeat(5){
            gameViewModel.decrementTimeUseCase()
        }

        assertThat(gameViewModel.gameState.value.isPaused).isFalse()
        assertThat(gameViewModel.gameState.value.player1CurrentTime == initialGameState.player1CurrentTime-5000).isTrue()
    }

    @Test
    fun test_UseCase_OnClickPausePlay_LetTimeRun_Player2(){
        gameViewModel.setNewGameUseCase(GameState(timeControl = defaultTimeControls[defaultSelectedItem], turn = Player.TWO))
        val initialGameState = gameViewModel.gameState.value.copy()

        gameViewModel.onClickPausePlayUseCase()
        repeat(5){
            gameViewModel.decrementTimeUseCase()
        }

        assertThat(gameViewModel.gameState.value.isPaused).isFalse()
        assertThat(gameViewModel.gameState.value.player2CurrentTime == initialGameState.player2CurrentTime-5000).isTrue()
    }

    @Test
    fun test_UseCase_OnPlayer1AreaClicked_gamePaused(){
        // test turn changes AND color of backgrounds change
        gameViewModel.setNewGameUseCase(
            GameState(
                timeControl = defaultTimeControls[defaultSelectedItem],
                isPaused = true
            )
        )
        val initialGameState = gameViewModel.gameState.value.copy()

        gameViewModel.onClickPlayer1AreaUseCase()
        gameViewModel.onClickPlayer1AreaUseCase()
        gameViewModel.onClickPlayer1AreaUseCase()


        assertThat(gameViewModel.gameState.value.turn == Player.ONE).isTrue()
        assertThat(gameViewModel.gameState.value.player1MoveCount == initialGameState.player1MoveCount).isTrue()
    }

    @Test
    fun test_UseCase_OnPlayer1AreaClicked(){
        // test turn changes AND color of backgrounds change
        gameViewModel.setNewGameUseCase(
            GameState(
                timeControl = defaultTimeControls[defaultSelectedItem],
                isPaused = false
            )
        )
        val initialGameState = gameViewModel.gameState.value.copy()

        gameViewModel.onClickPlayer1AreaUseCase()

        assertThat(gameViewModel.gameState.value.turn == Player.TWO).isTrue()
        assertThat(gameViewModel.gameState.value.player1MoveCount == initialGameState.player1MoveCount+1).isTrue()
    }

    @Test
    fun test_UseCase_OnPlayer1AreaClicked_withIncrement(){
        // test turn changes AND color of backgrounds change
        gameViewModel.setNewGameUseCase(
            GameState(
                timeControl = defaultTimeControls[defaultSelectedItem+1], // should be 10 mins + 5
                isPaused = false
            )
        )
        val initialGameState = gameViewModel.gameState.value.copy()

        gameViewModel.onClickPlayer1AreaUseCase()

        assertThat(gameViewModel.gameState.value.turn == Player.TWO).isTrue()
        assertThat(gameViewModel.gameState.value.player1MoveCount == initialGameState.player1MoveCount+1).isTrue()
        assertThat(gameViewModel.gameState.value.player1CurrentTime == initialGameState.player1CurrentTime+(1000*(gameViewModel.gameState.value.timeControl?.increment?:0))).isTrue()
    }

    @Test
    fun test_UseCase_OnPlayer2AreaClicked_gamePaused(){
        // test turn changes AND color of backgrounds change
        gameViewModel.setNewGameUseCase(
            GameState(
                timeControl = defaultTimeControls[defaultSelectedItem],
                turn = Player.TWO,
                isPaused = true
            )
        )
        val initialGameState = gameViewModel.gameState.value.copy()

        gameViewModel.onClickPlayer2AreaUseCase()
        gameViewModel.onClickPlayer2AreaUseCase()
        gameViewModel.onClickPlayer2AreaUseCase()


        assertThat(gameViewModel.gameState.value.turn == Player.TWO).isTrue()
        assertThat(gameViewModel.gameState.value.player2MoveCount == initialGameState.player2MoveCount).isTrue()
    }

    @Test
    fun test_UseCase_OnPlayer2AreaClicked(){
        // test turn changes AND color of backgrounds change
        gameViewModel.setNewGameUseCase(
            GameState(
                timeControl = defaultTimeControls[defaultSelectedItem],
                turn = Player.TWO,
                isPaused = false
            )
        )
        val initialGameState = gameViewModel.gameState.value.copy()

        gameViewModel.onClickPlayer2AreaUseCase()


        assertThat(gameViewModel.gameState.value.turn == Player.ONE).isTrue()
        assertThat(gameViewModel.gameState.value.player2MoveCount == initialGameState.player2MoveCount+1).isTrue()
    }

    @Test
    fun test_UseCase_OnPlayer2AreaClicked_withIncrement(){
        // test turn changes AND color of backgrounds change
        gameViewModel.setNewGameUseCase(
            GameState(
                timeControl = defaultTimeControls[defaultSelectedItem+1], // should be 10 mins + 5
                turn = Player.TWO,
                isPaused = false
            )
        )
        val initialGameState = gameViewModel.gameState.value.copy()

        gameViewModel.onClickPlayer2AreaUseCase()


        assertThat(gameViewModel.gameState.value.turn == Player.ONE).isTrue()
        assertThat(gameViewModel.gameState.value.player2MoveCount == initialGameState.player2MoveCount+1).isTrue()
        assertThat(gameViewModel.gameState.value.player2CurrentTime == initialGameState.player2CurrentTime+(1000*(gameViewModel.gameState.value.timeControl?.increment?:0))).isTrue()

    }

    @Test
    fun test_UseCase_PauseGame(){
        gameViewModel.setNewGameUseCase(
            GameState(timeControl = defaultTimeControls[defaultSelectedItem], isPaused = false)
        )

        gameViewModel.pauseGameUseCase()

        assertThat(gameViewModel.gameState.value.isPaused).isTrue()
    }


}

