package ir.amirroid.mafiauto.ui_models

import ir.amirroid.mafiauto.domain.model.Alignment
import ir.amirroid.mafiauto.resources.Resources

fun Alignment.getRelatedStringResource() = when (this) {
    Alignment.Mafia -> Resources.strings.mafia
    Alignment.Civilian -> Resources.strings.civilian
    Alignment.Neutral -> Resources.strings.neutral
}