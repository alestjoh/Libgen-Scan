package com.nfd.libgenscan

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenLibraryService {
    @GET("books")
    fun getBook(
            @Query("bibkeys") isbn: String,
            @Query("format") format: String = "json",
            @Query("jscmd") jscmd: String = "data"): Call<BookResponse>
}