// framework/src/main/java/com/ucb/framework/persistence/dao/IBookDAO.kt
package com.ucb.framework.persistence

import androidx.room.*
import com.ucb.framework.persistence.BookEntity

@Dao
interface IBookDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(book: BookEntity)

    @Query("SELECT * FROM books WHERE id = :bookId")
    suspend fun findById(bookId: String): BookEntity?

    @Query("SELECT * FROM books")
    suspend fun getAll(): List<BookEntity>

    @Query("DELETE FROM books WHERE id = :bookId")
    suspend fun deleteById(bookId: String)
}