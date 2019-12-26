package com.nfd.libgenscan

import com.google.gson.*
import java.lang.reflect.Type

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