package com.nfd.libgenscan.openLibrary

import android.os.Parcelable

/**
 * This structure matches the response JSON object from Open Library. This is not a complete
 * set of all fields returned in BookData, though. Other fields may be added as necessary.
 */
data class BookResponse(
        val bookData: BookData
)

//@Parcelize
data class BookData(
        val url: String?,
        val title: String?,
        val subtitle: String?,
        val authors: List<Author>?
)//: Parcelable

data class Author(
        val name: String,
        val url: String
)