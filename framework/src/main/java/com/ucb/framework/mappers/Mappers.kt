package com.ucb.framework.mappers

import com.ucb.domain.Book
import com.ucb.domain.Gitalias
import com.ucb.domain.Movie
import com.ucb.framework.dto.AvatarResponseDto
import com.ucb.framework.dto.BookDocDto
import com.ucb.framework.dto.MovieDto
import com.ucb.framework.persistence.BookEntity
import com.ucb.framework.persistence.GitAccount

fun AvatarResponseDto.toModel(): Gitalias {
    return Gitalias(
        login = login,
        avatarUrl = url
    )
}

fun Gitalias.toEntity(): GitAccount {
    return GitAccount(login)
}

fun GitAccount.toModel(): Gitalias {
    return Gitalias(
        alias,
        ""
    )
}

fun Book.toBookEntity(): BookEntity = BookEntity(
    id = id,
    title = title,
    authors = authors.joinToString(","),
    year = year,
    like = like,
    coverUrl = coverUrl
)

fun BookEntity.toBookModel(): Book = Book(
    id = id,
    title = title,
    authors = authors.split(","),
    year = year,
    like = like,
    coverUrl = coverUrl
)
