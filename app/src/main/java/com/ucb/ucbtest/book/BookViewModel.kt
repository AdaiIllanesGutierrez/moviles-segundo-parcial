package com.ucb.ucbtest.book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.data.NetworkResult
import com.ucb.domain.Book
import com.ucb.usecases.GetLikedBooks
import com.ucb.usecases.SearchBooks
import com.ucb.usecases.ToggleBookLike
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val searchBooks: SearchBooks,
    private val getLikedBooks: GetLikedBooks,
    private val toggleLikeBook: ToggleBookLike
) : ViewModel() {

    sealed class UIState {
        object Loading : UIState()
        data class Error(val message: String) : UIState()
        data class Success(val books: List<Book>) : UIState()
        object Empty : UIState()
    }

    private val _uiState = MutableStateFlow<UIState>(UIState.Empty)
    val uiState = _uiState.asStateFlow()

    private val _likedBooks = MutableStateFlow<List<Book>>(emptyList())
    val likedBooks = _likedBooks.asStateFlow()

    private val _message = MutableStateFlow<String?>(null)
    val message = _message.asStateFlow()

    fun search(query: String) {
        if (query.isBlank()) {
            _uiState.value = UIState.Error("Ingresa un término de búsqueda")
            return
        }

        _uiState.value = UIState.Loading
        viewModelScope.launch {
            when (val result = searchBooks(query)) {
                is NetworkResult.Success -> {
                    val books = result.data
                    if (books.isEmpty()) {
                        _uiState.value = UIState.Error("No se encontraron libros")
                    } else {
                        _uiState.value = UIState.Success(books)
                    }
                }
                is NetworkResult.Error -> {
                    _uiState.value = UIState.Error(result.exception.message ?: "Error desconocido")
                }
            }
        }
    }

    fun toggleLike(book: Book) {
        viewModelScope.launch {
            try {
                toggleLikeBook(book)
                _message.value = if (book.like) {
                    "${book.title} añadido a favoritos"
                } else {
                    "${book.title} eliminado de favoritos"
                }
                loadLikedBooks() // Actualizar lista de favoritos
            } catch (e: Exception) {
                _message.value = "Error al actualizar favoritos: ${e.message}"
            }
        }
    }

    fun loadLikedBooks() {
        viewModelScope.launch {
            try {
                _likedBooks.value = getLikedBooks()
            } catch (e: Exception) {
                _message.value = "Error al cargar favoritos: ${e.message}"
            }
        }
    }

    fun clearMessage() {
        _message.value = null
    }
}