package com.example.mymusic

class Constants {
    companion object {
        val DATASTORE_PREFERENCES_NAME = "AUTH_STATE_PREFERENCE"
        val AUTH_STATE = "AUTH_STATE"

        val SCOPE_EMAIL = "user-read-email"
        val SCOPE_UGC_IMAGE_UPLOAD = "ugc-image-upload"
        val SCOPE_USER_READ_PLAYBACK_STATE = "user-read-playback-state"
        val SCOPE_USER_MODIFY_PLAYBACK_STATE = "user-modify-playback-state"
        val SCOPE_USER_READ_CURRENTLY_PLAYING = "user-read-currently-playing"
        val SCOPE_APP_REMOTE_CONTROL = "app-remote-control"
        val SCOPE_STREAMING = "streaming"
        val SCOPE_PLAYLIST_READ_PRIVATE = "playlist-read-private"
        val SCOPE_PLAYLIST_MODIFY_PRIVATE = "playlist-modify-private"
        val SCOPE_PLAYLIST_MODIFY_PUBLIC = "playlist-modify-public"
        val SCOPE_USER_READ_PRIVATE = "user-read-private"

        val DATA_PICTURE = "picture"
        val DATA_FIRST_NAME = "given_name"
        val DATA_LAST_NAME = "family_name"
        val DATA_EMAIL = "email"

        val CLIENT_ID = "964f3c103fd64e478848b059f9240d73"
        val CODE_VERIFIER_CHALLENGE_METHOD = "S256"
        val MESSAGE_DIGEST_ALGORITHM = "SHA-256"

        val URL_AUTHORIZATION = "https://accounts.spotify.com/authorize"
        val URL_TOKEN_EXCHANGE = "https://accounts.spotify.com/api/token"
        val URL_AUTH_REDIRECT = "com.example.mymusic://callback"

    }
}