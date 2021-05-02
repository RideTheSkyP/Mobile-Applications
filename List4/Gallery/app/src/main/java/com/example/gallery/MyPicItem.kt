package com.example.gallery

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.json.JSONObject

@Parcelize
class MyPicItem(val imgId: Int, var desc: String, var rating: Float) : Parcelable
{
    fun toJson(): JSONObject
    {
        println("imgId: $imgId, $desc, $rating")
        return JSONObject().run {
            put("imageId", imgId)
            put("desc", desc)
            put("rating", rating)
        }
    }

    constructor(json: JSONObject): this(
        json.getString("imageId").toInt(),
        json.getString("desc"),
        json.getString("rating").toFloat()
    )
}