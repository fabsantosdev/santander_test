package com.fabsantosdev.pullhub.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PullRequestDto(
    @SerializedName("url") val url: String,
    @SerializedName("id") val id: Long,
    @SerializedName("node_id") val nodeId: String,
    @SerializedName("html_url") val htmlUrl: String,
    @SerializedName("diff_url") val diffUrl: String,
    @SerializedName("patch_url") val patchUrl: String,
    @SerializedName("issue_url") val issueUrl: String,
    @SerializedName("commits_url") val commitsUrl: String,
    @SerializedName("review_comments_url") val reviewCommentsUrl: String,
    @SerializedName("review_comment_url") val reviewCommentUrl: String,
    @SerializedName("comments_url") val commentsUrl: String,
    @SerializedName("statuses_url") val statusesUrl: String,
    @SerializedName("number") val number: Int,
    @SerializedName("state") val state: String,
    @SerializedName("locked") val locked: Boolean,
    @SerializedName("title") val title: String,
    @SerializedName("user") val user: UserDto,
    @SerializedName("body") val body: String?,
    @SerializedName("labels") val labels: List<LabelDto>,
    @SerializedName("milestone") val milestone: MilestoneDto?,
    @SerializedName("active_lock_reason") val activeLockReason: String?,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("closed_at") val closedAt: String?,
    @SerializedName("merged_at") val mergedAt: String?,
    @SerializedName("merge_commit_sha") val mergeCommitSha: String?,
    @SerializedName("assignee") val assignee: UserDto?
)



