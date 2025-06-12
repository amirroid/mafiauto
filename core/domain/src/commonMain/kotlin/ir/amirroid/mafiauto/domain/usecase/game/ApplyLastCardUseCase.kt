package ir.amirroid.mafiauto.domain.usecase.game

import ir.amirroid.mafiauto.domain.model.LastCardDescriptor
import ir.amirroid.mafiauto.domain.model.PlayerWithRole
import ir.amirroid.mafiauto.domain.repository.GameRepository

class ApplyLastCardUseCase(
    private val gameRepository: GameRepository
) {
    operator fun invoke(card: LastCardDescriptor, pickedPlayers: List<PlayerWithRole>) =
        gameRepository.applyLastCard(card, pickedPlayers)
}