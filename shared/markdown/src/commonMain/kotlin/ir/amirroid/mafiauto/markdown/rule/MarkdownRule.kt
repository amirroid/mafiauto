package ir.amirroid.mafiauto.markdown.rule

import androidx.compose.ui.text.AnnotatedString

class MarkdownRule(
    val pattern: Regex,
    val applyStyle: (MatchResult) -> AnnotatedString
)