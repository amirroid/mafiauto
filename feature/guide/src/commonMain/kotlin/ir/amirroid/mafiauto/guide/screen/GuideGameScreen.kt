package ir.amirroid.mafiauto.guide.screen

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import dev.chrisbanes.haze.rememberHazeState
import ir.amirroid.mafiauto.common.compose.components.BackButton
import ir.amirroid.mafiauto.common.compose.modifiers.thenIf
import ir.amirroid.mafiauto.common.compose.operators.plus
import ir.amirroid.mafiauto.design_system.components.appbar.CollapsingTopAppBarScaffold
import ir.amirroid.mafiauto.design_system.components.appbar.rememberCollapsingAppBarState
import ir.amirroid.mafiauto.design_system.components.text.MText
import ir.amirroid.mafiauto.guide.viewmodel.GuideGameViewModel
import ir.amirroid.mafiauto.resources.Resources
import ir.amirroid.mafiauto.theme.core.AppTheme
import ir.amirroid.mafiauto.ui_models.role.RoleUiModel
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import kotlin.math.roundToInt

val itemHeight = 86.dp

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun GuideGameScreen(
    onBack: () -> Unit,
    viewModel: GuideGameViewModel = koinViewModel()
) {
    val hazeState = rememberHazeState()
    val hazeStyle = HazeMaterials.thin(AppTheme.colorScheme.surface)
    val roles = viewModel.roles
    val horizontalScrollState = rememberScrollState()
    val collapsingTopAppBarState = rememberCollapsingAppBarState()

    Box(Modifier.fillMaxSize()) {
        CollapsingTopAppBarScaffold(
            title = { MText(stringResource(Resources.strings.gameGuide)) },
            navigationIcon = { BackButton(onBack = onBack) },
            hazeState = hazeState,
            hazeStyle = hazeStyle,
            state = collapsingTopAppBarState
        ) { paddingValues ->
            RolesGuidList(
                roles = roles,
                horizontalScrollState = horizontalScrollState,
                contentPadding = paddingValues + PaddingValues(top = itemHeight) + WindowInsets.systemBars.asPaddingValues()
            )
        }
        Box(
            Modifier
                .statusBarsPadding()
                .offset {
                    IntOffset(0, collapsingTopAppBarState.heightPx.roundToInt())
                }
                .height(itemHeight)
                .hazeEffect(hazeState, hazeStyle)
                .wrapContentWidth(unbounded = true, align = Alignment.Start)
        ) {
            RoleItem(
                name = stringResource(Resources.strings.name),
                explanation = stringResource(Resources.strings.explanation),
                alignment = stringResource(Resources.strings.alignment),
                maxAbilityUses = stringResource(Resources.strings.maxAbilityUses),
                isLastItem = true,
                modifier = Modifier.offset {
                    IntOffset(x = -horizontalScrollState.value, y = 0)
                },
                textAlignment = Alignment.CenterStart,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun RolesGuidList(
    roles: ImmutableList<RoleUiModel>,
    horizontalScrollState: ScrollState,
    contentPadding: PaddingValues = PaddingValues()
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().horizontalScroll(horizontalScrollState),
        contentPadding = contentPadding,
    ) {
        itemsIndexed(roles, key = { _, role -> role.key }) { index, role ->
            RoleItem(
                name = stringResource(role.name),
                explanation = stringResource(role.explanation),
                alignment = stringResource(role.formattedAlignment),
                maxAbilityUses = if (role.maxAbilityUses == Int.MAX_VALUE) {
                    "∞"
                } else role.maxAbilityUses.toString(),
                isLastItem = index == roles.size - 1
            )
        }
    }
}

@Composable
private fun TextBox(
    text: String,
    width: Dp,
    align: Alignment,
    isLastItem: Boolean = false,
    fontWeight: FontWeight? = null
) {
    val primaryColor = AppTheme.colorScheme.primary
    Box(
        modifier = Modifier
            .width(width)
            .thenIf(!isLastItem) {
                drawBehind {
                    drawLine(
                        primaryColor, start = Offset(size.width, 0f),
                        end = Offset(size.width, size.height),
                        strokeWidth = 1.dp.toPx()
                    )
                }
            }.fillMaxHeight(),
        contentAlignment = align
    ) {
        MText(
            text = text,
            modifier = Modifier.padding(12.dp),
            fontWeight = fontWeight
        )
    }
}

@Composable
fun RoleItem(
    name: String,
    alignment: String,
    maxAbilityUses: String,
    explanation: String,
    isLastItem: Boolean,
    modifier: Modifier = Modifier,
    textAlignment: Alignment = Alignment.CenterStart,
    fontWeight: FontWeight? = null
) {
    val primaryColor = AppTheme.colorScheme.primary
    Row(
        modifier = modifier
            .height(itemHeight)
            .thenIf(!isLastItem) {
                drawBehind {
                    drawLine(
                        primaryColor, start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = 1.dp.toPx()
                    )
                }
            }
    ) {
        TextBox(
            text = name,
            width = 110.dp,
            align = textAlignment,
            fontWeight = FontWeight.Bold
        )
        TextBox(
            text = alignment,
            width = 110.dp,
            align = Alignment.Center,
            fontWeight = fontWeight
        )
        TextBox(
            text = maxAbilityUses,
            width = 110.dp,
            align = Alignment.Center,
            fontWeight = fontWeight
        )
        TextBox(
            text = explanation,
            width = 350.dp,
            isLastItem = true,
            align = textAlignment,
            fontWeight = fontWeight
        )
    }
}