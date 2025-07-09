package ir.amirroid.mafiauto.markdown.parser

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import ir.amirroid.mafiauto.markdown.rule.MarkdownRule

internal class MarkdownParser(private val rules: List<MarkdownRule>) {
    fun parse(input: String): AnnotatedString = buildAnnotatedString {
        var currentIndex = 0
        val matches = rules.flatMap { rule ->
            rule.pattern.findAll(input).map { it to rule }
        }.sortedBy { it.first.range.first }

        for ((match, rule) in matches) {
            if (match.range.first > currentIndex) {
                append(input.substring(currentIndex, match.range.first))
            }
            append(rule.applyStyle(match))
            currentIndex = match.range.last + 1
        }
        if (currentIndex < input.length) {
            append(input.substring(currentIndex))
        }
    }
}