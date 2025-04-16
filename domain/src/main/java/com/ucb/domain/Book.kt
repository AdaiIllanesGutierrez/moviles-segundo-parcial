package com.ucb.domain

import kotlinx.serialization.Serializable

@Serializable
data class Book(
    val id: String, // Agregado para identificar el libro único
    val title: String,
    val authors: List<String>,
    val year: Int,
    val like: Boolean,
    val coverUrl: String? = null // Opcional para la imagen de portada
) {

}