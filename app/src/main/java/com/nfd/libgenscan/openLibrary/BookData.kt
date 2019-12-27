package com.nfd.libgenscan.openLibrary

import android.os.Parcel
import android.os.Parcelable

/**
 * This structure matches the response JSON object from Open Library. This is not a complete
 * set of all fields returned in BookData, though. Other fields may be added as necessary.
 */
data class BookResponse(
        val bookData: BookData
)

data class BookData(
        val url: String?,
        val title: String?,
        val subtitle: String?,
        val authors: List<Author>?,
        var isbn: String? = null
): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.createTypedArrayList(Author),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(url)
        parcel.writeString(title)
        parcel.writeString(subtitle)
        parcel.writeTypedList(authors)
        parcel.writeString(isbn)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BookData> {
        override fun createFromParcel(parcel: Parcel): BookData {
            return BookData(parcel)
        }

        override fun newArray(size: Int): Array<BookData?> {
            return arrayOfNulls(size)
        }
    }
}

data class Author(
        val name: String?,
        val url: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Author> {
        override fun createFromParcel(parcel: Parcel): Author {
            return Author(parcel)
        }

        override fun newArray(size: Int): Array<Author?> {
            return arrayOfNulls(size)
        }
    }
}
