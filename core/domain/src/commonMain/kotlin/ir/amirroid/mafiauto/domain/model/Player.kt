package ir.amirroid.mafiauto.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class Player(
    val id: Long,
    val name: String
)