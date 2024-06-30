package com.example.mymusic.core.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// TODO: Fix foreign keys
@Entity(
    tableName = "playHistory",
//    foreignKeys = [ForeignKey(
//        entity = LocalTrack::class,
//        parentColumns = arrayOf("trackId"),
//        childColumns = arrayOf("historyId"),
//        onUpdate = ForeignKey.CASCADE,
//        onDelete = ForeignKey.CASCADE
//    )]
)
data class LocalPlayHistory(
    @ColumnInfo(name = "historyId") @PrimaryKey val id: String,
    val playedAt: String?,
    @Embedded val track: LocalTrack
) {
    @Ignore
    val dateAsDate: Date? = SimpleDateFormat(DATE_FORMAT_IN_DATABASE, Locale.US).parse(playedAt ?: "2024-06-30T09:16:05.828Z")

    companion object {
        const val DATE_FORMAT_IN_DATABASE = "yyyy-MM-dd'T'HH:mm:ss.sss'Z'"
        const val SQLITE_STRFTIME_FORMAT = "%Y-%m-%dT%H:%M:%S"
    }
}