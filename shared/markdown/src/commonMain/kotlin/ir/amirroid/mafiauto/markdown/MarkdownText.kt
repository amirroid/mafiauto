package ir.amirroid.mafiauto.markdown

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import ir.amirroid.mafiauto.design_system.components.text.MText
import ir.amirroid.mafiauto.markdown.parser.MarkdownParser
import ir.amirroid.mafiauto.markdown.rule.DefaultMarkdownRules
import ir.amirroid.mafiauto.markdown.rule.MarkdownRule

@Composable
fun MarkdownText(
    markdown: String,
    modifier: Modifier = Modifier,
    extraRules: List<MarkdownRule> = emptyList()
) {
    val parser = remember { MarkdownParser(DefaultMarkdownRules.rules + extraRules) }
    val annotated = remember(markdown) { parser.parse(markdown) }
    MText(text = annotated, modifier = modifier)
}