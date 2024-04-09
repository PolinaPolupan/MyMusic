package com.example.mymusic.core.designSystem.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mymusic.R
import com.example.mymusic.core.designSystem.theme.MyMusicTheme

@Composable
fun ScreenHeader(
    @StringRes titleRes: Int,
    onAvatarClick: () -> Unit,
    @DrawableRes avatarImageRes: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(42.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                stringResource(
                    id = titleRes
                ),
                style = MaterialTheme.typography.displaySmall,
            )
            IconButton(
                modifier = Modifier.clip(RoundedCornerShape(100)),
                onClick = onAvatarClick
            ) {
                Image(
                    painter = painterResource(id = avatarImageRes),
                    contentDescription = null
                )
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
            onAvatarClick = {},
            avatarImageRes = R.drawable.images
        )
    }
}
