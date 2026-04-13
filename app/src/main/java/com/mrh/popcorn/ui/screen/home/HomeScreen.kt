package com.mrh.popcorn.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.mrh.popcorn.data.model.Movie
import com.mrh.popcorn.data.remote.RetrofitClient.IMAGE_BASE_URL
import com.mrh.popcorn.ui.screen.search.SearchViewModel

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"
private const val BACKDROP_SIZE = "w1280"
private const val POSTER_SIZE = "w500"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    searchViewModel: SearchViewModel = viewModel(),
    onMovieClick: (Movie) -> Unit
) {
    val movies by viewModel.movies.collectAsState()
    val query by searchViewModel.query.collectAsState()
    val searchResults by searchViewModel.movies.collectAsState()
    val isSearchLoading by searchViewModel.isLoading.collectAsState()

    var expanded by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .semantics { isTraversalGroup = true }
    ) {
        SearchBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .semantics { traversalIndex = 0f },
            inputField = {
                SearchBarDefaults.InputField(
                    query = query,
                    onQueryChange = { searchViewModel.onQueryChange(it) },
                    onSearch = { expanded = false },
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    placeholder = { Text("Buscar peliculas...") },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = "Buscar")
                    },
                    trailingIcon = {
                        if (expanded) {
                            IconButton(onClick = {
                                if (query.isNotEmpty()) {
                                    searchViewModel.onQueryChange("")
                                } else {
                                    expanded = false
                                }
                            }) {
                                Icon(Icons.Default.Close, contentDescription = "Cerrar")
                            }
                        }
                    }
                )
            },
            expanded = expanded,
            onExpandedChange = { expanded = it },
        ) {
            // Contenido expandido: resultados de busqueda
            when {
                isSearchLoading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                query.isBlank() -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Escribe para buscar peliculas",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                searchResults.isEmpty() -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Sin resultados para \"$query\"",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        items(searchResults) { movie ->
                            MovieCard(movie = movie) {
                                expanded = false
                                onMovieClick(movie)
                            }
                        }
                    }
                }
            }
        }

        // Lista de peliculas populares debajo del SearchBar
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .semantics { traversalIndex = 1f },
            contentPadding = PaddingValues(
                top = 110.dp,
                bottom = 8.dp,
                start = 8.dp,
                end = 8.dp
            )
        ) {
            items(movies) { movie ->
                MovieCard(movie = movie) {
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
        onClick = { onMovieClick(movie) }
    ) {
        // Row alinea el "póster" a la izquierda y los textos a la derecha
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically // Centra el contenido verticalmente en la fila
        ) {
            // Box para simular el póster de la película (más adelante cargaremos imágenes reales)
            AsyncImage(
                model = "$IMAGE_BASE_URL$POSTER_SIZE${movie.posterUrl}",
                contentDescription = "Poster de ${movie.title}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(80.dp)
                    .height(120.dp)
                    .shadow(12.dp, RoundedCornerShape(12.dp))
                    .clip(RoundedCornerShape(12.dp)),
            )

            Spacer(modifier = Modifier.width(16.dp)) // Espacio horizontal entre imagen y textos

            // Column apila el Título y la Puntuación uno debajo del otro
            Column(
                modifier = Modifier.fillMaxWidth() // Ocupa el espacio restante del Row
            ) {
                Text(
                    text = movie.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Otro Row anidado para poner una estrella al lado de la nota
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "⭐ ", fontSize = 16.sp)
                    Text(
                        text = "${movie.rating}/10",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
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
    MovieCard(movie = Movie(1, "Interstellar", "", 8.6))
}