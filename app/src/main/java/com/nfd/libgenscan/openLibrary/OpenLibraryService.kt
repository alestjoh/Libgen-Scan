package com.nfd.libgenscan.openLibrary

import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * This interface contacts the Open Library API to get a book's title and author, given its ISBN.
 */
interface OpenLibraryService {

    /**
     * bibkeys will need to start with "ISBN:", followed by the isbn number.
     * Other queries shouldn't be changed.
     */
    @GET("books")
    fun getBook(
            @Query("bibkeys") isbn: String,
            @Query("format") format: String = "json",
            @Query("jscmd") jscmd: String = "data"): Call<BookResponse>

    companion object {

        private var instance: OpenLibraryService? = null

        fun getInstance(): OpenLibraryService {
            if (instance == null) {
                val bookDeserializer = GsonBuilder()
                        .setLenient()
                        .registerTypeAdapter(BookResponse::class.java, BookResponseDeserializer())
                        .create()
                val retrofit = Retrofit.Builder()
                        .baseUrl("https://openlibrary.org/api/")
                        .addConverterFactory(GsonConverterFactory.create(bookDeserializer))
                        .build()
                instance = retrofit.create(OpenLibraryService::class.java)
            }
            return instance!!
        }
    }
}