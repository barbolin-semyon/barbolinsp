package ru.protei.barbolinsp.domain

import java.util.UUID

data class Note(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val text: String,
)
