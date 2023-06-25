package com.example.cinematics.ui.bottomnav

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cinematics.R
import com.example.cinematics.ui.ui.theme.CinematicsTheme


@Composable
fun BottomNavScreen(bottomNavList: List<BottomNavItemVariant>,
                    activeTab: BottomNavItemVariant = BottomNavItemVariant.Trending,
                    modifier: Modifier = Modifier) {

    NavigationBar(tonalElevation = 5.dp) {
        bottomNavList.forEach { item ->
            NavigationBarItem(selected = item == activeTab,
                              icon = {
                                  Icon(painter = painterResource(id = item.iconId),
                                       contentDescription = stringResource(id = item.iconContentDescription))
                              },
                              label = { Text(text = stringResource(id = item.textId)) },
                              onClick = { /*TODO*/ })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavScreenPreview() {
    CinematicsTheme {
        BottomNavScreen(bottomNavItemList)
    }

}

sealed class BottomNavItemVariant(@StringRes val textId: Int,
                                  @StringRes val iconContentDescription: Int,
                                  @DrawableRes val iconId: Int,
                                  val route: String) {
    object Trending : BottomNavItemVariant(textId = R.string.txt_trending,
                                           iconContentDescription = R.string.content_descrip_trending,
                                           iconId = R.drawable.icon_trending_24,
                                           route = BottomNavTab.TRENDING.route)

    object TopRated : BottomNavItemVariant(textId = R.string.txt_top_rated,
                                           iconContentDescription = R.string.content_descrip_top_rated,
                                           iconId = R.drawable.icon_top_rated_24,
                                           route = BottomNavTab.TOP_RATED.route)

    object WatchList : BottomNavItemVariant(textId = R.string.txt_watch_list,
                                            iconContentDescription = R.string.content_descrip_watch_list,
                                            iconId = R.drawable.icon_watch_list_24,
                                            route = BottomNavTab.WATCH_LIST.route)
}

enum class BottomNavTab(val route: String) {
    TRENDING("trending"),
    TOP_RATED("top-rated"),
    WATCH_LIST("watch_list")
}

val bottomNavItemList = listOf(BottomNavItemVariant.Trending,
                               BottomNavItemVariant.TopRated,
                               BottomNavItemVariant.WatchList)