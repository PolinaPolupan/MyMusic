package com.example.mymusic.core.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mymusic.R
import com.example.mymusic.core.designSystem.component.MyMusicIcons
import com.example.mymusic.core.designSystem.theme.MyMusicTheme

enum class SortOption(@StringRes val optionRes: Int) {
    RECENTLY_ADDED(R.string.recently_added),
    ALPHABETICAL(R.string.alphabetical),
    CREATOR(R.string.creator)
}

@Composable
fun Sort(
    sortOption: SortOption,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(imageVector = MyMusicIcons.Sort, contentDescription = stringResource(id = R.string.sort))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = stringResource(id = sortOption.optionRes), style = MaterialTheme.typography.titleSmall)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortBottomSheet(
    currentOption: SortOption,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    onSelection: (SortOption) -> Unit,
) {
    ModalBottomSheet(
        windowInsets = BottomSheetDefaults.windowInsets.only(WindowInsetsSides.Bottom),
        onDismissRequest = onDismissRequest,
        containerColor = containerColor,
        modifier = modifier
    ) {
            SortBottomSheetContent(
                currentOption = currentOption,
                onSelection = onSelection
            )
        }

}

@Composable
private fun SortBottomSheetContent(
    currentOption: SortOption,
    onSelection: (SortOption) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.sort_by),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(8.dp)
        )
        HorizontalDivider()
        Spacer(modifier = Modifier.height(8.dp))
        for (option in SortOption.entries) {
            SortOptionSelection(
                labelRes = option.optionRes,
                isSelected = option == currentOption,
                modifier = Modifier
                    .clickable { onSelection(option) }
                    .padding(8.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun SortOptionSelection(
    @StringRes labelRes: Int,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Box(modifier = Modifier.size(24.dp)) {
            if (isSelected) {
                Icon(
                    imageVector = MyMusicIcons.Check,
                    contentDescription = stringResource(id = R.string.sort_option_selection_desc)
                )
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = stringResource(id = labelRes))
    }
}

@Preview
@Composable
fun SortPreview() {
    MyMusicTheme {
        Sort(sortOption = SortOption.RECENTLY_ADDED)
    }
}

@Preview
@Composable
fun SortOptionSelectionPreview() {
    MyMusicTheme {
        SortOptionSelection(labelRes = R.string.recently_added, isSelected = true )
    }
}

@Preview
@Composable
fun SortBottomSheetContentPreview() {
    MyMusicTheme {
        SortBottomSheetContent(currentOption = SortOption.CREATOR, onSelection = {})
    }
}
