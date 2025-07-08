package ir.amirroid.mafiauto.libraries.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.mikepenz.aboutlibraries.ui.compose.rememberLibraries
import ir.amirroid.mafiauto.common.compose.components.BackButton
import ir.amirroid.mafiauto.common.compose.extra.defaultContentPadding
import ir.amirroid.mafiauto.common.compose.operators.plus
import ir.amirroid.mafiauto.design_system.components.appbar.CollapsingTopAppBarScaffold
import ir.amirroid.mafiauto.design_system.components.list.base.MListItem
import ir.amirroid.mafiauto.design_system.components.text.MText
import ir.amirroid.mafiauto.theme.core.AppTheme
import ir.amirroid.mafiauto.resources.Resources
import org.jetbrains.compose.resources.stringResource

@Composable
fun LibrariesScreen(
    onBack: () -> Unit
) {
    val libs by rememberLibraries {
        Resources.files.aboutLibraries().decodeToString()
    }
    val uriHandler = LocalUriHandler.current
    val libraries = libs?.libraries ?: return
    CollapsingTopAppBarScaffold(
        title = { MText(stringResource(Resources.strings.openSourceLibraries)) },
        navigationIcon = { BackButton(onBack) }
    ) { paddingValues ->
        CompositionLocalProvider(
            LocalLayoutDirection provides LayoutDirection.Ltr
        ) {
            LazyColumn(
                contentPadding = defaultContentPadding() + paddingValues,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(libraries, key = { it.uniqueId }) { library ->
                    MListItem(
                        text = {
                            Column(modifier = Modifier.padding(vertical = 4.dp)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    MText(
                                        text = library.name,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.weight(1f)
                                    )
                                    library.artifactVersion?.let { artifactVersion ->
                                        MText(
                                            text = artifactVersion,
                                            style = AppTheme.typography.caption
                                        )
                                    }
                                }
                                MText(
                                    text = remember {
                                        library.licenses.joinToString(", ") { it.name }
                                    }
                                )
                            }
                        },
                        onClick = { library.website?.let(uriHandler::openUri) },
                        enabled = library.website != null
                    )
                }
            }
        }
    }
}