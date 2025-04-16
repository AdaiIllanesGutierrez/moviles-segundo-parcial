// framework/src/main/java/com/ucb/framework/dto/BookResponseDto.kt
package com.ucb.framework.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BookResponseDto(
    @Json(name = "docs") val docs: List<BookDocDto>,
    @Json(name = "numFound") val totalResults: Int,
    @Json(name = "start") val start: Int,
    @Json(name = "numFoundExact") val exactMatch: Boolean
)

@JsonClass(generateAdapter = true)
data class BookDocDto(
    @Json(name = "key") val key: String,
    @Json(name = "title") val title: String,
    @Json(name = "author_name") val author_name: List<String>?,
    @Json(name = "first_publish_year") val first_publish_year: Int?,
    @Json(name = "cover_edition_key") val cover_edition_key: String?
)