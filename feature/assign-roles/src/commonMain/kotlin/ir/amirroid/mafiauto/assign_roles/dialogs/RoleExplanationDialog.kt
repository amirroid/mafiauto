package ir.amirroid.mafiauto.assign_roles.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import ir.amirroid.mafiauto.design_system.components.button.MButton
import ir.amirroid.mafiauto.design_system.components.dialog.MDialog
import ir.amirroid.mafiauto.design_system.components.text.MText
import ir.amirroid.mafiauto.design_system.core.AppTheme
import ir.amirroid.mafiauto.resources.Resources
import ir.amirroid.mafiauto.ui_models.role.RoleUiModel
import org.jetbrains.compose.resources.stringResource

@Composable
fun RoleExplanationDialog(
    role: RoleUiModel,
    onDismissRequest: () -> Unit
) {
    var visible by remember { mutableStateOf(true) }

    MDialog(
        isVisible = visible,
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            MText(
                text = buildAnnotatedString {
                    withStyle(AppTheme.typography.h3.toSpanStyle()) {
                        append(stringResource(role.name))
                    }
                    append(" - ")
                    append(stringResource(role.formattedAlignment))
                    if (role.maxAbilityUses != Int.MAX_VALUE) {
                        append(" - ${role.maxAbilityUses}X")
                    }
                },
            )
            MText(
                text = stringResource(role.explanation),
                modifier = Modifier.padding(top = 16.dp)
            )
            MButton(
                onClick = {
                    visible = false
                }, modifier = Modifier.padding(top = 16.dp).fillMaxWidth()
            ) {
                MText(stringResource(Resources.strings.ok))
            }
        }
    }
}