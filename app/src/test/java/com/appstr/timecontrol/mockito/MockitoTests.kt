package com.appstr.timecontrol.mockito

import com.appstr.timecontrol.data.daos.GameStateDao
import com.appstr.timecontrol.data.repositories.GameStateRepository
import com.appstr.timecontrol.domain.models.GameState
import com.appstr.timecontrol.util.defaultTimeControls
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule


class MockitoTests {

    @JvmField
    @Rule
    var mockitoRule: MockitoRule = MockitoJUnit.rule()

    private lateinit var repo: GameStateRepository
    private lateinit var repoDao: GameStateDao

    @Before
    fun setupRepo(){
        repoDao = Mockito.mock(GameStateDao::class.java)
        repo = GameStateRepository(repoDao)
    }

    @Test
    fun testIfMockRepoHasNoGameStateReturnNull(){
        runTest {
            val nullGameState: GameState? = null
            Mockito.`when`(repoDao.getGameState()).thenReturn(nullGameState)
            assertThat(repo.getGameState()).isEqualTo(null)
        }
    }

    @Test
    fun testGet1MinuteGameFromDB(){
        runTest {
            val oneMinuteGameState = GameState(timeControl = defaultTimeControls[0])
            Mockito.`when`(repoDao.getGameState()).thenReturn(oneMinuteGameState)
//            repo.updateGameState(GameState(timeControl = defaultTimeControls[0]))
            assertThat(repo.getGameState()).isEqualTo(GameState(timeControl = defaultTimeControls[0]))
        }
    }

    @Test
    fun testGet30MinuteGame20IncFromDB(){
        runTest {
            val thirtyMinuteGameState = GameState(timeControl = defaultTimeControls[defaultTimeControls.lastIndex])
            Mockito.`when`(repoDao.getGameState()).thenReturn(thirtyMinuteGameState)
            assertThat(repo.getGameState()).isEqualTo(GameState(timeControl = defaultTimeControls[defaultTimeControls.lastIndex]))
        }
    }

}