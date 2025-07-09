package ir.amirroid.mafiauto.markdown.rule

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

internal object DefaultMarkdownRules {
    val rules: List<MarkdownRule>
        get() = listOf(bold, bulletItem)

    val bold = MarkdownRule(
        pattern = """\*\*(.+?)\*\*""".toRegex(),
        applyStyle = { match ->
            AnnotatedString(
                text = match.groupValues[1],
                spanStyle = SpanStyle(fontWeight = FontWeight.Bold)
            )
        }
    )

    val bulletItem = MarkdownRule(
        pattern = "^[-*] (.+)".toRegex(RegexOption.MULTILINE),
        applyStyle = { match ->
            AnnotatedString(
                text = "• ${match.groupValues[1]}",
                spanStyle = SpanStyle(fontSize = 14.sp)
            )
        }
    )
}