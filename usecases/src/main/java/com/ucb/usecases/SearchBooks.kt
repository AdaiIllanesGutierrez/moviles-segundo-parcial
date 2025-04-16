package com.ucb.usecases

import com.ucb.data.NetworkResult
import com.ucb.data.BookRepository
import com.ucb.domain.Book

class SearchBooks(private val bookRepository: BookRepository) {
    suspend operator fun invoke(query: String): NetworkResult<List<Book>> {
        return bookRepository.searchBooks(query)
    }
}