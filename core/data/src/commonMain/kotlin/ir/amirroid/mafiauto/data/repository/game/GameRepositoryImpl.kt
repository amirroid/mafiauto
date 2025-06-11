package ir.amirroid.mafiauto.data.repository.game

import ir.amirroid.mafiauto.domain.repository.GameRepository
import ir.amirroid.mafiauto.game.engine.GameEngine
import kotlinx.coroutines.flow.Flow

class GameRepositoryImpl(
    private val engine: GameEngine
) : GameRepository {
    override val statusChecksCount = engine.statusCheckCount

    override fun onStatusChecked() = engine.incrementStatusCheckCount()
    override fun undoStatusCheck() = engine.decreaseStatusCheckCount()
}