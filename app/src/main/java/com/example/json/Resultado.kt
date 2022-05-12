package com.example.json

import com.google.gson.annotations.SerializedName

data class Resultado(
    @SerializedName("title") var title: String,
    @SerializedName("url") var url: String,
    @SerializedName("published_date") var published_date: String,
    @SerializedName("media") var media: List<Media>
)

data class Media(
    @SerializedName("media-metadata") var metadata: List<MediaData>
)

data class MediaData(
    @SerializedName("url") var urlmetadata: String
)

