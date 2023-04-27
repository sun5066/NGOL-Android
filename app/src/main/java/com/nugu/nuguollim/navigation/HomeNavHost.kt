package com.nugu.nuguollim.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nugu.nuguollim.design_system.component.NuguBottomNavItem
import com.nugu.nuguollim.ui.home.HomeRoute
import com.nugu.nuguollim.ui.search.SearchRoute


/**
 * 메인 화면에 대한 NavHost
 */
@Composable
fun HomeNavHost(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = NuguBottomNavItem.Home.screenRoute
    ) {
        composable(NuguBottomNavItem.Home.screenRoute) { HomeRoute(navController) }
        composable(NuguBottomNavItem.TemplateSearch.screenRoute) { SearchRoute(navController) }
        composable(NuguBottomNavItem.Community.screenRoute) {}
    }
}