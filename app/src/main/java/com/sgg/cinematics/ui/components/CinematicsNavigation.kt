package com.sgg.cinematics.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.sgg.cinematics.R
import com.sgg.cinematics.ui.ui.theme.CinematicsTheme
import com.sgg.cinematics.utils.Destination
import com.sgg.cinematics.utils.activeNavItem


private val navItemList = listOf(
        NavItemVariant.Trending,
        NavItemVariant.WatchList,
        NavItemVariant.UserProfile
)

/**
 * Custom bottom navigation composable that use [NavigationBar] from MUI3. It iterate through the
 * [navItemList] to create an [NavigationBarItem] inside the [NavigationBar] composable
 * @param bottomNavList : A list of [NavItemVariant] object which are the navigation destination
 * @param activeNavItem: A [NavItemVariant] item that is the current active destination
 *                           with [BottomNavItemVariant.Trending] as default value
 * @param modifier: A modifier with default value [Modifier]
 */
@Composable
fun BottomNavScreen(
        modifier: Modifier = Modifier.testTag(stringResource(id = R.string.test_tag_bottom_nav)),
        navController: NavHostController,
        onDestinationChanged: (Destination) -> Unit,
) {
    var activeNavItem by remember {
        mutableStateOf<NavItemVariant>(NavItemVariant.Trending)
    }

    navController.addOnDestinationChangedListener { _, destination, _ ->
        activeNavItem = destination.activeNavItem()
    }

    NavigationBar(modifier = modifier, tonalElevation = 5.dp) {
        navItemList.take(5)
            .forEach { navItem ->
                NavigationBarItem(selected = navItem == activeNavItem,
                                  icon = {
                                      Icon(
                                              painter = painterResource(id = navItem.iconId),
                                              contentDescription = stringResource(id = navItem.iconContentDescription)
                                      )
                                  },
                                  label = { Text(text = stringResource(id = navItem.textId)) },
                                  onClick = {
                                      navController.navigate(navItem.destination.route)
                                      onDestinationChanged(navItem.destination)
                                  })
            }
    }
}

@Composable
fun CinematicsNavigationRail(
        modifier: Modifier = Modifier,
        activeNavItem: NavItemVariant,
        onDestinationChanged: (Destination) -> Unit,
) {
    NavigationRail(modifier = modifier.padding(vertical = 50.dp)) {
        navItemList.forEach { navItem ->
            NavigationRailItem(
                    selected = navItem == activeNavItem,
                    onClick = {
                        onDestinationChanged(navItem.destination)
                    },
                    icon = {
                        Icon(
                                painter = painterResource(id = navItem.iconId),
                                contentDescription = stringResource(id = navItem.iconContentDescription)
                        )
                    }
            )
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
sealed class NavItemVariant(
        @StringRes val textId: Int,
        @StringRes val iconContentDescription: Int,
        @DrawableRes val iconId: Int,
        val destination: Destination
) {
    object Trending : NavItemVariant(
            textId = R.string.txt_trending,
            iconContentDescription = R.string.content_descrip_trending,
            iconId = R.drawable.icon_trending_24,
            destination = Destination.TrendingScreen
    )

    object WatchList : NavItemVariant(
            textId = R.string.txt_watch_list,
            iconContentDescription = R.string.content_descrip_watch_list,
            iconId = R.drawable.icon_watch_list_24,
            destination = Destination.WatchListScreen
    )

    object UserProfile : NavItemVariant(
            textId = R.string.txt_user_profile,
            iconContentDescription = R.string.content_descrip_user_profile,
            iconId = R.drawable.icon_profile_24,
            destination = Destination.UserProfileScreen
    )
}


@Preview(showBackground = true)
@Composable
fun BottomNavScreenPreview() {
    CinematicsTheme {
        /*   BottomNavScreen(NavItemVariant.Trending) {

           }*/
    }
}

@Preview(showBackground = true)
@Composable
fun CinematicsNavigationRailPreview() {
    CinematicsTheme {
        CinematicsNavigationRail(
                activeNavItem = navItemList[0],
                onDestinationChanged = {})
    }
}
