package com.example.mymusic.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mymusic.R
import com.example.mymusic.core.designSystem.component.NetworkImage
import com.example.mymusic.core.designSystem.icon.MyMusicIcons
import com.example.mymusic.core.designSystem.theme.MyMusicTheme
import com.example.mymusic.core.model.Track

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerCard(
    coverUrl: String,
    name: String,
    artist: String,
    isPaused: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
        OutlinedCard(
            onClick = onClick,
            colors = CardDefaults.outlinedCardColors(
                containerColor = Color.Black.copy(alpha = 0.3f)
            ),
            modifier = modifier
                .width(dimensionResource(id = R.dimen.player_card_width))
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                NetworkImage(
                    imageUrl = coverUrl,
                    modifier = Modifier
                        .size(70.dp)
                )
                Column(
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.padding_small))
                        .weight(2f)
                ) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .padding(bottom = 4.dp)
                    )
                    Text(
                        text = artist,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.padding_small))
                ) {
                    IconButton(
                        onClick = {/*TODO*/},
                        modifier = Modifier.size(50.dp)
                    ) {
                        Icon(
                            imageVector = MyMusicIcons.SkipPrevious,
                            contentDescription = stringResource(id = R.string.skip_previous),
                            modifier = Modifier.size(50.dp)
                        )
                    }
                    if (isPaused) {
                        IconButton(
                            onClick = {/*TODO*/},
                            modifier = Modifier.size(50.dp)
                        ) {
                            Icon(
                                imageVector = MyMusicIcons.Play,
                                contentDescription = stringResource(id = R.string.skip_previous),
                                modifier = Modifier.size(50.dp)
                            )
                        }
                    } else {
                        IconButton(
                            onClick = {/*TODO*/},
                            modifier = Modifier.size(50.dp)
                        ) {
                            Icon(
                                imageVector = MyMusicIcons.Pause,
                                contentDescription = stringResource(id = R.string.skip_previous),
                                modifier = Modifier.size(50.dp)
                            )
                        }
                    }
                    IconButton(
                        onClick = {/*TODO*/},
                        modifier = Modifier.size(50.dp)
                    ) {
                        Icon(
                            imageVector = MyMusicIcons.SkipNext,
                            contentDescription = stringResource(id = R.string.skip_next),
                            modifier = Modifier.size(50.dp)
                        )
                    }
                }
            }
        }
    
}

@Preview
@Composable
fun PlayerCardPreview() {
    MyMusicTheme {
        val mockTrack = Track("0", imageUrl = "")
        PlayerCard(mockTrack.imageUrl, mockTrack.name, mockTrack.artist, false, onClick = {})
    }
}