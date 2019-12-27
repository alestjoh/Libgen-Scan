package com.nfd.libgenscan.searchModeSelection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nfd.libgenscan.R
import com.nfd.libgenscan.openLibrary.Author

class AuthorAdapter(
        private val authors: List<Author>,
        private val onClick: (authorName: String) -> Unit
) : RecyclerView.Adapter<AuthorViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuthorViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_author, parent, false)
        return AuthorViewHolder(view)
    }

    override fun getItemCount(): Int = authors.size

    override fun onBindViewHolder(holder: AuthorViewHolder, position: Int) {
        holder.button.text = String.format(
                holder.button.context.getString(R.string.search_by_author),
                authors[position].name)

        holder.button.setOnClickListener { onClick(authors[position].name!!) }
    }
}