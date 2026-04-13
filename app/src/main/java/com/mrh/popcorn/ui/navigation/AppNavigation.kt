package com.mrh.popcorn.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.mrh.popcorn.ui.screen.detail.DetailScreen
import com.mrh.popcorn.ui.screen.detail.DetailViewModel
import com.mrh.popcorn.ui.screen.detail.DetailViewModelFactory
import com.mrh.popcorn.ui.screen.home.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen : NavKey {
    @Serializable
    data object Home : Screen
    @Serializable
    data class Details(val movieId: Int) : Screen
}

@Composable
fun AppNavigation() {
    val backStack = rememberNavBackStack(Screen.Home)

    NavDisplay(
        backStack = backStack,
        onBack = {
            if (backStack.size > 1) {
                backStack.removeAt(backStack.size - 1)
            }
        },
        entryProvider = entryProvider {
            entry<Screen.Home> {
                HomeScreen(
                    onMovieClick = { movie ->
                        backStack.add(Screen.Details(movie.id))
                    }
                )
            }
            entry<Screen.Details> { details ->
                val viewModel: DetailViewModel = viewModel(
                    key = "detail_${details.movieId}",
                    factory = DetailViewModelFactory(details.movieId)
                )
                DetailScreen(
                    viewModel = viewModel,
                    onBack = {
                        if (backStack.size > 1) {
                            backStack.removeAt(backStack.size - 1)
                        }
                    }
                )
            }
        }
    )
}