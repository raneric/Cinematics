package com.sgg.cinematics.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sgg.cinematics.R
import com.sgg.cinematics.ui.ui.theme.CinematicsTheme
import com.sgg.cinematics.utils.Destination

/**
 * Custom bottom navigation composable that use [NavigationBar] from MUI3. It iterate through the
 * [bottomNavItemList] to create an [NavigationBarItem] inside the [NavigationBar] composable
 * @param bottomNavList : A list of [BottomNavItemVariant] object which are the navigation destination
 * @param activeDestination: A [BottomNavItemVariant] item that is the current active destination
 *                           with [BottomNavItemVariant.Trending] as default value
 * @param modifier: A modifier with default value [Modifier]
 */
@Composable
fun BottomNavScreen(activeDestination: BottomNavItemVariant,
                    modifier: Modifier = Modifier.testTag(stringResource(id = R.string.test_tag_bottom_nav)),
                    onItemClicked: (String) -> Unit) {

    NavigationBar(tonalElevation = 5.dp) {
        bottomNavItemList.take(5)
                .forEach { item ->
                    NavigationBarItem(selected = item == activeDestination,
                                      icon = {
                                          Icon(painter = painterResource(id = item.iconId),
                                               contentDescription = stringResource(id = item.iconContentDescription))
                                      },
                                      label = { Text(text = stringResource(id = item.textId)) },
                                      onClick = { onItemClicked(item.route) })
                }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavScreenPreview() {
    CinematicsTheme {
        BottomNavScreen(BottomNavItemVariant.Trending) {

        }
    }

}

/**
 * A sealed base class for each BottomNavItem
 * @constructor :
 * @param textId : Int StringRes ID for theBottom nav item display text
 * @param iconContentDescription : Int StringRes ID for the icon content description
 * @param iconId : Int DrawableRes ID for the icon
 * @param route: String rout for the navigation destination
 */
sealed class BottomNavItemVariant(@StringRes val textId: Int,
                                  @StringRes val iconContentDescription: Int,
                                  @DrawableRes val iconId: Int,
                                  val route: String) {
    object Trending : BottomNavItemVariant(textId = R.string.txt_trending,
                                           iconContentDescription = R.string.content_descrip_trending,
                                           iconId = R.drawable.icon_trending_24,
                                           route = Destination.TrendingScreen.route)

    object TopRated : BottomNavItemVariant(textId = R.string.txt_top_rated,
                                           iconContentDescription = R.string.content_descrip_top_rated,
                                           iconId = R.drawable.icon_top_rated_24,
                                           route = Destination.TopRatedScreen.route)

    object WatchList : BottomNavItemVariant(textId = R.string.txt_watch_list,
                                            iconContentDescription = R.string.content_descrip_watch_list,
                                            iconId = R.drawable.icon_watch_list_24,
                                            route = Destination.WatchListScreen.route)
}

private val bottomNavItemList = listOf(BottomNavItemVariant.Trending,
                                       BottomNavItemVariant.TopRated,
                                       BottomNavItemVariant.WatchList)