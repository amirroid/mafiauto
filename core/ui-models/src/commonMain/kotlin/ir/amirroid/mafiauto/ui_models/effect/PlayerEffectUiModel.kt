package ir.amirroid.mafiauto.ui_models.effect

sealed class PlayerEffectUiModel(val buttonInfo: EffectButtonInfo?) {
    data class FlagEffect(
        val flag: String,
        val info: EffectButtonInfo?
    ) : PlayerEffectUiModel(info)

    data class DayActionEffect(
        val name: String,
        val nightActionRequiredPicks: Int,
        val info: EffectButtonInfo?
    ) : PlayerEffectUiModel(info)
}