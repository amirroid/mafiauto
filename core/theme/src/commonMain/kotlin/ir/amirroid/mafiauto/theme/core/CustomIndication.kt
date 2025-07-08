package ir.amirroid.mafiauto.theme.core

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.node.DelegatableNode
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.node.invalidateDraw
import kotlinx.coroutines.launch

object PressScaleIndication : IndicationNodeFactory {
    override fun create(interactionSource: InteractionSource): DelegatableNode {
        return PressScaleIndicationNode(interactionSource)
    }

    override fun equals(other: Any?) = other === this
    override fun hashCode() = this::class.hashCode()
}

private class PressScaleIndicationNode(
    private val interactionSource: InteractionSource
) : Modifier.Node(), DrawModifierNode {

    private var currentScale = 1f

    override fun onAttach() {
        super.onAttach()
        listenToPressInteractions()
    }

    private fun listenToPressInteractions() = coroutineScope.launch {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is PressInteraction.Press -> animateScaleTo(0.92f)
                is PressInteraction.Release, is PressInteraction.Cancel -> animateScaleTo(1f)
            }
        }
    }

    private fun animateScaleTo(targetScale: Float) = coroutineScope.launch {
        animate(
            initialValue = currentScale,
            targetValue = targetScale,
            animationSpec = tween(durationMillis = 150, easing = LinearOutSlowInEasing)
        ) { animatedValue, _ ->
            currentScale = animatedValue
            invalidateDraw()
        }
    }

    override fun ContentDrawScope.draw() {
        scale(currentScale) {
            this@draw.drawContent()
        }
    }
}