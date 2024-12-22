package com.example.mymusic.core.common

class Constants {
    companion object {
        const val DATASTORE_PREFERENCES_NAME = "AUTH_STATE_PREFERENCE"
        const val AUTH_STATE = "AUTH_STATE"
        const val TRACK_ID = "TRACK_ID"
        const val IS_PLAYING = "IS_PLAYING"
        const val SPOTIFY_URI = "SPOTIFY_URI"

        const val SCOPE_EMAIL = "user-read-email"
        const val SCOPE_UGC_IMAGE_UPLOAD = "ugc-image-upload"
        const val SCOPE_USER_READ_PLAYBACK_STATE = "user-read-playback-state"
        const val SCOPE_USER_MODIFY_PLAYBACK_STATE = "user-modify-playback-state"
        const val SCOPE_USER_READ_CURRENTLY_PLAYING = "user-read-currently-playing"
        const val SCOPE_APP_REMOTE_CONTROL = "app-remote-control"
        const val SCOPE_STREAMING = "streaming"
        const val SCOPE_USER_TOP_READ = "user-top-read"
        const val SCOPE_PLAYLIST_READ_PRIVATE = "playlist-read-private"
        const val SCOPE_PLAYLIST_MODIFY_PRIVATE = "playlist-modify-private"
        const val SCOPE_PLAYLIST_MODIFY_PUBLIC = "playlist-modify-public"
        const val SCOPE_USER_READ_PRIVATE = "user-read-private"
        const val SCOPE_USER_READ_RECENTLY_PLAYED = "user-read-recently-played"
        const val SCOPE_USER_LIBRARY_READ = "user-library-read"

        const val IMAGE_URL = "picture"
        const val DATA_DISPLAY_NAME = "display_name"
        const val DATA_EMAIL = "email"

        const val CLIENT_ID = "964f3c103fd64e478848b059f9240d73"
        const val CODE_VERIFIER_CHALLENGE_METHOD = "S256"
        const val MESSAGE_DIGEST_ALGORITHM = "SHA-256"

        const val URL_AUTHORIZATION = "https://accounts.spotify.com/authorize"
        const val URL_TOKEN_EXCHANGE = "https://accounts.spotify.com/api/token"
        const val URL_AUTH_REDIRECT = "com.example.mymusic://callback"
    }
}