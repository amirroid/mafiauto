package ir.amirroid.mafiauto.engine.test

import ir.amirroid.mafiauto.engine.base.GameEngineTestBase
import ir.amirroid.mafiauto.engine.utils.generateDefenseVotes
import ir.amirroid.mafiauto.game.engine.last_card.LastCard
import ir.amirroid.mafiauto.game.engine.models.Phase
import ir.amirroid.mafiauto.game.engine.models.Player
import ir.amirroid.mafiauto.game.engine.role.Alignment
import ir.amirroid.mafiauto.game.engine.utils.LastCardKeys
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.collections.first
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue

class LastCardTest : GameEngineTestBase() {
    private val majorityDefender: Player
        get() = players.first()

    private fun prepareLastCardPhase(card: LastCard, pickedPlayers: List<Player>) {
        val defenders = listOf(majorityDefender, players.last())

        engine.proceedToDefendingPhase(defenders)
        engine.handleDefenseVoteResult(generateDefenseVotes(defenders, majorityDefender))
        assertIs<Phase.LastCard>(engine.currentPhase.value)
        val currentPhase = engine.currentPhase.value as Phase.LastCard
        assertEquals(majorityDefender, currentPhase.player)
        engine.applyLastCard(card, pickedPlayers = pickedPlayers)
    }

    @Test
    fun testSilenceCardEffect() {
        val card = lastCardsProvider.getAllLastCards().first { it.key == LastCardKeys.SILENCE }

        prepareLastCardPhase(card, listOf(players[5], players[10]))
        listOf(players[5], players[10]).forEach {
            assertTrue(it.isSilenced)
        }
    }

    @Test
    fun testBraceletCardEffect() {
        val card = lastCardsProvider.getAllLastCards().first { it.key == LastCardKeys.BRACELET }

        prepareLastCardPhase(card, listOf(players[5]))
        assertFalse(players[5].canUseAbility)
    }

    @Test
    fun testAuthenticationCardEffect() = runTest {
        val card =
            lastCardsProvider.getAllLastCards().first { it.key == LastCardKeys.AUTHENTICATION }

        prepareLastCardPhase(card, emptyList())
        engine.messages.first() == majorityDefender.role.name
    }

    @Test
    fun testFaceUpCardEffect() = runTest {
        val card = lastCardsProvider.getAllLastCards()
            .first { it.key == LastCardKeys.FACE_UP }

        val defenderBefore = majorityDefender
        val pickedPlayerBefore = players[5]

        val expectedDefenderRole = defenderBefore.role
        val expectedPickedPlayerRole = pickedPlayerBefore.role

        prepareLastCardPhase(card, listOf(pickedPlayerBefore))

        val defenderAfter = majorityDefender
        val pickedPlayerAfter = players[5]

        assertEquals(expectedPickedPlayerRole, defenderAfter.role)
        assertEquals(expectedDefenderRole, pickedPlayerAfter.role)
    }

    @Test
    fun testBeautifulMindKillsNeutralPickedPlayer() = runTest {
        val beautifulMindCard = lastCardsProvider.getAllLastCards()
            .first { it.key == LastCardKeys.BEAUTIFUL_MIND }

        val neutralPlayer = players.first { it.role.alignment == Alignment.Neutral }
        val neutralPlayerId = neutralPlayer.id

        prepareLastCardPhase(beautifulMindCard, listOf(neutralPlayer))

        val updatedNeutralPlayer = players.first { it.id == neutralPlayerId }

        assertFalse(updatedNeutralPlayer.isAlive)
        assertFalse(updatedNeutralPlayer.canBackWithSave)
        assertTrue(majorityDefender.isAlive)
    }

    @Test
    fun testBeautifulMindKillsMajorityDefenderIfPickedPlayerIsNotNeutral() = runTest {
        val beautifulMindCard = lastCardsProvider.getAllLastCards()
            .first { it.key == LastCardKeys.BEAUTIFUL_MIND }

        val nonNeutralPlayer = (players - majorityDefender)
            .first { it.role.alignment != Alignment.Neutral }
        val nonNeutralPlayerId = nonNeutralPlayer.id
        val defenderId = majorityDefender.id

        prepareLastCardPhase(beautifulMindCard, listOf(nonNeutralPlayer))

        val updatedNonNeutralPlayer = players.first { it.id == nonNeutralPlayerId }
        val updatedDefender = players.first { it.id == defenderId }

        assertTrue(updatedNonNeutralPlayer.isAlive)
        assertFalse(updatedDefender.isAlive)
    }
}