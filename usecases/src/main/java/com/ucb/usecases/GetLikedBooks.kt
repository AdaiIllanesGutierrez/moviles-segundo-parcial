package com.ucb.usecases

import com.ucb.data.BookRepository
import com.ucb.domain.Book

class GetLikedBooks(private val bookRepository: BookRepository) {
    suspend operator fun invoke(): List<Book> {
        return bookRepository.getLikedBooks()
    }
}