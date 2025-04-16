package com.ucb.framework.persistence


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val authors: String,  // Lista de autores como String separado por comas
    val year: Int,
    val like: Boolean,
    val coverUrl: String?
)