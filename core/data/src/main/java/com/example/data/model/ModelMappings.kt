package com.example.data.model

import com.example.database.model.LocalAlbumWithArtists
import com.example.database.model.LocalRecentlyPlayedWithArtists
import com.example.database.model.LocalSimplifiedTrackWithArtists
import com.example.database.model.LocalTrackWithArtists
import com.example.database.model.entities.LocalAlbum
import com.example.database.model.entities.LocalArtist
import com.example.database.model.entities.LocalPlaylist
import com.example.database.model.entities.LocalRecentlyPlayed
import com.example.database.model.entities.LocalRecommendation
import com.example.database.model.entities.LocalSavedAlbum
import com.example.database.model.entities.LocalSavedPlaylist
import com.example.database.model.entities.LocalSimplifiedArtist
import com.example.database.model.entities.LocalSimplifiedTrack
import com.example.database.model.entities.LocalTrack
import com.example.model.Artist
import com.example.model.SimplifiedAlbum
import com.example.model.SimplifiedArtist
import com.example.model.SimplifiedPlaylist
import com.example.model.SimplifiedTrack
import com.example.model.Track
import com.example.network.model.SavedAlbum
import com.example.network.model.SpotifyAlbum
import com.example.network.model.SpotifyArtist
import com.example.network.model.SpotifyPlayHistoryObject
import com.example.network.model.SpotifySimplifiedArtist
import com.example.network.model.SpotifySimplifiedPlaylist
import com.example.network.model.SpotifySimplifiedTrack
import com.example.network.model.SpotifyTrack

@JvmName("SpotifyPlayHistoryObjectToLocalRecentlyPlayed")
fun SpotifyPlayHistoryObject.toLocal() = LocalRecentlyPlayed(
    id = track.id,
    track = track.toLocalTrack()
)

@JvmName("SpotifyPlayHistoryObjectListToLocalRecentlyPlayed")
fun List<SpotifyPlayHistoryObject>.toLocal() = map(SpotifyPlayHistoryObject::toLocal)

@JvmName("SpotifyAlbumToLocalAlbum")
fun SpotifyAlbum.toLocal() = LocalAlbum(
    id = id,
    type = type,
    imageUrl = if (images.isNotEmpty()) images[0].url else "",
    name = name
)

@JvmName("SavedAlbumToLocalAlbumConversion")
fun SavedAlbum.toLocalAlbum() = LocalAlbum(
    id = album.id,
    type = album.type,
    imageUrl = if (album.images.isNotEmpty()) album.images[0].url else "",
    name = album.name
)

@JvmName("SavedAlbumListToLocalAlbums")
fun List<SavedAlbum>.toLocalAlbum() = map(SavedAlbum::toLocalAlbum)

@JvmName("SavedAlbumToLocalSavedAlbum")
fun SavedAlbum.toLocal() = LocalSavedAlbum(
    id = album.id
)

@JvmName("SavedAlbumListToLocalSavedAlbums")
fun List<SavedAlbum>.toLocal() = map(SavedAlbum::toLocal)

@JvmName("SpotifyArtistToLocalArtist")
fun SpotifyArtist.toLocal() = LocalArtist(
    id = id,
    name = name,
    imageUrl = if (images?.isNotEmpty() == true) images!![0].url else ""
)

@JvmName("SpotifyArtistListToLocalArtists")
fun List<SpotifyArtist>.toLocal() = map(SpotifyArtist::toLocal)

@JvmName("SpotifyArtistToLocalSimplifiedArtist")
fun SpotifyArtist.toLocalSimplified() = LocalSimplifiedArtist(
    id = id,
    name = name
)

@JvmName("SpotifyArtistListToLocalSimplifiedArtists")
fun List<SpotifyArtist>.toLocalSimplified() = map(SpotifyArtist::toLocalSimplified)

@JvmName("SpotifySimplifiedArtistToLocalSimplifiedArtist")
fun SpotifySimplifiedArtist.toLocal() = LocalSimplifiedArtist(
    id = id,
    name = name
)

@JvmName("SpotifySimplifiedArtistListToLocalSimplifiedArtists")
fun List<SpotifySimplifiedArtist>.toLocal() = map(SpotifySimplifiedArtist::toLocal)

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

@JvmName("SpotifySimplifiedTrackToLocalSimplifiedTrack")
fun SpotifySimplifiedTrack.toLocal() = LocalSimplifiedTrack(
    id = id,
    name = name
)

@JvmName("SpotifySimplifiedTrackListToLocalSimplifiedTracks")
fun List<SpotifySimplifiedTrack>.toLocal() = map(SpotifySimplifiedTrack::toLocal)

@JvmName("SpotifyTrackToLocalTrackConversion")
fun SpotifyTrack.toLocalTrack() = LocalTrack(
    id = id,
    album = album.toLocal(),
    name = name
)

@JvmName("SpotifyTrackToLocalSimplifiedTrackConversion")
fun SpotifyTrack.toLocalSimplifiedTrack() = LocalSimplifiedTrack(
    id = id,
    name = name
)

@JvmName("SpotifyTrackToLocalRecommendation")
fun SpotifyTrack.toLocalRecommendation() = LocalRecommendation(
    id = id
)

@JvmName("SpotifyTrackListToLocalRecommendations")
fun List<SpotifyTrack>.toLocalRecommendations() = map(SpotifyTrack::toLocalRecommendation)

@JvmName("LocalAlbumWithArtistsToExternalSimplifiedAlbum")
fun LocalAlbumWithArtists.toExternalSimplified(): SimplifiedAlbum =
    SimplifiedAlbum(
        id = album.id,
        type = album.type.toAlbumType(),
        imageUrl = album.imageUrl,
        name = album.name,
        artists = simplifiedArtists.map { it.toExternal() }
    )

@JvmName("LocalRecentlyPlayedWithArtistsToExternalTrack")
fun LocalRecentlyPlayedWithArtists.toExternal() = Track(
    id = trackHistory.track.id,
    album = trackHistory.track.album.toExternalSimplified(albumArtists.toExternal()),
    name = trackHistory.track.name,
    artists = artists.toExternal()
)

@JvmName("LocalSimplifiedTrackWithArtistsToExternalSimplifiedTrack")
fun LocalSimplifiedTrackWithArtists.toExternal() = SimplifiedTrack(
    id = simplifiedTrack.id,
    name = simplifiedTrack.name,
    artists = artists.toExternal()
)

@JvmName("LocalSimplifiedTrackWithArtistsListToExternalSimplifiedTracks")
fun List<LocalSimplifiedTrackWithArtists>.toExternal() = map(LocalSimplifiedTrackWithArtists::toExternal)

@JvmName("LocalTrackWithArtistsToExternalTrack")
fun LocalTrackWithArtists.toExternal() = Track(
    id = track.id,
    album = track.album.toExternalSimplified(albumArtists.toExternal()),
    name = track.name,
    artists = artists.toExternal()
)

@JvmName("LocalTrackWithArtistsListToExternalTracks")
fun List<LocalTrackWithArtists>.toExternal() = map(LocalTrackWithArtists::toExternal)

@JvmName("LocalAlbumToExternalSimplifiedAlbum")
fun LocalAlbum.toExternalSimplified(artists: List<SimplifiedArtist>): SimplifiedAlbum =
    SimplifiedAlbum(
        id = id,
        type = type.toAlbumType(),
        imageUrl = imageUrl,
        name = name,
        artists = artists,
    )

@JvmName("StringToAlbumTypeConversion")
fun String.toAlbumType(): com.example.model.AlbumType {
    return when(this) {
        "album" -> com.example.model.AlbumType.Album
        "single" -> com.example.model.AlbumType.Single
        "compilation" -> com.example.model.AlbumType.Compilation
        else -> {
            throw IllegalArgumentException("Unknown type")
        }
    }
}

@JvmName("LocalArtistToExternalArtist")
fun LocalArtist.toExternal() = Artist(
    id = id,
    name = name,
    imageUrl = imageUrl
)

@JvmName("LocalArtistListToExternalArtists")
fun List<LocalArtist>.toExternal() = map(LocalArtist::toExternal)

@JvmName("LocalPlaylistToExternalSimplifiedPlaylist")
fun LocalPlaylist.toExternal() = SimplifiedPlaylist(
    id = id,
    imageUrl = imageUrl,
    name = name,
    ownerName = ownerName ?: ""
)

@JvmName("LocalSimplifiedArtistToExternalSimplifiedArtist")
fun LocalSimplifiedArtist.toExternal() = SimplifiedArtist(
    id = id,
    name = name
)

@JvmName("LocalSimplifiedArtistListToExternalSimplifiedArtists")
fun List<LocalSimplifiedArtist>.toExternal() = map(LocalSimplifiedArtist::toExternal)
