package com.nugu.nuguollim.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nugu.nuguollim.common.data.model.template.Template
import com.nugu.nuguollim.ui.message.detail.MessageDetailRoute
import com.nugu.nuguollim.ui.message.edit.MessageEditRoute

@Composable
fun MessageNavHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    template: Template,
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = MessageScreenType.Detail.screenRoute
    ) {
        composable(MessageScreenType.Detail.screenRoute) {
            MessageDetailRoute(navHostController, template = template)
        }
        composable(MessageScreenType.Edit.screenRoute) {
            MessageEditRoute(navHostController)
        }
    }
}

sealed class MessageScreenType(val screenRoute: String) {
    object Detail : MessageScreenType("detail")
    object Edit : MessageScreenType("edit")
}