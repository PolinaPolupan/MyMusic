package com.example.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.designsystem.component.MyMusicGradientBackground
import com.example.designsystem.component.MyMusicIcons
import com.example.designsystem.component.ScreenHeader
import com.example.designsystem.theme.MyMusicTheme


@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel()
) {
    SearchContent(
        onSearchTriggered = viewModel::onSearchTriggered,
        onSearchQueryChanged = viewModel::onSearchQueryChanged,
        searchQuery = viewModel.searchQuery,
        recentSearches = viewModel.recentSearches,
        modifier = modifier.testTag("search")
    )
}

@Composable
fun SearchContent(
    searchQuery: String,
    recentSearches: List<String>,
    onSearchTriggered: (String) -> Unit,
    onSearchQueryChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    MyMusicGradientBackground(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            ScreenHeader(
                isLoading = true,
                titleRes = R.string.search,
                AccountDialog = { com.example.account.AccountDialog(onDismiss = it) },
                imageUrl = "",
                modifier = Modifier.padding(horizontal = 16.dp))
            SearchToolbar(
                searchQuery = searchQuery,
                onSearchQueryChanged = onSearchQueryChanged,
                onSearchTriggered = onSearchTriggered,
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp)
            )
            RecentSearches(
                recentSearches = recentSearches,
                modifier = Modifier
                    .padding(16.dp)
            )
        }
    }
}

@Composable
private fun SearchToolbar(
    modifier: Modifier = Modifier,
    onSearchQueryChanged: (String) -> Unit,
    searchQuery: String = "",
    onSearchTriggered: (String) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth(),
    ) {
        SearchTextField(
            onSearchQueryChanged = onSearchQueryChanged,
            onSearchTriggered = onSearchTriggered,
            searchQuery = searchQuery,
            modifier = Modifier
        )
    }
}

@Composable
private fun SearchTextField(
    onSearchQueryChanged: (String) -> Unit,
    searchQuery: String,
    onSearchTriggered: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    val onSearchExplicitlyTriggered = {
        keyboardController?.hide()
        onSearchTriggered(searchQuery)
    }

    TextField(
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            focusedContainerColor = MaterialTheme.colorScheme.inverseOnSurface.copy(alpha = 0.8f),
            unfocusedContainerColor = MaterialTheme.colorScheme.inverseOnSurface.copy(alpha = 0.5f),
            disabledContainerColor = MaterialTheme.colorScheme.inverseOnSurface.copy(alpha = 0.5f)
        ),
        leadingIcon = {
            Icon(
                imageVector = MyMusicIcons.Search,
                contentDescription = stringResource(
                    id = R.string.search,
                ),
                tint = MaterialTheme.colorScheme.onSurface,
            )
        },
        trailingIcon = {
            if (searchQuery.isNotEmpty()) {
                IconButton(
                    onClick = {
                        onSearchQueryChanged("")
                    },
                ) {
                    Icon(
                        imageVector = MyMusicIcons.Close,
                        contentDescription = stringResource(
                            id = R.string.close,
                        ),
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        },
        placeholder = { Text(text = stringResource(id = R.string.hinted_search_text))},
        onValueChange = {
            if ("\n" !in it) onSearchQueryChanged(it)
        },
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .onKeyEvent {
                if (it.key == Key.Enter) {
                    onSearchExplicitlyTriggered()
                    true
                } else {
                    false
                }
            }
            .testTag("searchTextField"),
        shape = RoundedCornerShape(32.dp),
        value = searchQuery,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchExplicitlyTriggered()
            },
        ),
        maxLines = 1,
        singleLine = true,
    )
}

@Composable
private fun RecentSearches(
    recentSearches: List<String>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.recent_searches),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium
        )
        for (search in recentSearches) {
            Text(
                text = search,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

@Preview
@Composable
fun RecentSearchesPreview() {
    MyMusicTheme {
        RecentSearches(
            recentSearches = listOf("new rules", "hello", "happier", "dua lipa")
        )
    }
}

@Preview
@Composable
fun ToolBarPreview() {
    MyMusicTheme {
        SearchToolbar(
            onSearchQueryChanged = {},
            onSearchTriggered = {}
        )
    }
}

@Preview
@Composable
fun SearchPreview() {
    MyMusicTheme {
        SearchContent(
            onSearchTriggered = {},
            onSearchQueryChanged = {},
            searchQuery = "",
            recentSearches = listOf()
        )
    }
}
