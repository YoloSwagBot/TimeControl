package com.appstr.timecontrol.screens

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.appstr.timecontrol.data.daos.GameStateDao
import com.appstr.timecontrol.data.repositories.GameStateRepository
import com.appstr.timecontrol.domain.usecases.RetrieveGameStateUseCase
import com.appstr.timecontrol.domain.usecases.SaveGameStateUseCase
import com.appstr.timecontrol.domain.usecases.SetNewGameUseCase
import com.appstr.timecontrol.domain.usecases.dialogs.setplayerstime.SetPlayersTimeUseCase
import com.appstr.timecontrol.domain.usecases.gamescreen.DecrementTimeUseCase
import com.appstr.timecontrol.domain.usecases.gamescreen.OnClickPausePlayUseCase
import com.appstr.timecontrol.domain.usecases.gamescreen.OnClickPlayer1AreaUseCase
import com.appstr.timecontrol.domain.usecases.gamescreen.OnClickPlayer2AreaUseCase
import com.appstr.timecontrol.domain.usecases.gamescreen.PauseGameUseCase
import com.appstr.timecontrol.ui.game.screens.SetupTimeScreen
import com.appstr.timecontrol.ui.game.viewmodels.GameViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.mockito.Mockito


@HiltAndroidTest
class TestSetupTimeScreen {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createComposeRule()

    private lateinit var navController: TestNavHostController

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

        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        composeRule.setContent {
            SetupTimeScreen(
                navController = navController,
                gameVM = gameViewModel
            )
        }
    }

//    @Test
//    fun test_navigateTo_SetupTimeScreen(){
//
//        assert(true)
//    }

//    @Test
//    fun test_defaultSelectedItemIs10MinsNoIncrement(){
//
//    }

}