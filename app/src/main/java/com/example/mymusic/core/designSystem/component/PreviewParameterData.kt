package com.example.mymusic.core.designSystem.component

import com.example.mymusic.model.AlbumType
import com.example.mymusic.model.Artist
import com.example.mymusic.model.Playlist
import com.example.mymusic.model.SimplifiedAlbum
import com.example.mymusic.model.SimplifiedArtist
import com.example.mymusic.model.SimplifiedPlaylist
import com.example.mymusic.model.SimplifiedTrack
import com.example.mymusic.model.Track

object PreviewParameterData {

    val simplifiedAlbums = listOf<SimplifiedAlbum>(
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
            )
        ),
        SimplifiedAlbum(
            id = "53XhwfbYqKCa1cC15pYq2q",
            type = AlbumType.Album,
            imageUrl = "ttps://i.scdn.co/image/ab67616d0000b2735675e83f707f1d7271e5cf8a",
            name = "Evolve",
            artists = listOf(
                SimplifiedArtist(
                    id = "53XhwfbYqKCa1cC15pYq2q",
                    name = "Imagine Dragons"
                )
            ),
        ),
        SimplifiedAlbum(
            id = "2Auw0pTT6EcQdvHNimhLQI",
            type = AlbumType.Album,
            imageUrl = "https://i.scdn.co/image/ab67616d0000b273442b53773d50e1b5369bb16c",
            name = "V",
            artists = listOf(SimplifiedArtist(id = "04gDigrS5kc9YWfZHwBETP", name = "Maroon 5")),
        ),
        SimplifiedAlbum(
            id = "6TElmwQZfd0lIA3yWoBfUm",
            type = AlbumType.Album,
            imageUrl = "https://i.scdn.co/image/ab67616d0000b273d29a7db4c624f7a662c30783",
            name = "Key of Sea",
            artists = listOf(
                SimplifiedArtist(
                    id = "1rL7w1UIwu887SlCVYYMJW",
                    name = "Jennifer Thomas"
                )
            ),
        ),
        SimplifiedAlbum(
            id = "7gbuuXibrC1KoULn6nfxaH",
            type = AlbumType.Album,
            imageUrl = "https://i.scdn.co/image/ab67616d0000b27305271302d0b7ebbaadba7c8a",
            name = "Zwezdnyie voiny: Mest sitkhov (Originalnyi Saundtrek)",
            artists = listOf(
                SimplifiedArtist(
                    id = "3dRfiJ2650SZu6GbydcHNb",
                    name = "John Williams"
                )
            ),
        ),
        SimplifiedAlbum(
            id = "4OAzTjKrZX2tsO64fuvUGA",
            type = AlbumType.Album,
            imageUrl = "https://i.scdn.co/image/ab67616d0000b273ad23694f5312acca60c1c2cb",
            name = "The Way",
            artists = listOf(SimplifiedArtist(id = "2Eg0o1Mok66akOkuOiGdEv", name = "Zack Hemsey")),
        ),
        SimplifiedAlbum(
            id = "3AOeatEAPjy1CKtdkaXaDq",
            type = AlbumType.Album,
            imageUrl = "https://i.scdn.co/image/ab67616d0000b273536f3b6f6b46e6e97742673b",
            name = "Game Of Thrones: Season 8 (Music from the HBO Series)",
            artists = listOf(
                SimplifiedArtist(
                    id = "1hCkSJcXREhrodeIHQdav8",
                    name = "Ramin Djawadi"
                )
            ),
        ),
        SimplifiedAlbum(
            id = "4NGvKveOupPBLw4h0uFmeu",
            type = AlbumType.Album,
            imageUrl = "https://i.scdn.co/image/ab67616d0000b2733454f72d03a754c790792034",
            name = "Experience",
            artists = listOf(
                SimplifiedArtist(
                    id = "2uFUBdaVGtyMqckSeCl0Qj",
                    name = "Ludovico Einaudi"
                )
            ),
        ),
        SimplifiedAlbum(
            id = "4SYYFXoD8abpjsQoravjur",
            type = AlbumType.Album,
            imageUrl = "https://i.scdn.co/image/ab67616d0000b2732a7a0737b075aa279357a7d6",
            name = "Fly",
            artists = listOf(SimplifiedArtist(id = "348Ajoa7Nqzh4RHaGSTgGH", name = "Ludovico Einaudi")),
        )
    )
    val simplifiedTracks = List(size = 10) {
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
    }

    val tracks = listOf(
        Track(
            id = "0",
            album = simplifiedAlbums[1],
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
            album = simplifiedAlbums[0],
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
            album = simplifiedAlbums[0],
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
            album = simplifiedAlbums[2],
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
            album = simplifiedAlbums[0]
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
            album = simplifiedAlbums[0]
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
            album = simplifiedAlbums[0]
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
            album = simplifiedAlbums[0]
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
            album = simplifiedAlbums[0]
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
            album = simplifiedAlbums[0]
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
            album = simplifiedAlbums[0]
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
            album = simplifiedAlbums[0]
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
            album = simplifiedAlbums[0]
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
            album = simplifiedAlbums[0]
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
            album = simplifiedAlbums[0]
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
            album = simplifiedAlbums[0]
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
            album = simplifiedAlbums[0]
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
            album = simplifiedAlbums[0]
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
            album = simplifiedAlbums[0]
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
            album = simplifiedAlbums[0]
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
            album = simplifiedAlbums[0]
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
            album = simplifiedAlbums[0]
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
            album = simplifiedAlbums[0]
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
            album = simplifiedAlbums[0]
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
            album = simplifiedAlbums[0]
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
            album = simplifiedAlbums[0]
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
            album = simplifiedAlbums[0]
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
            album = simplifiedAlbums[0]
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
            album = simplifiedAlbums[0]
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
            album = simplifiedAlbums[0]
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
            album = simplifiedAlbums[0]
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
            album = simplifiedAlbums[0]
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
            album = simplifiedAlbums[0]
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
           album = simplifiedAlbums[0]
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

    val simplifiedArtists = listOf(
        SimplifiedArtist(
            id = "0",
            name = "BLACKPINK"
        ),
        SimplifiedArtist(
            id = "1",
            name = "Adele"
        ),
        SimplifiedArtist(
            id = "04gDigrS5kc9YWfZHwBETP",
            name = "Maroon 5"
        ),
        SimplifiedArtist(
            id = "53XhwfbYqKCa1cC15pYq2q",
            name = "Imagine Dragons"
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

    val simplifiedPlaylists = listOf<SimplifiedPlaylist>(
        SimplifiedPlaylist(
            id = "37i9dQZF1DXaMu9xyX1HzK",
            name = "Best of the Decade For You",
            ownerName = "Spotify",
            imageUrl = "https://i.scdn.co/image/ab67706f00000003ba77a2166a7b66e9a300ffaa"
        ),
        SimplifiedPlaylist(
            id = "37i9dQZF1DX7iB3RCnBnN4",
            name = "BILLIONS CLUB",
            ownerName = "Spotify",
            imageUrl = "https://i.scdn.co/image/ab67706f00000002f6ca7e915476b90299a5b75e"
        ),
        SimplifiedPlaylist(
            id = "66mDstPtgRQoDPvcBwUokA",
            name = "My recommendation playlist",
            ownerName = "Spotify",
            imageUrl = "https://mosaic.scdn.co/640/ab67616d00001e02712701c5e263efc8726b1464ab67616d00001e02b340b496cb7c38d727ff40beab67616d00001e02bfd46639322b597331d9ecefab67616d00001e02dfc2f59568272de50a257f2f"
        ),
        SimplifiedPlaylist(
            id = "5wtuoO5ZsNT5ySbuPD5CRc",
            name = "My recommendation playlist",
            ownerName = "Spotify",
            imageUrl = "https://mosaic.scdn.co/640/ab67616d00001e025d29fc4c51a450bd32135de1ab67616d00001e02b340b496cb7c38d727ff40beab67616d00001e02b3be3b970fc89a02f301c9daab67616d00001e02bfd46639322b597331d9ecef"
        ),
        SimplifiedPlaylist(
            id = "6MSKHpsAlEAwz6USSHvXRh",
            name = "My recommendation playlist",
            ownerName = "Spotify",
            imageUrl = "https://mosaic.scdn.co/640/ab67616d00001e02a200f972bd8b1f9cb76da8c2ab67616d00001e02b340b496cb7c38d727ff40beab67616d00001e02bfd46639322b597331d9ecefab67616d00001e02f062321ad9046a2a99c6bac8"
        ),
        SimplifiedPlaylist(
            id = "37i9dQZF1EUMDoJuT8yJsl",
            name = "Your Top Songs 2021",
            ownerName = "Spotify",
            imageUrl = "https://lineup-images.scdn.co/wrapped-2021-top100_DEFAULT-en.jpg"
        ),
        SimplifiedPlaylist(
            id = "37i9dQZF1DXdfOcg1fm0VG",
            name = "Video Game Soundtracks",
            ownerName = "Spotify",
            imageUrl = "https://i.scdn.co/image/ab67706f00000002c94fd5639e1bd451f55b103c"
        ),
        SimplifiedPlaylist(
            id = "37i9dQZF1DXaUaRhCgtpCo",
            name = "Best of Star Wars",
            ownerName = "Spotify",
            imageUrl = "https://i.scdn.co/image/ab67706f00000002df318081a0b2da5d16202c1e"
        ),
        SimplifiedPlaylist(
            id = "37i9dQZF1DX1tz6EDao8it",
            name = "Iconic Soundtracks",
            ownerName = "Spotify",
            imageUrl = "https://i.scdn.co/image/ab67706f000000026319ff8355868a79d6bcf74a"
        )
    )
}