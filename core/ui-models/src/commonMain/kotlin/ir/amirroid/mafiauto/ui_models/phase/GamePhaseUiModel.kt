package ir.amirroid.mafiauto.ui_models.phase

import ir.amirroid.mafiauto.resources.Resources
import org.jetbrains.compose.resources.StringResource

enum class GamePhaseUiModel(
    val displayName: StringResource,
) {
    Night(Resources.strings.night),
    Day(Resources.strings.day),
    Voting(Resources.strings.voting),
    Result(Resources.strings.voting)
}