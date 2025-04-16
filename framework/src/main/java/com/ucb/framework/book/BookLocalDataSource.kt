// framework/src/main/java/com/ucb/framework/book/BookLocalDataSource.kt
package com.ucb.framework.book

import android.content.Context
import com.ucb.data.book.IBookLocalDataSource
import com.ucb.domain.Book
import com.ucb.framework.mappers.toBookEntity
import com.ucb.framework.mappers.toBookModel
import com.ucb.framework.persistence.AppRoomDatabase

class BookLocalDataSource(
    private val context: Context
) : IBookLocalDataSource {

    private val bookDao by lazy { AppRoomDatabase.getDatabase(context).bookDao() }

    override suspend fun save(book: Book): Boolean {
        bookDao.insert(book.toBookEntity())
        return true
    }

    override suspend fun findByUser(bookId: String): Book? {
        return bookDao.findById(bookId)?.toBookModel()
    }

    override suspend fun getAll(): List<Book> {
        return bookDao.getAll().map { it.toBookModel() }
    }

    override suspend fun delete(bookId: String): Boolean {
        bookDao.deleteById(bookId)
        return true
    }
}