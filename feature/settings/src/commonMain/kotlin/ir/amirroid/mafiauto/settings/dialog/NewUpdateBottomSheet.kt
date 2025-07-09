package ir.amirroid.mafiauto.settings.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.ExternalLink
import ir.amirroid.mafiauto.design_system.components.button.MButton
import ir.amirroid.mafiauto.design_system.components.icon.MIcon
import ir.amirroid.mafiauto.design_system.components.sheet.MBottomSheet
import ir.amirroid.mafiauto.design_system.components.text.MText
import ir.amirroid.mafiauto.markdown.MarkdownText
import ir.amirroid.mafiauto.resources.Resources
import ir.amirroid.mafiauto.theme.core.AppTheme
import ir.amirroid.mafiauto.ui_models.update.UpdateInfoUiModel
import org.jetbrains.compose.resources.stringResource

@Composable
fun NewUpdateBottomSheet(
    updateInfo: UpdateInfoUiModel,
    onDismissRequest: () -> Unit
) {
    var visible by remember { mutableStateOf(true) }

    MBottomSheet(visible, onDismissRequest = onDismissRequest) {
        if (updateInfo.needToUpdate) {
            UpdateAvailableContent(updateInfo) {
                visible = false
            }
        } else {
            AppIsUpToDateContent {
                visible = false
            }
        }
    }
}

@Composable
private fun UpdateAvailableContent(
    updateInfo: UpdateInfoUiModel,
    onClose: () -> Unit
) {
    val scrollState = rememberScrollState()
    val uriHandler = LocalUriHandler.current

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        MText(updateInfo.release.name, style = AppTheme.typography.h2)

        MarkdownText(
            markdown = updateInfo.release.body,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 400.dp)
                .verticalScroll(scrollState)
        )

        MButton(
            onClick = {
                onClose()
                uriHandler.openUri(updateInfo.release.url)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            MText(text = stringResource(Resources.strings.view))
            MIcon(
                imageVector = EvaIcons.Outline.ExternalLink,
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(18.dp)
            )
        }
    }
}

@Composable
private fun AppIsUpToDateContent(
    onClose: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        MText(
            text = stringResource(Resources.strings.appUpToDate),
            style = AppTheme.typography.h4
        )

        MButton(
            onClick = { onClose() },
            modifier = Modifier.fillMaxWidth()
        ) {
            MText(text = stringResource(Resources.strings.ok))
        }
    }
}