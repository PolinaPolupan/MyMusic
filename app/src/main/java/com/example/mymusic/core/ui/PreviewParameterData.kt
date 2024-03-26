package com.example.mymusic.core.ui

import com.example.mymusic.core.model.Album
import com.example.mymusic.core.model.AlbumType
import com.example.mymusic.core.model.Artist
import com.example.mymusic.core.model.Playlist
import com.example.mymusic.core.model.SimplifiedArtist
import com.example.mymusic.core.model.SimplifiedTrack
import com.example.mymusic.core.model.Track

object PreviewParameterData {

    val albums = listOf<Album>(
        Album(
            id = "6nxDQi0FeEwccEPJeNySoS",
            type = AlbumType.Album,
            imageUrl = "https://i.scdn.co/image/ab67616d0000b273407bd04707c463bbb3410737",
            name = "Night Visions",
            artists = listOf(
                SimplifiedArtist(
                    id = "53XhwfbYqKCa1cC15pYq2q",
                    name = "Imagine Dragons"
                )
            ),
            tracks = listOf(
                SimplifiedTrack(
                    id = "62yJjFtgkhUrXktIoSjgP2",
                    name = "Radioactive",
                    artists = listOf(
                        SimplifiedArtist(
                            id = "53XhwfbYqKCa1cC15pYq2q",
                            name = "Imagine Dragons",
                        )
                    )
                )
            )
        )
    )
    val tracks = listOf(
        Track(
            id = "0",
            album = albums[0],
            imageUrl = "https://i.scdn.co/image/ab67616d0000b273838698485511bd9108fadadc",
            name = "New Rules",
            artists = listOf(
                Artist(
                    id = "2ekn2ttSfGqwhhate0LSR0",
                    name = "Dua Lipa",
                    imageUrl = "https://i.scdn.co/image/ab67616d0000b273838698485511bd9108fadadc"
                )
            )
        ),
        Track(
            id = "1",
            album = albums[0],
            imageUrl = "https://images.genius.com/c05b3c4739a994bca85d932f6d6cb586.1000x1000x1.png",
            name = "Sugar",
            artists = listOf(
                Artist(
                    id = "",
                    name = "Maroon 5",
                    imageUrl = ""
                )
            )
        ),
        Track(
            id = "2",
            album = albums[0],
            imageUrl = "https://i.scdn.co/image/ab67616d0000b273dd0a40eecd4b13e4c59988da",
            name = "Happier",
            artists = listOf(
                Artist(
                    id = "",
                    imageUrl = "",
                    name = "Marshmello"
                )
            )
        ),
        Track(
            id = "3",
            album = albums[0],
            imageUrl = "https://i.scdn.co/image/ab67616d0000b27352b2a3824413eefe9e33817a",
            name = "Blank Space",
            artists = listOf(
                Artist(
                    id = "",
                    imageUrl = "",
                    name = "Taylor Swift"
                )
            )
        ),
    )
    val moreLikeArtists = mapOf<Artist, List<Track>>(
        Pair(
            Artist(
            id = "0",
            name = "BLACKPINK",
            imageUrl = "https://i.scdn.co/image/ab6761610000f178c9690bc711d04b3d4fd4b87c"),
            tracks),
        Pair(
            Artist(
                id = "1",
                name = "Major Lazer",
                imageUrl = "https://i.scdn.co/image/ab6761610000f178133f44ab343b35c715a4ac97"),
            tracks),
    )
    val artists = listOf(
        Artist(
            id = "0",
            name = "BLACKPINK",
            imageUrl = "https://i.scdn.co/image/ab6761610000f178c9690bc711d04b3d4fd4b87c"
        ),
        Artist(
            id = "1",
            name = "Adele",
            imageUrl = "https://i.scdn.co/image/ab6761610000e5ebb99cacf8acd537820676726"
        ),
        Artist(
            id = "04gDigrS5kc9YWfZHwBETP",
            name = "Maroon 5",
            imageUrl = "https://i.scdn.co/image/ab6761610000e5ebf8349dfb619a7f842242de77"
        ),
        Artist(
            id = "53XhwfbYqKCa1cC15pYq2q",
            name = "Imagine Dragons",
            imageUrl = "https://i.scdn.co/image/ab6761610000e5eb920dc1f617550de8388f368e"
        )
    )

    val playlists = listOf<Playlist>(
        Playlist(
            id = "37i9dQZF1DXaMu9xyX1HzK",
            name = "Best of the Decade For You",
            ownerName = "Spotify",
            imageUrl = "https://i.scdn.co/image/ab67706f00000003ba77a2166a7b66e9a300ffaa",
            tracks = tracks
        )
    )
}