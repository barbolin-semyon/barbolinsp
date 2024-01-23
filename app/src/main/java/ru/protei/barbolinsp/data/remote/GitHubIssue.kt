package ru.protei.barbolinsp.data.remote

import com.google.gson.annotations.SerializedName
import java.util.Date

data class GitHubIssue(
    val number: Long,
    val title: String,
    val body: String?,
    @SerializedName("updated_at")
    val updatedAt: Date,
    val state: String?
) {
    constructor(
        number: Long,
        title: String,
        body: String,
        updatedAt: Date
    ) : this(number, title, body, updatedAt, null)
}
