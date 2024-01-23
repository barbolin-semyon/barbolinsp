package ru.protei.barbolinsp.data.remote

data class GitHubIssue(
    val number: Long,
    val title: String,
    val body: String,
    val state: String?
) {
    constructor(
        number: Long,
        title: String,
        body: String,
    ) : this(number, title, body, null)
}
