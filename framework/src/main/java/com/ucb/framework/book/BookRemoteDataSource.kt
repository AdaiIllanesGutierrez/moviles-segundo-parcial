// framework/src/main/java/com/ucb/framework/book/BookRemoteDataSource.kt
package com.ucb.framework.book

import com.ucb.data.NetworkResult
import com.ucb.data.book.IBookRemoteDataSource
import com.ucb.domain.Book
import com.ucb.framework.service.RetrofitBuilder
import retrofit2.HttpException
import java.io.IOException

class BookRemoteDataSource(
    private val retrofitBuilder: RetrofitBuilder
) : IBookRemoteDataSource {

    override suspend fun searchBooks(query: String): NetworkResult<List<Book>> {
        return try {
            val response = retrofitBuilder.bookService.searchBooks(query)
            if (response.isSuccessful) {
                response.body()?.let { bookResponse ->
                    NetworkResult.Success(bookResponse.docs.map { doc ->
                        Book(
                            id = doc.key,
                            title = doc.title,
                            authors = doc.author_name ?: emptyList(),
                            year = doc.first_publish_year ?: 0,
                            like = false,
                            coverUrl = doc.cover_edition_key?.let {
                                "https://covers.openlibrary.org/b/olid/$it-M.jpg"
                            }
                        )
                    })
                } ?: NetworkResult.Error(Exception("Response body is empty"))
            } else {
                NetworkResult.Error(Exception("HTTP error: ${response.code()}"))
            }
        } catch (e: IOException) {
            NetworkResult.Error(Exception("Network error: ${e.message}"))
        } catch (e: HttpException) {
            NetworkResult.Error(Exception("HTTP exception: ${e.message}"))
        }
    }
}