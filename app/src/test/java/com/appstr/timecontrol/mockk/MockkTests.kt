package com.appstr.timecontrol.mockk

import com.appstr.timecontrol.data.daos.GameStateDao
import com.appstr.timecontrol.data.repositories.GameStateRepository
import com.appstr.timecontrol.domain.models.GameState
import com.appstr.timecontrol.util.defaultTimeControls
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test


class MockkTests {

    private var repoDao = mockk<GameStateDao>(relaxed = true)
    private var repo = GameStateRepository(repoDao)


    @Test
    fun `does repo return null when no game present`() = runTest {
        coEvery { repo.getGameState() } returns null
        assertThat(repo.getGameState()).isEqualTo(null)
    }

    @Test
    fun `test get 1 minute game from db`() = runTest {
        val gameState = GameState(timeControl = defaultTimeControls[0])
        coEvery { repo.getGameState() } returns gameState
        assertThat(repo.getGameState()).isEqualTo(gameState)
    }

    @Test
    fun `test get 30 minute 20 increment game from db`() = runTest {
        val gameState = GameState(timeControl = defaultTimeControls[defaultTimeControls.lastIndex])
        coEvery { repo.getGameState() } returns gameState
        assertThat(repo.getGameState()).isEqualTo(GameState(timeControl = defaultTimeControls[defaultTimeControls.lastIndex]))
    }


}
