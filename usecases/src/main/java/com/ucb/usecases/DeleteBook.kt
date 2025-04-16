package com.ucb.usecases

import com.ucb.data.BookRepository
import com.ucb.domain.Book

class DeleteBook(private val bookRepository: BookRepository) {
    suspend operator fun invoke(book: Book) {
        bookRepository.forceDelete(book)
    }
}