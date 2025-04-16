package com.ucb.data.book

import com.ucb.domain.Book

interface IBookLocalDataSource {
    suspend fun save(book: Book): Boolean
    suspend fun findByUser(bookId: String): Book?
    suspend fun getAll(): List<Book>
    suspend fun delete(bookId: String): Boolean
}