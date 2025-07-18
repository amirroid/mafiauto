package ir.amirroid.mafiauto.engine.test

import ir.amirroid.mafiauto.engine.base.GameEngineTestBase
import ir.amirroid.mafiauto.game.engine.effect.GunShotDayActionEffect
import ir.amirroid.mafiauto.game.engine.models.NightAction
import ir.amirroid.mafiauto.game.engine.models.Phase
import ir.amirroid.mafiauto.game.engine.models.findWithRoleKey
import ir.amirroid.mafiauto.game.engine.role.GodFather
import ir.amirroid.mafiauto.game.engine.utils.RoleKeys
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class NightActionsTest : GameEngineTestBase() {
    @Test
    fun shouldEnterNightPhaseAndHandleEmptyActions() {
        advanceToNightPhase()
        engine.handleNightActions(emptyList())
        assertIs<Phase.Result>(engine.currentPhase.value)
    }

    @Test
    fun doctorShouldSuccessfullySaveTargetFromMafiaAttack() {
        advanceToNightPhase()
        val actions = listOf(
            NightAction(
                player = players.findWithRoleKey(RoleKeys.GOD_FATHER)!!,
                targets = listOf(players.findWithRoleKey(RoleKeys.MAYOR)!!)
            ),
            NightAction(
                player = players.findWithRoleKey(RoleKeys.DOCTOR)!!,
                targets = listOf(players.findWithRoleKey(RoleKeys.MAYOR)!!)
            ),
        )
        engine.handleNightActions(actions)
        assertTrue(players.findWithRoleKey(RoleKeys.MAYOR)!!.isAlive)
    }

    @Test
    fun doctorShouldFailToSaveIfHealingWrongTarget() {
        advanceToNightPhase()
        val actions = listOf(
            NightAction(
                player = players.findWithRoleKey(RoleKeys.GOD_FATHER)!!,
                targets = listOf(players.findWithRoleKey(RoleKeys.MAYOR)!!)
            ),
            NightAction(
                player = players.findWithRoleKey(RoleKeys.DOCTOR)!!,
                targets = listOf(players.findWithRoleKey(RoleKeys.SILENCER)!!)
            ),
        )
        engine.handleNightActions(actions)
        assertFalse(players.findWithRoleKey(RoleKeys.MAYOR)!!.isAlive)
    }

    @Test
    fun sniperShouldFailToKillGodFatherOnFirstAttemptButSucceedNextTime() {
        advanceToNightPhase()
        fun getActions() = listOf(
            NightAction(
                player = players.findWithRoleKey(RoleKeys.SNIPER)!!,
                targets = listOf(players.findWithRoleKey(RoleKeys.GOD_FATHER)!!)
            ),
        )
        engine.handleNightActions(getActions())
        assertTrue(players.findWithRoleKey(RoleKeys.GOD_FATHER)!!.isAlive)
        advanceToNightPhase()
        engine.handleNightActions(getActions())
        assertFalse(players.findWithRoleKey(RoleKeys.GOD_FATHER)!!.isAlive)
    }


    @Test
    fun sniperDiesIfTargetsCivilian() {
        advanceToNightPhase()
        val actions = listOf(
            NightAction(
                player = players.findWithRoleKey(RoleKeys.SNIPER)!!,
                targets = listOf(players.findWithRoleKey(RoleKeys.MAYOR)!!)
            )
        )
        engine.handleNightActions(actions)
        assertTrue(players.findWithRoleKey(RoleKeys.MAYOR)!!.isAlive)
        assertFalse(players.findWithRoleKey(RoleKeys.SNIPER)!!.isAlive)
    }


    @Test
    fun silencerShouldSilenceTarget() {
        advanceToNightPhase()
        val actions = listOf(
            NightAction(
                player = players.findWithRoleKey(RoleKeys.SILENCER)!!,
                targets = listOf(players.findWithRoleKey(RoleKeys.MAYOR)!!)
            )
        )
        engine.handleNightActions(actions)
        assertTrue(players.findWithRoleKey(RoleKeys.MAYOR)!!.isSilenced)
    }

    @Test
    fun replacementGodFatherShouldKillTarget() {
        sniperShouldFailToKillGodFatherOnFirstAttemptButSucceedNextTime()
        advanceToNightPhase()
        val replacementGodFather = engine.getGodFatherReplacement()
        assertNotNull(replacementGodFather)
        val actions = listOf(
            NightAction(
                player = replacementGodFather,
                targets = listOf(players.findWithRoleKey(RoleKeys.MAYOR)!!),
                replacementRole = GodFather
            )
        )
        engine.handleNightActions(actions)
        assertFalse(players.findWithRoleKey(RoleKeys.MAYOR)!!.isAlive)
    }

    @Test
    fun replacementGodFatherShouldAppearInNightPhaseOptions() {
        sniperShouldFailToKillGodFatherOnFirstAttemptButSucceedNextTime()
        val phase = advanceToNightPhase()
        assertTrue { phase.options.any { it.isReplacement && it.player.role == GodFather } }
    }

    @Test
    fun rangerShouldKillTargetButDieIfTargetsMayor() {
        advanceToNightPhase()
        val actions = listOf(
            NightAction(
                player = players.findWithRoleKey(RoleKeys.RANGER)!!,
                targets = listOf(players.findWithRoleKey(RoleKeys.SURGEON)!!)
            )
        )
        engine.handleNightActions(actions)
        assertTrue(players.findWithRoleKey(RoleKeys.RANGER)!!.isAlive)
        assertFalse(players.findWithRoleKey(RoleKeys.SURGEON)!!.isAlive)
        val actions2 = listOf(
            NightAction(
                player = players.findWithRoleKey(RoleKeys.RANGER)!!,
                targets = listOf(players.findWithRoleKey(RoleKeys.MAYOR)!!)
            )
        )
        engine.handleNightActions(actions2)
        assertFalse(players.findWithRoleKey(RoleKeys.RANGER)!!.isAlive)
        assertTrue(players.findWithRoleKey(RoleKeys.MAYOR)!!.isAlive)
    }

    @Test
    fun mayorShouldReceiveMessageWithSurgeonAndDoctorNames() {
        val phase = advanceToNightPhase()

        val mayorOption = phase.options.firstOrNull {
            it.player.role.key == RoleKeys.MAYOR
        }
        assertNotNull(mayorOption)

        val mayorMessage = mayorOption.message
        assertNotNull(mayorMessage)

        val expectedPlayers = listOf(
            players.findWithRoleKey(RoleKeys.SURGEON)!!.name,
            players.findWithRoleKey(RoleKeys.DOCTOR)!!.name,
        )

        val allNamesPresent = mayorMessage.formatArgs.all { it in expectedPlayers }
        assertTrue(allNamesPresent)
    }

    @Test
    fun bomberExplosionShouldKillNeighbors() {
        advanceToNightPhase()
        val bomber = players.findWithRoleKey(RoleKeys.BOMBER)!!
        val bomberIndex = players.indexOf(bomber)
        val actions = listOf(
            NightAction(
                player = players.findWithRoleKey(RoleKeys.GOD_FATHER)!!,
                targets = listOf(bomber)
            )
        )
        engine.handleNightActions(actions)
        assertFalse(players[bomberIndex - 1].isAlive)
        assertFalse(players[bomberIndex + 1].isAlive)
    }

    @Test
    fun bulletproofPlayerShouldSurviveGodFatherAttack() {
        advanceToNightPhase()
        val actions = listOf(
            NightAction(
                player = players.findWithRoleKey(RoleKeys.GOD_FATHER)!!,
                targets = listOf(players.findWithRoleKey(RoleKeys.BULLETPROOF)!!)
            )
        )
        engine.handleNightActions(actions)
        assertTrue(players.findWithRoleKey(RoleKeys.BULLETPROOF)!!.isAlive)
    }

    @Test
    fun realAndPracticeShotsShouldKillCorrectTargets() {
        advanceToNightPhase()
        val gunSmith = players.findWithRoleKey(RoleKeys.GUNSMITH)!!
        val bulletProof = players.findWithRoleKey(RoleKeys.BULLETPROOF)!!
        val mayor = players.findWithRoleKey(RoleKeys.MAYOR)!!
        val doctor = players.findWithRoleKey(RoleKeys.DOCTOR)!!
        val sniper = players.findWithRoleKey(RoleKeys.SNIPER)!!

        engine.handleNightActions(listOf(NightAction(gunSmith, listOf(bulletProof, mayor))))

        val updatedBulletProof = players.findWithRoleKey(RoleKeys.BULLETPROOF)!!
        val updatedMayor = players.findWithRoleKey(RoleKeys.MAYOR)!!

        val bulletProofEffect =
            updatedBulletProof.effects.filterIsInstance<GunShotDayActionEffect>().first()
        val mayorEffect = updatedMayor.effects.filterIsInstance<GunShotDayActionEffect>().first()

        bulletProofEffect.getAction()
            .applyAction(updatedBulletProof, listOf(doctor), players, engine)
        mayorEffect.getAction().applyAction(updatedMayor, listOf(sniper), players, engine)

        assertFalse(players.findWithRoleKey(RoleKeys.DOCTOR)!!.isAlive)
        assertTrue(players.findWithRoleKey(RoleKeys.SNIPER)!!.isAlive)
    }

    @Test
    fun sniperShotFailsButSurgeonSavesMafia() {
        advanceToNightPhase()
        val actions = listOf(
            NightAction(
                player = players.findWithRoleKey(RoleKeys.SNIPER)!!,
                targets = listOf(players.findWithRoleKey(RoleKeys.SAUL_GOODMAN)!!)
            ),
            NightAction(
                player = players.findWithRoleKey(RoleKeys.SURGEON)!!,
                targets = listOf(players.findWithRoleKey(RoleKeys.SAUL_GOODMAN)!!)
            ),
        )
        engine.handleNightActions(actions)
        assertTrue(players.findWithRoleKey(RoleKeys.SAUL_GOODMAN)!!.isAlive)
    }


    private fun advanceToNightPhase(): Phase.Night {
        engine.proceedToNextPhase() // Day → Voting
        engine.proceedToDefendingPhase(emptyList()) // Voting → Defending
        engine.proceedToNextPhase() // Defending → Night
        assertIs<Phase.Night>(engine.currentPhase.value)
        return engine.currentPhase.value as Phase.Night
    }
}