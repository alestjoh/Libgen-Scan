package com.nfd.libgenscan.scanning

import me.dm7.barcodescanner.zbar.BarcodeFormat

/**
 * @author Alexander Ronsse-Tucherov
 * @version 2016-08-26.
 * Wrapper to hold a reference to some book; provided for options for future expansion (e.g. UPC manipulation)
 */
data class BookRef(
        val id: String,
        val format: BarcodeFormat) {

    companion object {
        private val allowedFormats = listOf(BarcodeFormat.ISBN10, BarcodeFormat.ISBN13)
        private val bookList = mutableListOf<BookRef>() //currently dead, but likely will help with history

        fun addToList(b: BookRef) {
            bookList.add(b)
        }

        fun isFormatAllowed(b: BarcodeFormat): Boolean {
            return allowedFormats.contains(b)
        }
    }

    init {
        require(isFormatAllowed(format)) { "Format not supported" }
    }

    //TODO: add more libraries, possibly ways of handling other types of barcodes (search by UPC?)
}
