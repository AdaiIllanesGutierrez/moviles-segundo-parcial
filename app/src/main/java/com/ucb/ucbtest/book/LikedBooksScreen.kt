package com.ucb.ucbtest.book

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import kotlinx.coroutines.delay
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ucb.domain.Book

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LikedBooksScreen(
    navController: NavController,
    viewModel: BookViewModel = hiltViewModel()
) {
    val likedBooks by viewModel.likedBooks.collectAsState()
    val message by viewModel.message.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadLikedBooks()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Libros Favoritos") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when {
                likedBooks.isEmpty() -> {
                    EmptyFavorites()
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(likedBooks) { book ->
                            BookItem(
                                book = book,
                                onClick = { /* Navegar a detalle */ },
                                onLikeClick = { viewModel.toggleLike(book) }
                            )
                        }
                    }
                }
            }

            message?.let { msg ->
                LaunchedEffect(msg) {
                    delay(3000)
                    viewModel.clearMessage()
                }
                Snackbar(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                ) {
                    Text(msg)
                }
            }
        }
    }
}

@Composable
fun EmptyFavorites() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                Icons.Default.Favorite,
                contentDescription = "Sin favoritos",
                modifier = Modifier.size(48.dp)
            )
            Spacer(Modifier.height(16.dp))
            Text("No tienes libros favoritos aún")
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Busca libros y añádelos a favoritos",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}