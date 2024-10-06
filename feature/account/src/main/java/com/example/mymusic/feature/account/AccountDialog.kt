package com.example.mymusic.feature.account

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mymusic.core.designsystem.component.NetworkImage
import com.example.mymusic.core.designsystem.theme.MyMusicTheme


@Composable
fun AccountDialog(
    onDismiss: () -> Unit,
    accountViewModel: AccountViewModel = hiltViewModel()
) {
    val startForResult =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult()) { result ->
            run {
                if (result.resultCode == Activity.RESULT_OK) {
                    accountViewModel.handleAuthorizationResponse(result.data!!) {
                        accountViewModel.refresh()
                    }
                }
            }
        }

    val uiState by accountViewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        AccountUiState.Loading -> Unit
        is AccountUiState.Success -> {
            AccountDialogContent(
                name = (uiState as AccountUiState.Success).data.displayName,
                email = (uiState as AccountUiState.Success).data.email,
                imageUrl = (uiState as AccountUiState.Success).data.imageUrl,
                onDismiss = onDismiss,
                onSignOut = { startForResult.launch(accountViewModel.signIn()) }
            )
        }
    }
}

@Composable
fun AccountDialogContent(
    name: String?,
    email: String?,
    imageUrl: String?,
    onDismiss: () -> Unit,
    onSignOut: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        ) {
            IconButton(onClick = onDismiss) {
                Icon(imageVector = Icons.Filled.Close, contentDescription = null)
            }
            Row(
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
            ) {
                NetworkImage(
                    imageUrl = imageUrl ?: "",
                    defaultImageRes = R.drawable.spotify_logo_white_on_green,
                    modifier = Modifier
                        .size(70.dp)
                        .padding(dimensionResource(id = R.dimen.padding_small))
                        .clip(RoundedCornerShape(100))
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_small)))
                Column {
                    Text(
                        text = name ?: "Not loaded",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(text = email ?: "Not loaded")
                    HorizontalDivider(
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)),
                        color = MaterialTheme.colorScheme.primary
                    )
                    OutlinedButton(
                        onClick = onSignOut,
                        contentPadding = PaddingValues(horizontal = dimensionResource(id = R.dimen.padding_medium))
                    ) {
                        Text(text = stringResource(id = R.string.log_out))
                    }
                }
            }
        }
    }

}
@Composable
@Preview
fun AccountDialogPreview() {
    MyMusicTheme {
        AccountDialogContent(
            name = "Polina",
            email = "polupanpolina9@gmail.com",
            onDismiss = {},
            onSignOut = {},
            imageUrl = ""
        )
    }
}


