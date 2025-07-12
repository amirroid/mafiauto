package ir.amirroid.mafiauto.domain.usecase.game

import ir.amirroid.mafiauto.domain.model.game.NightActionDescriptor
import ir.amirroid.mafiauto.domain.repository.GameRepository

class HandleNightActionsUseCase(
    private val gameRepository: GameRepository
) {
    operator fun invoke(actions: List<NightActionDescriptor>) =
        gameRepository.handleNightActions(actions)
}