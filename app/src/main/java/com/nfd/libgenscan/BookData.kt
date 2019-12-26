package com.nfd.libgenscan

data class BookResponse(
        val bookData: BookData
)

data class BookData(
        val url: String?,
        val title: String?,
        val subtitle: String?,
        val authors: List<Author>
)

data class Author(
        val name: String,
        val url: String
)