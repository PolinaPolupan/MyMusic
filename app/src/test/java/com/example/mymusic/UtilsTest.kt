package com.example.mymusic

import com.example.mymusic.core.designSystem.util.artistsString
import com.example.mymusic.model.Artist
import org.junit.Assert.assertEquals
import org.junit.Test


/**
 * [UtilsTest] tests utils functions tests
 */
class UtilsTest {

    @Test
    fun artistsString_createsCorrectString() {
        var artists = listOf(Artist(id = "0", name = "1", imageUrl = null))

        var string = artistsString(artists)

        assertEquals(string, "1")

        artists = listOf(Artist(id = "0", name = "", imageUrl = null))

        string = artistsString(artists)

        assertEquals(string, "")

        artists = listOf(
            Artist(id = "0", name = "Adele", imageUrl = null),
            Artist(id = "0", name = "Maroon 5", imageUrl = null),
            Artist(id = "0", name = "Marshmello", imageUrl = null),
        )

        string = artistsString(artists)

        assertEquals(string, "Adele, Maroon 5, Marshmello")

        artists = listOf(
            Artist(id = "0", name = "Adele", imageUrl = null),
        )

        string = artistsString(artists)

        assertEquals(string, "Adele")
    }
}