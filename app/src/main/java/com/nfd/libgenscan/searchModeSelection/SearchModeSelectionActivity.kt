package com.nfd.libgenscan.searchModeSelection

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.nfd.libgenscan.R
import com.nfd.libgenscan.openLibrary.BookData
import kotlinx.android.synthetic.main.activity_search_mode_selection.*

class SearchModeSelectionActivity : Activity() {

    private lateinit var book: BookData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_mode_selection)

        book = intent.getParcelableExtra(BOOK_REF)!!
        textView_bookData_searchMode.text = book.toString()

        button_isbnSearch_searchMode.setOnClickListener {
            searchLibgen(LibgenUri.getIsbnUri(book.isbn ?: ""))
        }

        button_titleSearch_searchMode.setOnClickListener {
            searchLibgen(LibgenUri.getTitleUri(book.title ?: ""))
        }

        recyclerView_authors_searchMode.adapter = AuthorAdapter(
                book.authors ?: emptyList()) {
            searchLibgen(LibgenUri.getAuthorUri(it))
        }
        recyclerView_authors_searchMode.layoutManager = LinearLayoutManager(this)
    }

    private fun searchLibgen(uri: Uri) {
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }

    companion object {
        const val BOOK_REF = "BOOK REF"
    }
}
