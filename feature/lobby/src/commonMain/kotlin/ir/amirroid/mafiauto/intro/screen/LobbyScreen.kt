package ir.amirroid.mafiauto.intro.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.ArrowIosBack
import ir.amirroid.mafiauto.common.compose.extra.defaultContentPadding
import ir.amirroid.mafiauto.common.compose.operators.plus
import ir.amirroid.mafiauto.design_system.components.appbar.CollapsingTopAppBarScaffold
import ir.amirroid.mafiauto.design_system.components.button.MIconButton
import ir.amirroid.mafiauto.design_system.components.list.selectable.MToggleListItem
import ir.amirroid.mafiauto.design_system.components.text.MText
import ir.amirroid.mafiauto.resources.Resources
import org.jetbrains.compose.resources.stringResource
import kotlin.random.Random

@Composable
fun LobbyScreen(onBack: () -> Unit) {
    CollapsingTopAppBarScaffold(
        title = {
            Text(stringResource(Resources.strings.selectRoles))
        },
        navigationIcon = {
            MIconButton(onClick = onBack) {
                Icon(
                    imageVector = EvaIcons.Outline.ArrowIosBack,
                    contentDescription = null
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = defaultContentPadding() + paddingValues,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(30) {
                MToggleListItem(
                    text = {
                        MText("HELLO")
                    },
                    selected = remember { Random.nextBoolean() },
                    onClick = {}
                )
            }
        }
    }
}