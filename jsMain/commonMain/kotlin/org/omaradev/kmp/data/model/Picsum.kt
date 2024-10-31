package org.omaradev.kmp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Picsum(
    @SerialName("author")
    var author: String?,
    @SerialName("download_url")
    var downloadUrl: String,
    @SerialName("height")
    var height: Int?,
    @SerialName("id")
    var id: String?,
    @SerialName("url")
    var url: String,
    @SerialName("width")
    var width: Int?
)