package com.example.network.model

import com.example.database.model.entities.LocalPlaylist
import com.example.database.model.entities.LocalSavedPlaylist
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpotifySimplifiedPlaylist(
    val collaborative: Boolean,
    val description: String,
    @SerialName("external_urls")
    val externalUrls: ExternalUrls?,
    val href: String,
    val id: String,
    val images: List<SpotifyImage>?,
    val name: String,
    val owner: SpotifyOwner,
    val public: Boolean,
    @SerialName("snapshot_id")
    val snapshotId: String?,
    val tracks: SpotifyTracks,
    val type: String,
    val uri: String,
    @SerialName("primary_color")
    val primaryColor: String?
)

@JvmName("SpotifySimplifiedPlaylistToLocalPlaylist")
fun SpotifySimplifiedPlaylist.toLocal() = LocalPlaylist(
    id = id,
    imageUrl = if (!images.isNullOrEmpty()) images!![0].url else "",
    name = name,
    ownerName = owner.displayName
)

@JvmName("SpotifySimplifiedPlaylistListToLocalPlaylists")
fun List<SpotifySimplifiedPlaylist>.toLocal() = map(SpotifySimplifiedPlaylist::toLocal)

@JvmName("SpotifySimplifiedPlaylistToLocalSavedPlaylist")
fun SpotifySimplifiedPlaylist.toLocalSaved() = LocalSavedPlaylist(id = id)

@JvmName("SpotifySimplifiedPlaylistListToLocalSavedPlaylists")
fun List<SpotifySimplifiedPlaylist>.toLocalSaved() = map(SpotifySimplifiedPlaylist::toLocalSaved)
