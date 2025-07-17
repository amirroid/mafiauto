package ir.amirroid.mafiauto.engine.test

import ir.amirroid.mafiauto.engine.base.GameEngineTestBase
import ir.amirroid.mafiauto.engine.utils.generateDefenseVotes
import ir.amirroid.mafiauto.game.engine.models.Phase
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class GameEngineCoreTest : GameEngineTestBase() {
    @Test
    fun testPlayersSize() {
        assertEquals(playerRepository.getPlayers().size, engine.players.value.size)
    }

    @Test
    fun testUpdatePhase() {
        assertIs<Phase.Day>(engine.currentPhase.value)
        engine.proceedToNextPhase()
        assertIs<Phase.Voting>(engine.currentPhase.value)
        engine.proceedToDefendingPhase(emptyList())
        assertIs<Phase.Defending>(engine.currentPhase.value)
        engine.proceedToNextPhase()
        assertIs<Phase.Night>(engine.currentPhase.value)
        engine.handleNightActions(emptyList())
        assertIs<Phase.Result>(engine.currentPhase.value)
        engine.proceedToNextPhase()
        assertIs<Phase.Day>(engine.currentPhase.value)
    }

    @Test
    fun testVotingPhaseWithMajorityDefender() {
        engine.proceedToNextPhase()
        assertIs<Phase.Voting>(engine.currentPhase.value)
        val defenders = engine.getDefenseCandidates(generateDefenseVotes(players, players.last()))
        val expectedDefenders = listOf(players.last())
        assertEquals(expectedDefenders, defenders)
        engine.proceedToDefendingPhase(defenders)
        assertIs<Phase.Defending>(engine.currentPhase.value)
        val phase = engine.currentPhase.value as Phase.Defending
        assertEquals(expectedDefenders, phase.defenders)
    }

    @Test
    fun testDefendingPhaseWithMajorityDefender() {
        val defenders = listOf(players.first(), players.last())
        engine.proceedToDefendingPhase(defenders)
        assertIs<Phase.Defending>(engine.currentPhase.value)
        val phase = engine.currentPhase.value as Phase.Defending
        assertEquals(defenders, phase.defenders)
        engine.handleDefenseVoteResult(generateDefenseVotes(defenders, defenders.first()))
        assertIs<Phase.LastCard>(engine.currentPhase.value)
        val currentPhase = engine.currentPhase.value as Phase.LastCard
        assertEquals(defenders.first(), currentPhase.player)
    }

    @Test
    fun testDefendingPhaseWithNoDefenseVotes() {
        val defenders = listOf(players.first(), players.last())
        engine.proceedToDefendingPhase(defenders)
        assertIs<Phase.Defending>(engine.currentPhase.value)
        val phase = engine.currentPhase.value as Phase.Defending
        assertEquals(defenders, phase.defenders)
        engine.handleDefenseVoteResult(emptyMap())
        assertIs<Phase.Night>(engine.currentPhase.value)
    }
}