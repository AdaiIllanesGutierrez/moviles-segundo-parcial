package com.ucb.usecases

import com.ucb.data.BookRepository

class CheckBookLiked(private val bookRepository: BookRepository) {
    suspend operator fun invoke(bookId: String): Boolean {
        return bookRepository.isBookLiked(bookId)
    }
}