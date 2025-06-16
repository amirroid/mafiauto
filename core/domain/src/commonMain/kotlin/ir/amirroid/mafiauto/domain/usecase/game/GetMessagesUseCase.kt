package ir.amirroid.mafiauto.domain.usecase.game

import ir.amirroid.mafiauto.domain.repository.GameRepository

class GetMessagesUseCase(private val gameRepository: GameRepository) {
    operator fun invoke() = gameRepository.messages
}