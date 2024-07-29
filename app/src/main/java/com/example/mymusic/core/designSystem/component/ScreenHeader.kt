package com.example.mymusic.core.designSystem.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mymusic.R
import com.example.mymusic.core.designSystem.theme.MyMusicTheme
import com.example.mymusic.feature.account.AccountDialog

@Composable
fun ScreenHeader(
    @StringRes titleRes: Int,
    imageUrl: String?,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false
) {
    /* Show account when the picture is clicked. */
    var showAccountDialog by rememberSaveable { mutableStateOf(false) }

    if (showAccountDialog) {
        AccountDialog(onDismiss = { showAccountDialog = false })
    }

    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(42.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = titleRes),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.displaySmall,
            )
            IconButton(
                modifier = Modifier.clip(RoundedCornerShape(100)),
                onClick = { showAccountDialog = true },
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CirclePlaceholder(radius = 32.dp, modifier = Modifier.fillMaxSize())
                } else {
                    NetworkImage(
                        imageUrl = imageUrl ?: "",
                        defaultImageRes = R.drawable.spotify_logo_white_on_green
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ScreenHeaderPreview() {
    MyMusicTheme {
        ScreenHeader(
            titleRes = R.string.listen_now,
            imageUrl = "",
            isLoading = false
        )
    }
}

@Preview
@Composable
fun ScreenHeaderLoadingPreview() {
    MyMusicTheme {
        ScreenHeader(
            titleRes = R.string.listen_now,
            imageUrl = "",
            isLoading = true
        )
    }
}
