package com.ucb.data

import com.ucb.data.book.IBookRemoteDataSource
import com.ucb.data.book.IBookLocalDataSource
import com.ucb.domain.Book

class BookRepository (private val remoteDataSource: IBookRemoteDataSource,
                      private val localDataSource: IBookLocalDataSource
) {
    suspend fun searchBooks(query: String): NetworkResult<List<Book>> {
        return remoteDataSource.searchBooks(query)
    }

    suspend fun toggleLike(book: Book): Boolean {
        if (book.like) {
            localDataSource.delete(book.id)
        } else {
            localDataSource.save(book)
        }
        return true
    }

    suspend fun getLikedBooks(): List<Book> {
        return localDataSource.getAll()
    }

    suspend fun isBookLiked(bookId: String): Boolean {
        return localDataSource.findByUser(bookId) != null
    }
}
