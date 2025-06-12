package ir.amirroid.mafiauto.design_system.components.snakebar

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.jetbrains.compose.resources.StringResource
import kotlin.random.Random

@Stable
data class SnackBarData(
    val text: StringResource,
) {
    internal val key = text.key + Random.nextInt()
    internal var visible by mutableStateOf(true)
}