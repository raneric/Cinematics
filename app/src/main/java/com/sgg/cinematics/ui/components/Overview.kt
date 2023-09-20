package com.sgg.cinematics.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sgg.cinematics.R
import com.sgg.cinematics.ui.ui.theme.CinematicsTheme

val OVERVIEW_HEIGHT = 100.dp

/**
 * This composable display overview description of the movie it have two state :
 *  - [OverviewState.Collapsed] : the default state where the Box hava a fixed height of [OVERVIEW_HEIGHT] and hide partially text
 *  - [OverviewState.Expanded] : the state of the overview where the Box height is wrapContentHeight() and display all text
 * @param text : String : the text to display
 * @param modifier: A modifier with default value [Modifier]
 */
@Composable
fun Overview(text: String,
             modifier: Modifier = Modifier) {

    var overviewSate: OverviewState by remember {
        mutableStateOf(OverviewState.Collapsed)
    }

    var textHeight by remember { mutableStateOf(0) }

    Box(modifier = modifier
            .testTag(stringResource(id = R.string.test_tag_overview))
            .fillMaxWidth()
            .then(overviewSate.boxModifier)) {
        Text(
            style = MaterialTheme.typography.bodyMedium,
            text = text,
            modifier = Modifier
                    .then(overviewSate.textModifier)
                    .onGloballyPositioned { textHeight = it.size.height }
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessMedium
                        )
                    ))
        OverviewHider(overviewSate = overviewSate,
                      modifier = Modifier.align(Alignment.BottomCenter)) {
            overviewSate = overviewSate.reverseState()
        }
    }
}

/**
 *  The foreground gradient with a button that allow to partially hide or view all overview text
 * @param overviewSate : the state of the current overview [OverviewState.Collapsed] or [OverviewState.Expanded]
 * @param modifier: A modifier with default value [Modifier]
 * @param expand : a lambda expression that handle the onclick event from the button to update overviewSate param
 */
@Composable
fun OverviewHider(overviewSate: OverviewState,
                  modifier: Modifier = Modifier,
                  expand: () -> Unit) {
    AnimatedVisibility(
        visible = overviewSate.isCollapsed(),
        enter = fadeIn(),
        exit = fadeOut()) {
        GradientForeground(color = MaterialTheme.colorScheme.surface,
                           modifier = Modifier.height(OVERVIEW_HEIGHT))
    }

    IconButton(onClick = expand,
               modifier = modifier) {
        Icon(painter = painterResource(id = overviewSate.buttonIcon),
             contentDescription = stringResource(id = R.string.content_descrip_read_more),
             tint = MaterialTheme.colorScheme.onSurface)
    }
}

/**
 * Sealed class for the state of the overview with two singleton sub-class :
 *  - [Collapsed] : hold all overview state when collapsed
 *  - [Expanded] : hold all overview state when expanded
 *  @param buttonIcon : Drawable id of the button icon to display
 *  @param boxModifier : modifier for the Box composable
 *  @param textModifier : modifier for the Text composable
 */
sealed class OverviewState(@DrawableRes val buttonIcon: Int,
                           val boxModifier: Modifier,
                           val textModifier: Modifier = Modifier) {

    abstract fun reverseState(): OverviewState

    fun isCollapsed() = this is Collapsed

    object Collapsed :
            OverviewState(buttonIcon = R.drawable.chevron_double_down,
                          boxModifier = Modifier.heightIn(max = OVERVIEW_HEIGHT)) {
        override fun reverseState(): OverviewState {
            return Expanded
        }
    }

    object Expanded :
            OverviewState(buttonIcon = R.drawable.chevron_double_up,
                          boxModifier = Modifier.heightIn(min = OVERVIEW_HEIGHT),
                          textModifier = Modifier.padding(bottom = 40.dp)) {
        override fun reverseState(): OverviewState {
            return Collapsed
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OverviewPreview() {
    CinematicsTheme {
        Overview(text = stringResource(id = R.string.txt_overview_preview_example))
    }
}

