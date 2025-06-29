package ir.amirroid.mafiauto.common.compose.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.ArrowIosBack
import compose.icons.evaicons.outline.ArrowIosForward

@Composable
private fun isRtl() = LocalLayoutDirection.current == LayoutDirection.Rtl

@Composable
fun autoMirrorIosForwardIcon() =
    if (isRtl()) EvaIcons.Outline.ArrowIosBack else EvaIcons.Outline.ArrowIosForward

@Composable
fun autoMirrorIosBackwardIcon() =
    if (!isRtl()) EvaIcons.Outline.ArrowIosBack else EvaIcons.Outline.ArrowIosForward