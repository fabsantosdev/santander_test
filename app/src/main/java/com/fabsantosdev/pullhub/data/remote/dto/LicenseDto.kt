package com.fabsantosdev.pullhub.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LicenseDto(
    @SerializedName("key") val key: String,
    @SerializedName("name") val name: String,
    @SerializedName("spdx_id") val spdxId: String? = null,
    @SerializedName("url") val url: String? = null,
    @SerializedName("node_id") val nodeId: String
)