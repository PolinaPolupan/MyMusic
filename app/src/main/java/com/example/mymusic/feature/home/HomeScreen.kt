package com.example.mymusic.feature.home

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.mymusic.R
import com.example.mymusic.core.designSystem.component.AnimationBox
import com.example.mymusic.core.designSystem.component.BlurredImageHeader
import com.example.mymusic.core.designSystem.component.SquarePlaceholder
import com.example.mymusic.core.designSystem.component.SquareRoundedCornerPlaceholder
import com.example.mymusic.core.designSystem.component.linearGradientScrim
import com.example.mymusic.core.designSystem.theme.DominantColorState
import com.example.mymusic.core.ui.ScreenHeader
import com.example.mymusic.core.designSystem.theme.DynamicThemePrimaryColorsFromImage
import com.example.mymusic.core.designSystem.theme.MyMusicTheme
import com.example.mymusic.core.designSystem.theme.rememberDominantColorState
import com.example.mymusic.core.designSystem.util.contrastAgainst
import com.example.mymusic.core.designSystem.util.darker
import com.example.mymusic.core.designSystem.util.lerpScrollOffset
import com.example.mymusic.core.ui.FeaturedTrack
import com.example.mymusic.core.ui.PreviewParameterData
import com.example.mymusic.core.ui.TrackCard
import com.example.mymusic.model.Track
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlin.math.absoluteValue
import kotlin.math.max


@Composable
internal fun HomeScreen(
    onTrackClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    onNavigateToLogin: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val authenticatedUiState by viewModel.authenticatedUiState.collectAsStateWithLifecycle()
    val isSyncing by viewModel.isSyncing.collectAsStateWithLifecycle()

    // If not authorized - navigate to login screen
    if (authenticatedUiState is AuthenticatedUiState.NotAuthenticated) {
        LaunchedEffect(key1 = uiState) {
            onNavigateToLogin()
        }
    }
    // Show ui if the data is ready and user is authorized
    else if (authenticatedUiState is AuthenticatedUiState.Success && !isSyncing) {

        val recentlyPlayed = viewModel.recentlyPlayed.collectAsLazyPagingItems()

        HomeContent(
            uiState = uiState,
            userImageUrl = (authenticatedUiState as AuthenticatedUiState.Success).userImageUrl,
            onTrackClick = onTrackClick,
            recentlyPlayed = recentlyPlayed,
            modifier = modifier
                .fillMaxSize(),
        )
    }
    // If the data is not ready - show placeholders instead of ui
    else {
        HomeContent(
            uiState = HomeUiState.Loading,
            userImageUrl = "",
            onTrackClick = {},
            recentlyPlayed = flowOf(PagingData.from(emptyList<Track>())).collectAsLazyPagingItems(),
            modifier = modifier
                .fillMaxSize(),
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun HomeContent(
    uiState: HomeUiState,
    userImageUrl: String?,
    onTrackClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    recentlyPlayed: LazyPagingItems<Track>
) {
    val scrollState = rememberScrollState()

    val surfaceColor = MaterialTheme.colorScheme.surface
    val dominantColorState = rememberDominantColorState { color ->
        // We want a color which has sufficient contrast against the surface color
        color.contrastAgainst(surfaceColor) >= 3f
    }

    var pageCount by remember {
        mutableIntStateOf(3)
    }

    if (uiState is HomeUiState.Success) {
        pageCount = uiState.topPicks.size * 100
    }

    val pagerState = rememberPagerState(
        initialPage = pageCount / 2,
        pageCount = { pageCount }
    )


    DynamicThemePrimaryColorsFromImage(dominantColorState) {
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = modifier
                .background(MaterialTheme.colorScheme.primary.darker(0.95f))
        ) {

            Box(modifier = Modifier) {
                BlurredImageHeader(
                    uiState = uiState,
                    pagerState = pagerState,
                    scrollState = scrollState,
                    dominantColorState = dominantColorState,
                )
                Box(modifier = Modifier
                    .linearGradientScrim(
                        color = Color.Black,
                        start = Offset(0f, 550f),
                        end = Offset(0f, 800f),
                    )
                    .matchParentSize()
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                ScreenHeader(
                    titleRes = R.string.listen_now,
                    imageUrl = userImageUrl,
                    isLoading = uiState is HomeUiState.Loading,
                    modifier = Modifier.padding(horizontal = 16.dp))
                Spacer(modifier = Modifier.height(16.dp))
                TopPicks(
                    uiState = uiState,
                    onTrackClick = onTrackClick,
                    pagerState = pagerState
                )
                Spacer(modifier = Modifier.height(16.dp))
                RecentlyPlayed(
                    onTrackClick = onTrackClick,
                    recentlyPlayed = recentlyPlayed
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.player_with_bottom_app_bar_height)))
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun BlurredImageHeader(
    uiState: HomeUiState,
    dominantColorState: DominantColorState,
    pagerState: PagerState,
    scrollState: ScrollState,
    modifier: Modifier = Modifier
) {
    if (uiState is HomeUiState.Loading) {
        Spacer(modifier = Modifier.height(280.dp))
    }
    if (uiState is HomeUiState.Success && uiState.topPicks.isNotEmpty()) {

        val page = pagerState.currentPage % uiState.topPicks.size

        var pageInd by remember {
            mutableIntStateOf(page)
        }

        val onUpdate = {
            pageInd = page
        }

        val launchChange by rememberUpdatedState(newValue = onUpdate)

        LaunchedEffect(key1 = page) {
            delay(1500)
            launchChange()
            dominantColorState.updateColorsFromImageUrl(uiState.topPicks[pageInd].album.imageUrl)
        }

        BlurredImageHeader(
            imageUrl = uiState.topPicks[pageInd].album.imageUrl,
            alpha = max(
                0.0f,
                lerpScrollOffset(
                    scrollState = scrollState,
                    valueMin = 100f,
                    valueMax = 300f,
                    reverse = true
                ) - 0.3f
            ),
            modifier = modifier
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun TopPicks(
    uiState: HomeUiState,
    pagerState: PagerState,
    onTrackClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.top_picks),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(
                    horizontal = dimensionResource(id = R.dimen.padding_large),
                    vertical = dimensionResource(id = R.dimen.padding_small)
                )
        )
        when (uiState) {
            HomeUiState.Loading -> {

                HorizontalPager(
                    state = pagerState,
                    pageSpacing = 25.dp,
                    pageSize = PageSize.Fixed(dimensionResource(id = R.dimen.top_picks_card_min_size)),
                    contentPadding = PaddingValues(
                        horizontal = dimensionResource(id = R.dimen.top_picks_card_min_size) / 2
                    ),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensionResource(id = R.dimen.top_picks_card_max_size))
                ) { page ->
                    AnimationBox(
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        SquareRoundedCornerPlaceholder(
                            size = dimensionResource(id = R.dimen.top_picks_card_min_size),
                            modifier = Modifier.carouselPageOffset(pagerState, page)
                        )
                    }
                }
            }
            is HomeUiState.Success -> {

                HorizontalPager(
                    state = pagerState,
                    pageSpacing = 25.dp,
                    pageSize = PageSize.Fixed(dimensionResource(id = R.dimen.top_picks_card_min_size)),
                    contentPadding = PaddingValues(
                        horizontal = dimensionResource(id = R.dimen.top_picks_card_min_size) / 2
                    ),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensionResource(id = R.dimen.top_picks_card_max_size))
                ) { page ->
                    AnimationBox(
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        FeaturedTrack(
                            coverUrl = uiState.topPicks[page % uiState.topPicks.size].album.imageUrl,
                            name = uiState.topPicks[page % uiState.topPicks.size].name,
                            artists = uiState.topPicks[page % uiState.topPicks.size].artists,
                            onClick = { onTrackClick(uiState.topPicks[page % uiState.topPicks.size].id) },
                            modifier = Modifier.carouselPageOffset(pagerState, page),
                            imageModifier = Modifier
                                .graphicsLayer {
                                    // Calculate the absolute offset for the current page from the
                                    // scroll position. We use the absolute value which allows us to mirror
                                    // any effects for both directions
                                    val pageOffset = (
                                            (pagerState.currentPage - page) + pagerState
                                                .currentPageOffsetFraction
                                            )

                                    scaleX = lerp(
                                        start = 1.2f,
                                        stop = 1f,
                                        fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1.2f)
                                    )
                                    scaleY = lerp(
                                        start = 1.2f,
                                        stop = 1f,
                                        fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1.2f)
                                    )
                                }
                        )
                    }
                }
            }
        }
    }
}

@Composable
internal fun RecentlyPlayed(
    recentlyPlayed: LazyPagingItems<Track>,
    onTrackClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.recently_played),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(
                    horizontal = dimensionResource(id = R.dimen.padding_large),
                    vertical = dimensionResource(id = R.dimen.padding_small)
                )
        )
        LazyRow {
            items(items = recentlyPlayed.itemSnapshotList) { track ->
                AnimationBox(
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    TrackCard(
                        name = track!!.name,
                        artists = track.artists,
                        imageUrl = track.album.imageUrl,
                        onClick = { onTrackClick(track.id) },
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }

            when (recentlyPlayed.loadState.refresh) {
                is LoadState.Error -> {}
                LoadState.Loading -> {
                    items(count = 3) {
                        AnimationBox {
                            SquarePlaceholder(
                                size = dimensionResource(id = R.dimen.track_card_size),
                                modifier = modifier
                                    .padding(start = 8.dp)
                            )
                        }
                    }
                }
                is LoadState.NotLoading -> {}
            }
        }
    }
}



@Preview
@Composable
fun HomePreview() {
    val tracks = PreviewParameterData.tracks
    MyMusicTheme {
        HomeContent(
            uiState = HomeUiState.Success(tracks),
            userImageUrl = null,
            onTrackClick = {},
            recentlyPlayed = flowOf(PagingData.from(PreviewParameterData.tracks)).collectAsLazyPagingItems()
        )
    }
}

@Preview
@Composable
fun HomeLoadingPreview() {
    MyMusicTheme {
        HomeContent(
            uiState = HomeUiState.Loading,
            userImageUrl = null,
            onTrackClick = {},
            recentlyPlayed = flowOf(PagingData.from(emptyList<Track>())).collectAsLazyPagingItems()
        )
    }
}

/**
 * [carouselPageOffset] helper extension function. Computes the scale and the offset of the item
 * of a center-aligned carousel
 */
@OptIn(ExperimentalFoundationApi::class)
fun Modifier.carouselPageOffset(pagerState: PagerState, page: Int) = graphicsLayer {
    // Calculate the absolute offset for the current page from the
    // scroll position. We use the absolute value which allows us to mirror
    // any effects for both directions
    val pageOffset = (
            (pagerState.currentPage - page) + pagerState
                .currentPageOffsetFraction
            ).absoluteValue

    scaleX = lerp(
        start = 1f,
        stop = 1.15f,
        fraction = 1f - pageOffset.coerceIn(0f, 1.15f)
    )
    scaleY = lerp(
        start = 1f,
        stop = 1.15f,
        fraction = 1f - pageOffset.coerceIn(0f, 1.15f)
    )
}