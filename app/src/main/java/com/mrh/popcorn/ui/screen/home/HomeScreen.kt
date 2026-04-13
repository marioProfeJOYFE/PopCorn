package com.mrh.popcorn.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mrh.popcorn.data.model.Movie

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel(), onMovieClick: (Movie) -> Unit) {
    val movies by viewModel.movies.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "🍿 PopCorn",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        containerColor = Color.Transparent
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
        ) {
            items(movies) { movie ->
                MovieCard(movie = movie){
                    onMovieClick(movie)
                }
            }
        }
    }
}

// 1. SPLASH SCREEN: Pantalla de carga de la app
@Composable
fun PopCornSplashScreen() {
    // Usamos Box para superponer elementos y centrar el contenido en toda la pantalla
    Box(
        modifier = Modifier
            .fillMaxSize() // Ocupa toda la pantalla
            .background(Color(0xFFE50914)), // Rojo estilo cine
        contentAlignment = Alignment.Center // Centra todo lo que esté dentro del Box
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "🍿 PopCorn",
                fontSize = 48.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp)) // Añade espacio vertical
            Text(
                text = "Tu catálogo de cine",
                fontSize = 18.sp,
                color = Color.White
            )
        }
    }
}

// 2. MOVIE CARD: Tarjeta estática de una película
@Composable
fun MovieCard(movie: Movie, onMovieClick: (Movie) -> Unit = {}) {
    // Card nos da una tarjeta con sombras y bordes redondeados por defecto
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp), // Margen exterior
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        onClick = { onMovieClick(movie) }
    ) {
        // Row alinea el "póster" a la izquierda y los textos a la derecha
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically // Centra el contenido verticalmente en la fila
        ) {
            // Box para simular el póster de la película (más adelante cargaremos imágenes reales)
            Box(
                modifier = Modifier
                    .size(80.dp, 120.dp) // Ancho y Alto
                    .background(Color.LightGray, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "🎬", fontSize = 32.sp)
            }

            Spacer(modifier = Modifier.width(16.dp)) // Espacio horizontal entre imagen y textos

            // Column apila el Título y la Puntuación uno debajo del otro
            Column(
                modifier = Modifier.fillMaxWidth() // Ocupa el espacio restante del Row
            ) {
                Text(
                    text = movie.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Otro Row anidado para poner una estrella al lado de la nota
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "⭐ ", fontSize = 16.sp)
                    Text(
                        text = "${movie.rating}/10",
                        fontSize = 16.sp,
                        color = Color.DarkGray
                    )
                }
            }
        }
    }
}

// 3. PREVIEWS: Esto nos permite ver el diseño en Android Studio sin compilar la app
@Preview(showBackground = true)
@Composable
fun PreviewSplashScreen() {
    PopCornSplashScreen()
}

@Preview(showBackground = true)
@Composable
fun PreviewMovieCard() {
    // Instanciamos datos de prueba (Mock)
    MovieCard(movie = Movie(1, "Interstellar", "",8.6))
}