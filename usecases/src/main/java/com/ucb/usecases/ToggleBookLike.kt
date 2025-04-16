package com.ucb.usecases


import com.ucb.data.BookRepository
import com.ucb.domain.Book

class ToggleBookLike(private val bookRepository: BookRepository) {
    suspend operator fun invoke(book: Book): Boolean {
        return bookRepository.toggleLike(book)
    }
}