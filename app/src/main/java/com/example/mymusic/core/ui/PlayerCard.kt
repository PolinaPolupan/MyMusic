package com.example.mymusic.core.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.res.booleanResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mymusic.R
import com.example.mymusic.core.designSystem.component.NetworkImage
import com.example.mymusic.core.designSystem.icon.MyMusicIcons
import com.example.mymusic.core.designSystem.theme.MyMusicTheme
import com.example.mymusic.core.model.Track

/**
 * Player card which is visible across all of the screens
 * NOTE: In case there are several artists the first artist's name is visible
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun PlayerCard(
    coverUrl: String,
    name: String,
    artistName: String,
    isPaused: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonModifier: Modifier = Modifier
        .size(36.dp)
) {
        OutlinedCard(
            border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)),
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
                        maxLines = 1,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .padding(bottom = 4.dp)
                            .basicMarquee()
                    )
                    Text(
                        text = artistName,
                        maxLines = 1,
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier
                            .basicMarquee()
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.padding_small))
                ) {
                    IconButton(
                        onClick = {/*TODO*/},
                        modifier = buttonModifier
                    ) {
                        Icon(
                            imageVector = MyMusicIcons.SkipPrevious,
                            contentDescription = stringResource(id = R.string.skip_previous),
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    if (isPaused) {
                        IconButton(
                            onClick = {/*TODO*/},
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                imageVector = MyMusicIcons.Play,
                                contentDescription = stringResource(id = R.string.play),
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    } else {
                        IconButton(
                            onClick = {/*TODO*/},
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                imageVector = MyMusicIcons.Pause,
                                contentDescription = stringResource(id = R.string.pause),
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                    IconButton(
                        onClick = {/*TODO*/},
                        modifier = buttonModifier
                    ) {
                        Icon(
                            imageVector = MyMusicIcons.SkipNext,
                            contentDescription = stringResource(id = R.string.skip_next),
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    
}

@PreviewWithBackground
@Composable
fun PlayerCardPreview() {
    MyMusicTheme {
        val mockTrack = PreviewParameterData.tracks[0]
        PlayerCard(mockTrack.imageUrl, mockTrack.name, mockTrack.artists[0].name, false, onClick = {})
    }
}

@PreviewWithBackground
@Composable
fun PlayerCardLongNamePreview() {
    MyMusicTheme {
        val mockTrack = PreviewParameterData.tracks[0]
        PlayerCard(mockTrack.imageUrl, "This is a very very very very long name", mockTrack.artists[0].name, false, onClick = {})
    }
}

@PreviewWithBackground
@Composable
fun PlayerCardLongArtistsNamePreview() {
    MyMusicTheme {
        val mockTrack = PreviewParameterData.tracks[0]
        PlayerCard(mockTrack.imageUrl, mockTrack.name, "This is a very very very very long artists name", false, onClick = {})
    }
}