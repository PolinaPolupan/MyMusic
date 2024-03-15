package com.example.mymusic.core.ui

import com.example.mymusic.core.model.Artist
import com.example.mymusic.core.model.Track

object PreviewParameterData {
    val moreLikeArtists = mapOf<Artist, List<Track>>(

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
        )
    )
    val tracks = listOf(
        Track(
            id = "0",
            coverUrl = "https://www.musictour.eu/sites/default/files/pictures/blog/dua_packshot_standard_1_1.jpg",
            name = "New Rules",
            artist = "Dua Lipa"
        ),
        Track(
            id = "1",
            coverUrl = "https://images.genius.com/c05b3c4739a994bca85d932f6d6cb586.1000x1000x1.png",
            name = "Sugar",
            artist = "Maroon 5"
        ),
        Track(
            id = "2",
            coverUrl = "https://i.scdn.co/image/ab67616d0000b273dd0a40eecd4b13e4c59988da",
            name = "Happier",
            artist = "Marshmello"
        ),
        Track(
            id = "3",
            coverUrl = "https://i.scdn.co/image/ab67616d0000b27352b2a3824413eefe9e33817a",
            name = "Blank Space",
            artist = "Taylor Swift"
        ),
    )
}