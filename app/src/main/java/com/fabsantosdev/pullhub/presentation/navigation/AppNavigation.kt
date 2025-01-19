package com.fabsantosdev.pullhub.presentation.navigation

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.fabsantosdev.pullhub.domain.model.PullRequestQuery
import com.fabsantosdev.pullhub.presentation.screens.home.HomeScreen
import com.fabsantosdev.pullhub.presentation.screens.repodetails.PullRequestScreen

sealed class NavigationRoutes(val route: String) {
    data object Home : NavigationRoutes("home")

    data object PullRequests : NavigationRoutes("pull_requests/{ownerName}/{repositoryTitle}") {
        fun createRoute(ownerName: String, repositoryTitle: String): String {
            return "pull_requests/${Uri.encode(ownerName)}/${Uri.encode(repositoryTitle)}"
        }

        const val ARG_OWNER_NAME = "ownerName"
        const val ARG_REPOSITORY_TITLE = "repositoryTitle"
    }
}

@Composable
fun AppNavigation(modifier: Modifier = Modifier, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.Home.route,
        modifier = modifier
    ) {
        composable(NavigationRoutes.Home.route) {
            HomeScreen(
                modifier = Modifier.fillMaxSize(),
                onDetailsClick = { ownerName, repositoryTitle ->
                    val route = NavigationRoutes.PullRequests.createRoute(
                        ownerName = ownerName,
                        repositoryTitle = repositoryTitle
                    )
                    navController.navigate(route)
                }
            )
        }

        composable(
            route = NavigationRoutes.PullRequests.route,
            arguments = listOf(
                navArgument(NavigationRoutes.PullRequests.ARG_OWNER_NAME) {
                    type = NavType.StringType
                },
                navArgument(NavigationRoutes.PullRequests.ARG_REPOSITORY_TITLE) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val ownerName = backStackEntry.arguments?.getString(NavigationRoutes.PullRequests.ARG_OWNER_NAME) ?: ""
            val repositoryTitle = backStackEntry.arguments?.getString(NavigationRoutes.PullRequests.ARG_REPOSITORY_TITLE) ?: ""

            PullRequestScreen(
                modifier = Modifier.fillMaxSize(),
                query = PullRequestQuery(ownerName, repositoryTitle),
                title = "$ownerName/$repositoryTitle",
                onBackPressed = { navController.popBackStack() }
            )
        }
    }
}