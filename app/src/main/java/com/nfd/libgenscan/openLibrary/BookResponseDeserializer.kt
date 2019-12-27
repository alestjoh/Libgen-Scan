package com.nfd.libgenscan.openLibrary

import com.google.gson.*
import java.lang.reflect.Type

/**
 * The field in the BookResponse JSON object has its key named after the ISBN given,
 * so its name needs to be ignored. The data within it can be retrieved as normal.
 */
class BookResponseDeserializer : JsonDeserializer<BookResponse> {
    override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?): BookResponse {
        val obj: JsonObject = json as JsonObject
        val data = Gson().fromJson(obj[obj.keySet().first()], BookData::class.java)
        return BookResponse(data)
    }
}