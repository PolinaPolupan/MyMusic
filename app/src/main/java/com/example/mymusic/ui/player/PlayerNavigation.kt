package com.example.mymusic.ui.player

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.VisibleForTesting
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mymusic.designSystem.util.EnterAnimation
import java.net.URLEncoder

private val URL_CHARACTER_ENCODING = Charsets.UTF_8.name()

@VisibleForTesting
internal const val TRACK_ID_ARG = "trackId"

fun NavController.navigateToPlayer(trackId: String) {
    val encodedId = URLEncoder.encode(trackId, URL_CHARACTER_ENCODING)
    navigate("topic_route/$encodedId") {
        launchSingleTop = true
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.playerScreen(
    onBackClick: () -> Unit,
) {
    composable(
        route = "topic_route/{$TRACK_ID_ARG}",
        arguments = listOf(
            navArgument(TRACK_ID_ARG) { type = NavType.StringType },
        ),
    ) {
        Player(onBackClick = onBackClick)
    }
}