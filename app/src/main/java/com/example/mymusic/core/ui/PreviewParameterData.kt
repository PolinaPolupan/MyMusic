package com.example.mymusic.core.ui

import com.example.mymusic.model.AlbumType
import com.example.mymusic.model.Artist
import com.example.mymusic.model.Playlist
import com.example.mymusic.model.SimplifiedAlbum
import com.example.mymusic.model.SimplifiedArtist
import com.example.mymusic.model.SimplifiedPlaylist
import com.example.mymusic.model.SimplifiedTrack
import com.example.mymusic.model.Track

object PreviewParameterData {

    val albums = listOf<SimplifiedAlbum>(
        SimplifiedAlbum(
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
        ),
        SimplifiedAlbum(
            id = "01sfgrNbnnPUEyz6GZYlt9",
            type = AlbumType.Album,
            imageUrl = "https://i.scdn.co/image/ab67616d0000b273838698485511bd9108fadadc",
            name = "Dua Lipa (Deluxe)",
            artists = listOf(SimplifiedArtist(id = "fgyu", name = "Dau Lipa")),
        ),
        SimplifiedAlbum(
            id = "5fy0X0JmZRZnVa2UEicIOl",
            type = AlbumType.Album,
            imageUrl = "https://i.scdn.co/image/ab67616d0000b273b7e976d2b35c767f9012cb72",
            name = "1989",
            artists = listOf(
                SimplifiedArtist(
                    id = "06HL4z0CvFAxyc27GXpf02",
                    name = "Taylor Swift"
                )
            ),
        )
    )
    val simplifiedTracks = listOf(
        SimplifiedTrack(
            id = "0",
            name = "New Rules",
            artists = listOf(
                SimplifiedArtist(
                    id = "2ekn2ttSfGqwhhate0LSR0",
                    name = "Dua Lipa"
                )
            )
        ),
        SimplifiedTrack(
            id = "0",
            name = "New Rules",
            artists = listOf(
                SimplifiedArtist(
                    id = "2ekn2ttSfGqwhhate0LSR0",
                    name = "Dua Lipa"
                )
            )
        ),
        SimplifiedTrack(
            id = "0",
            name = "New Rules",
            artists = listOf(
                SimplifiedArtist(
                    id = "2ekn2ttSfGqwhhate0LSR0",
                    name = "Dua Lipa"
                )
            )
        ),
        SimplifiedTrack(
            id = "0",
            name = "New Rules",
            artists = listOf(
                SimplifiedArtist(
                    id = "2ekn2ttSfGqwhhate0LSR0",
                    name = "Dua Lipa"
                )
            )
        )
    )

    val tracks = listOf(
        Track(
            id = "0",
            album = albums[1],
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
            album = albums[2],
            name = "Blank Space",
            artists = listOf(
                Artist(
                    id = "",
                    imageUrl = "",
                    name = "Taylor Swift"
                )
            )
        ),
        Track(
            id = "62yJjFtgkhUrXktIoSjgP2",
            name = "Radioactive",
            artists = listOf(
                Artist(
                    id = "06HL4z0CvFAxyc27GXpf02",
                    name = "Taylor Swift",
                    imageUrl = ""
                )
            ),
            album = albums[0]
        ),
        Track(
            id = "7lBPn6LpIvbvAihTRvBMig",
            name = "Tiptoe",
            artists = listOf(
                Artist(
                    id = "06HL4z0CvFAxyc27GXpf02",
                    name = "Taylor Swift",
                    imageUrl = ""
                )
            ),
            album = albums[0]
        ),
        Track(
            id = "7MXlTgQeo3IVlMpLnZuhxc",
            name = "It's Time",
            artists = listOf(
                Artist(
                    id = "06HL4z0CvFAxyc27GXpf02",
                    name = "Taylor Swift",
                    imageUrl = ""
                )
            ),
            album = albums[0]
        ),
        Track(
            id = "5qaEfEh1AtSdrdrByCP7qR",
            name = "Demons",
            artists = listOf(
                Artist(
                    id = "06HL4z0CvFAxyc27GXpf02",
                    name = "Taylor Swift",
                    imageUrl = ""
                )
            ),
            album = albums[0]
        ),
        Track(
            id = "213x4gsFDm04hSqIUkg88w",
            name = "On Top Of The World",
            artists = listOf(
                Artist(
                    id = "06HL4z0CvFAxyc27GXpf02",
                    name = "Taylor Swift",
                    imageUrl = ""
                )
            ),
            album = albums[0]
        ),
        Track(
            id = "5gXdinVZqeuDIVxogWzRk0",
            name = "Amsterdam",
            artists = listOf(
                Artist(
                    id = "06HL4z0CvFAxyc27GXpf02",
                    name = "Taylor Swift",
                    imageUrl = ""
                )
            ),
            album = albums[0]
        ),
        Track(
            id = "62yJjFtgkhUrXktIoSjgP2",
            name = "Radioactive",
            artists = listOf(
                Artist(
                    id = "06HL4z0CvFAxyc27GXpf02",
                    name = "Taylor Swift",
                    imageUrl = ""
                )
            ),
            album = albums[0]
        ),
        Track(
            id = "7lBPn6LpIvbvAihTRvBMig",
            name = "Tiptoe",
            artists = listOf(
                Artist(
                    id = "06HL4z0CvFAxyc27GXpf02",
                    name = "Taylor Swift",
                    imageUrl = ""
                )
            ),
            album = albums[0]
        ),
        Track(
            id = "7MXlTgQeo3IVlMpLnZuhxc",
            name = "It's Time",
            artists = listOf(
                Artist(
                    id = "06HL4z0CvFAxyc27GXpf02",
                    name = "Taylor Swift",
                    imageUrl = ""
                )
            ),
            album = albums[0]
        ),
        Track(
            id = "5qaEfEh1AtSdrdrByCP7qR",
            name = "Demons",
            artists = listOf(
                Artist(
                    id = "06HL4z0CvFAxyc27GXpf02",
                    name = "Taylor Swift",
                    imageUrl = ""
                )
            ),
            album = albums[0]
        ),
        Track(
            id = "213x4gsFDm04hSqIUkg88w",
            name = "On Top Of The World",
            artists = listOf(
                Artist(
                    id = "06HL4z0CvFAxyc27GXpf02",
                    name = "Taylor Swift",
                    imageUrl = ""
                )
            ),
            album = albums[0]
        ),
        Track(
            id = "5gXdinVZqeuDIVxogWzRk0",
            name = "Amsterdam",
            artists = listOf(
                Artist(
                    id = "06HL4z0CvFAxyc27GXpf02",
                    name = "Taylor Swift",
                    imageUrl = ""
                )
            ),
            album = albums[0]
        ),
        Track(
            id = "62yJjFtgkhUrXktIoSjgP2",
            name = "Radioactive",
            artists = listOf(
                Artist(
                    id = "06HL4z0CvFAxyc27GXpf02",
                    name = "Taylor Swift",
                    imageUrl = ""
                )
            ),
            album = albums[0]
        ),
        Track(
            id = "7lBPn6LpIvbvAihTRvBMig",
            name = "Tiptoe",
            artists = listOf(
                Artist(
                    id = "06HL4z0CvFAxyc27GXpf02",
                    name = "Taylor Swift",
                    imageUrl = ""
                )
            ),
            album = albums[0]
        ),
        Track(
            id = "7MXlTgQeo3IVlMpLnZuhxc",
            name = "It's Time",
            artists = listOf(
                Artist(
                    id = "06HL4z0CvFAxyc27GXpf02",
                    name = "Taylor Swift",
                    imageUrl = ""
                )
            ),
            album = albums[0]
        ),
        Track(
            id = "5qaEfEh1AtSdrdrByCP7qR",
            name = "Demons",
            artists = listOf(
                Artist(
                    id = "06HL4z0CvFAxyc27GXpf02",
                    name = "Taylor Swift",
                    imageUrl = ""
                )
            ),
            album = albums[0]
        ),
        Track(
            id = "213x4gsFDm04hSqIUkg88w",
            name = "On Top Of The World",
            artists = listOf(
                Artist(
                    id = "06HL4z0CvFAxyc27GXpf02",
                    name = "Taylor Swift",
                    imageUrl = ""
                )
            ),
            album = albums[0]
        ),
        Track(
            id = "5gXdinVZqeuDIVxogWzRk0",
            name = "Amsterdam",
            artists = listOf(
                Artist(
                    id = "06HL4z0CvFAxyc27GXpf02",
                    name = "Taylor Swift",
                    imageUrl = ""
                )
            ),
            album = albums[0]
        ),
        Track(
            id = "4P76CEIXrrWT2cgS1YrTMr",
            name = "Genesis",
            artists = listOf(
                Artist(
                    id = "06HL4z0CvFAxyc27GXpf02",
                    name = "Taylor Swift",
                    imageUrl = ""
                )
            ),
            album = albums[0]
        ),
        Track(
            id = "2OWKDTonST8HNko3dBlPPp",
            name = "Lost In Your Light (feat. Miguel)",
            artists = listOf(
                Artist(
                    id = "06HL4z0CvFAxyc27GXpf02",
                    name = "Taylor Swift",
                    imageUrl = ""
                )
            ),
            album = albums[0]
        ),
        Track(
            id = "5eTNdkstwKaNahHf41fJ9u",
            name = "Hotter Than Hell",
            artists = listOf(
                Artist(
                    id = "06HL4z0CvFAxyc27GXpf02",
                    name = "Taylor Swift",
                    imageUrl = ""
                )
            ),
            album = albums[0]
        ),
        Track(
            id = "7FCfMXYTIiQ9b4hDYs4Iol",
            name = "Be the One",
            artists = listOf(
                Artist(
                    id = "06HL4z0CvFAxyc27GXpf02",
                    name = "Taylor Swift",
                    imageUrl = ""
                )
            ),
            album = albums[0]
        ),
        Track(
            id = "76cy1WJvNGJTj78UqeA5zr",
            name = "IDGAF",
            artists = listOf(
                Artist(
                    id = "06HL4z0CvFAxyc27GXpf02",
                    name = "Taylor Swift",
                    imageUrl = ""
                )
            ),
            album = albums[0]
        ),
        Track(
            id = "2ekn2ttSfGqwhhate0LSR0",
            name = "New Rules",
            artists = listOf(
                Artist(
                    id = "06HL4z0CvFAxyc27GXpf02",
                    name = "Taylor Swift",
                    imageUrl = ""
                )
            ),
            album = albums[0]
        ),
        Track(
            id = "4wNQAG2BzbVkLhdajjxGpR",
            name = "Begging",
            artists = listOf(
                Artist(
                    id = "06HL4z0CvFAxyc27GXpf02",
                    name = "Taylor Swift",
                    imageUrl = ""
                )
            ),
            album = albums[0]
        ),
        Track(
            id = "10nqz67NQWWa7XPq7ycihi",
            name = "Welcome To New York",
            artists = listOf(
                Artist(
                    id = "06HL4z0CvFAxyc27GXpf02",
                    name = "Taylor Swift",
                    imageUrl = ""
                )
            ),
            album = albums[0]
        ),
        Track(
            id = "2ls70nUDfjzm1lSRDuKxmw",
            name = "Blank Space",
            artists = listOf(
                Artist(
                    id = "06HL4z0CvFAxyc27GXpf02",
                    name = "Taylor Swift",
                    imageUrl = ""
                )
            ),
            album = albums[0]
        ),
        Track(
            id = "71PmZqBXH0RUETqxpwlV0w",
            name = "Style",
            artists = listOf(
                Artist(
                    id = "06HL4z0CvFAxyc27GXpf02",
                    name = "Taylor Swift",
                    imageUrl = ""
                )
            ),
            album = albums[0]
        ),
        Track(
            id = "7EpVQjqtKygiwJIASxMWNg",
            name = "Out Of The Woods",
            artists = listOf(
                Artist(
                    id = "06HL4z0CvFAxyc27GXpf02",
                    name = "Taylor Swift",
                    imageUrl = ""
                )
            ),
            album = albums[0]
        ),
       Track(
            id = "1kKlhuQE0HXp1IwBRpaH2P",
            name = "Shake It Off",
            artists = listOf(
                Artist(
                    id = "06HL4z0CvFAxyc27GXpf02",
                    name = "Taylor Swift",
                    imageUrl = ""
                )
            ),
           album = albums[0]
        )
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

    val playlists = List(10) {
        Playlist(
            id = "37i9dQZF1DXaMu9xyX1HzK",
            name = "Best of the Decade For You",
            ownerName = "Spotify",
            imageUrl = "https://i.scdn.co/image/ab67706f00000003ba77a2166a7b66e9a300ffaa",
            tracks = tracks
        )
    }

    val simplifiedPlaylists = List(10) {
        SimplifiedPlaylist(
            id = "37i9dQZF1DXaMu9xyX1HzK",
            name = "Best of the Decade For You",
            ownerName = "Spotify",
            imageUrl = "https://i.scdn.co/image/ab67706f00000003ba77a2166a7b66e9a300ffaa"
        )
    }
}