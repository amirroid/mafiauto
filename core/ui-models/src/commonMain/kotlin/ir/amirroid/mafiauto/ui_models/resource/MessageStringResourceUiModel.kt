package ir.amirroid.mafiauto.ui_models.resource

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Immutable
data class MessageStringResourceUiModel(
    val resource: StringResource,
    val formatArgs: List<String> = emptyList()
) {
    @Composable
    fun rememberStringResource() = stringResource(resource, *formatArgs.toTypedArray())

    @Composable
    fun rememberAnnotatedString(
        highlightStyle: SpanStyle = SpanStyle(fontWeight = FontWeight.Bold),
    ): AnnotatedString {
        val rawText = stringResource(resource, *formatArgs.toTypedArray())
        return remember {
            val builder = AnnotatedString.Builder(rawText)
            formatArgs.forEach { arg ->
                if (arg.isEmpty()) return@forEach
                var searchStart = 0
                while (searchStart < rawText.length) {
                    val start = rawText.indexOf(arg, startIndex = searchStart)
                    if (start == -1) break

                    builder.addStyle(
                        style = highlightStyle,
                        start = start,
                        end = start + arg.length
                    )
                    searchStart = start + arg.length
                }
            }
            builder.toAnnotatedString()
        }
    }
}