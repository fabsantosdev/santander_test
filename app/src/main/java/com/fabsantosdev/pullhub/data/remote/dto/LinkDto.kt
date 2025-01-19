package com.fabsantosdev.pullhub.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LinkDto(
    @SerializedName("href") val href: String
)