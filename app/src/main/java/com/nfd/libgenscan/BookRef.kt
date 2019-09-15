package com.nfd.libgenscan

import java.util.ArrayList
import java.util.Arrays

import me.dm7.barcodescanner.zbar.BarcodeFormat
import android.content.Intent
import android.net.Uri

/**
 * @author Alexander Ronsse-Tucherov
 * @version 2016-08-26.
 * Wrapper to hold a reference to some book; provided for options for future expansion (e.g. UPC manipulation)
 */
class BookRef(
        private val id: String,
        format: BarcodeFormat,
        private val parent: MainActivity) {

    companion object {
        private val allowedFormats = Arrays.asList(BarcodeFormat.ISBN10, BarcodeFormat.ISBN13)
        private val bookList = ArrayList<BookRef>() //currently dead, but likely will help with history

        fun addToList(b: BookRef) {
            bookList.add(b)
        }
    }

    init {
        if (!isAllowed(format)) {
            throw IllegalArgumentException("Format not supported")
        }
    }

    private fun isAllowed(b: BarcodeFormat): Boolean {
        return allowedFormats.contains(b)
    }

    //TODO: add more libraries, possibly ways of handling other types of barcodes (search by UPC?)
    fun searchBook() {
        val uri = "http://libgen.io/search.php?req=" + id +
                "&lg_topic=libgen&open=0&view=simple&res=25&phrase=1&column=identifier"
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        parent.fire(browserIntent)
    }
}
