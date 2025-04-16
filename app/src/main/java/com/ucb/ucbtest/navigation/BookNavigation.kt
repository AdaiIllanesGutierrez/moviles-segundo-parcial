package com.ucb.ucbtest.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ucb.ucbtest.book.BookScreen
import com.ucb.ucbtest.book.LikedBooksScreen

@Composable
fun BookNavigation(
    navController: NavHostController = rememberNavController(),
    startDestination: String = BookScreens.BookScreen.route,
    builder: NavGraphBuilder.() -> Unit = {
        bookNavigation(navController)
    }
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        builder = builder
    )
}

fun NavGraphBuilder.bookNavigation(
    navController: NavHostController,
    bookScreenContent: @Composable () -> Unit = { BookScreen(navController) },
    likedBooksScreenContent: @Composable () -> Unit = { LikedBooksScreen(navController) }
) {
    composable(BookScreens.BookScreen.route) {
        bookScreenContent()
    }
    composable(BookScreens.LikedBooksScreen.route) {
        likedBooksScreenContent()
    }
}

sealed class BookScreens(val route: String) {
    object BookScreen : BookScreens("book_screen")
    object LikedBooksScreen : BookScreens("liked_books_screen")

    // Para navegación con argumentos en el futuro
    companion object {
        fun fromRoute(route: String?): BookScreens = when (route?.substringBefore("/")) {
            BookScreen.route -> BookScreen
            LikedBooksScreen.route -> LikedBooksScreen
            null -> BookScreen
            else -> throw IllegalArgumentException("Route $route not recognized")
        }
    }
}