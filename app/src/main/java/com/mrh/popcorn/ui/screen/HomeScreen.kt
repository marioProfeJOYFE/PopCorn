package com.mrh.popcorn.ui.screen

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.mrh.popcorn.data.model.Movie
import com.mrh.popcorn.data.viewmodel.HomeViewModel


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(),
    onMovieClick: (Movie) -> Unit
) {


    val peliculas by viewModel.popularMovies.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LazyVerticalGrid(
        columns = GridCells.Adaptive(200.dp),
        modifier = modifier
    ) {
        if(isLoading){
            items(4){
                GridMovieLoadingCard()
            }
        }else{
            items(peliculas) { pelicula ->
                GridMovieCard(
                    movie = pelicula,
                    onFavouriteClick = { viewModel.toggleFavourite(pelicula.id) },
                    onMovieClick = { onMovieClick(pelicula) }
                )
            }
        }


    }


}

@Composable
fun MovieCard(movie: Movie, onFavouriteClick: () -> Unit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Card(
                modifier = Modifier
                    .width(150.dp)
                    .height(100.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🎬", fontSize = 50.sp, textAlign = TextAlign.Center)
                }
            }
            Column {
                Text(movie.title)
                Text("Rating: ${movie.rating} / 5,0")
            }
            IconButton(
                onClick = onFavouriteClick
            ) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = null,
                    tint = if (movie.isFavourite) Color.Red else MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}


@Composable
fun GridMovieCard(movie: Movie, onFavouriteClick: () -> Unit, onMovieClick: () -> Unit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = onMovieClick
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                // Le pasamos la URL de internet que hemos construido en el Repositorio
                model = movie.posterUrl,
                // Accesibilidad para lectores de pantalla
                contentDescription = "Póster de la película ${movie.title}",
                // Modifier para darle tamaño y recortar las esquinas superiores
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                // ContentScale.Crop hace que la imagen llene el espacio sin deformarse (como centerCrop en XML)
                contentScale = ContentScale.Crop
            )
            Column {
                Text(movie.title)
                Text("Rating: ${movie.rating} / 5,0")
            }
            IconButton(
                onClick = onFavouriteClick
            ) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = null,
                    tint = if (movie.isFavourite) Color.Red else MaterialTheme.colorScheme.primary
                )
            }
        }
    }

}


@Composable
fun LoadingRectangle(
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(8.dp)
){

    val transition = rememberInfiniteTransition(label = "shimmerTransition")

    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        label = "shimmerTranslateAnimation",
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f)
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(x = translateAnimation.value - 200f, y = 0f),
        end = Offset(x = translateAnimation.value, y = translateAnimation.value / 2)
    )

    Box(
        modifier = modifier.background(
            brush = brush, shape = shape
        )
    )
}


@Composable
fun GridMovieLoadingCard() {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🎬", fontSize = 50.sp, textAlign = TextAlign.Center)
                }
            }
            Column {
                LoadingRectangle(
                    modifier = Modifier.fillMaxWidth(0.7f)
                        .height(20.dp)
                        .padding(top = 5.dp)
                )
                LoadingRectangle(
                    modifier = Modifier.fillMaxWidth(0.4f)
                        .height(20.dp)
                        .padding(top = 5.dp)
                )
            }
            IconButton(
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }

}


