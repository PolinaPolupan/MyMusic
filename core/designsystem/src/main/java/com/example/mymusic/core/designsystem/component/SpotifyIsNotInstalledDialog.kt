package com.example.mymusic.core.designsystem.component

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.mymusic.core.designsystem.R


@Composable
fun SpotifyIsNotInstalledDialog(
    onDismissClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/search?q=spotify&c=apps&hl=en&gl=US")) }

    AlertDialog(
        icon = {
               Icon(imageVector = MyMusicIcons.ErrorBorder, contentDescription = null)
        },
        title = {
                Text(text = stringResource(id = R.string.spotify_is_not_installed))
        },
        text = {
               Text(text = stringResource(id = R.string.spotify_is_not_installed_text))
        },
        onDismissRequest = onDismissClick,
        confirmButton = { 
            TextButton(onClick = { /*TODO*/ }) {
                Text(text = stringResource(id = R.string.install))
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissClick() }) {
                Text(text = stringResource(id = R.string.dismiss))
            }
        }
    )
}

@Preview
@Composable
fun SpotifyIsNotInstalledDialogPreview() {
    SpotifyIsNotInstalledDialog(onDismissClick = {})
}

tailrec fun Context.activity(): Activity? = when {
    this is Activity -> this
    else -> (this as? ContextWrapper)?.baseContext?.activity()
}