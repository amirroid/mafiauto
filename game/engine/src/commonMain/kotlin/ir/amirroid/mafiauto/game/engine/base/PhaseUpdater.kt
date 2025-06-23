package ir.amirroid.mafiauto.game.engine.base

import ir.amirroid.mafiauto.game.engine.models.Phase

interface PhaseUpdater {
    fun updatePhase(newPhase: Phase)
}