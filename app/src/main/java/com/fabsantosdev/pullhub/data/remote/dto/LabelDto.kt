package com.fabsantosdev.pullhub.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LabelDto(
    @SerializedName("id") val id: Long,
    @SerializedName("node_id") val nodeId: String,
    @SerializedName("url") val url: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String?,
    @SerializedName("color") val color: String,
    @SerializedName("default") val isDefault: Boolean
)