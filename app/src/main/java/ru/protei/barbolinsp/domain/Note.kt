package ru.protei.barbolinsp.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Note(
    @PrimaryKey (autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val text: String,
    val updateAt: Date?,
)
