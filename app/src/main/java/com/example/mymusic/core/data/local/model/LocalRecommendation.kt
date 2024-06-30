package com.example.mymusic.core.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "recommendations",
    foreignKeys = [ForeignKey(
        entity = LocalTrack::class,
        parentColumns = arrayOf("trackId"),
        childColumns = arrayOf("recommendationId"),
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE
    )]
)
data class LocalRecommendation(
    @ColumnInfo(name = "recommendationId")
    @PrimaryKey val id: String
)
