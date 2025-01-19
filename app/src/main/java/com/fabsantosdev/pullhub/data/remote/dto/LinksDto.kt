package com.fabsantosdev.pullhub.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LinksDto(
    @SerializedName("self") val self: LinkDto,
    @SerializedName("html") val html: LinkDto,
    @SerializedName("issue") val issue: LinkDto,
    @SerializedName("comments") val comments: LinkDto,
    @SerializedName("review_comments") val reviewComments: LinkDto,
    @SerializedName("review_comment") val reviewComment: LinkDto,
    @SerializedName("commits") val commits: LinkDto,
    @SerializedName("statuses") val statuses: LinkDto
)