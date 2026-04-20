package com.mrh.popcorn.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.mrh.popcorn.data.viewmodel.HomeViewModel
import com.mrh.popcorn.ui.screen.HomeScreen
import com.mrh.popcorn.ui.screen.MovieDetailScreen
import kotlinx.serialization.Serializable

@Serializable
object HomeScreenDestination

@Serializable
data class DetailScreenDestination(val movieId: Int)


@SuppressLint("RestrictedApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // Lista con las rutas disponibles en el NavigationBar
    val routes = listOf(
        NavRoute(label = "Inicio", icon = Icons.Filled.Home, routeObject = HomeScreenDestination)
    )

    val homeViewModel: HomeViewModel = viewModel()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                routes.forEach { route ->
                    NavigationBarItem(
                        onClick = {
                            navController.navigate(route.routeObject)
                        },
                        selected = currentDestination?.hasRoute(route.routeObject::class) == true,
                        icon = {
                            Icon(imageVector = route.icon, contentDescription = null)
                        },
                        label = {
                            Text(route.label)
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = HomeScreenDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<HomeScreenDestination> {
                HomeScreen(
                    onMovieClick = { pelicula ->
                        navController.navigate(DetailScreenDestination(movieId = pelicula.id))
                    },
                    viewModel = homeViewModel
                )
            }
            composable<DetailScreenDestination> { backStackEntry ->
                val args = backStackEntry.toRoute<DetailScreenDestination>()
                val peliculaSeleccionada =
                    homeViewModel.popularMovies.collectAsState().value.find { pelicula ->
                        pelicula.id == args.movieId
                    }
                if (peliculaSeleccionada != null) {
                    MovieDetailScreen(
                        movie = peliculaSeleccionada,
                        onBack = {
                            navController.popBackStack()
                        }
                    )
                }
            }

        }
    }


}
















