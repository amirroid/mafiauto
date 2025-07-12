package ir.amirroid.mafiauto.domain.model.effect

sealed interface PlayerEffectDomain {
    data class FlagEffect(val flag: String) : PlayerEffectDomain
    data class DayActionEffect(
        val name: String,
        val nightActionRequiredPicks: Int,
    ) : PlayerEffectDomain
}