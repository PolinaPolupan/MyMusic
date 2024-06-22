package com.example.mymusic.feature.account

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
import androidx.compose.material3.Divider
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
import com.example.mymusic.R
import com.example.mymusic.core.designSystem.component.NetworkImage
import com.example.mymusic.core.designSystem.theme.MyMusicTheme

@Composable
fun AccountDialog(
    onDismiss: () -> Unit,
    onSignOut: () -> Unit,
    accountViewModel: AccountViewModel = hiltViewModel()
) {
    val uiState by accountViewModel.uiState.collectAsStateWithLifecycle()
    /*TODO: Pass an image uri*/
    AccountDialogContent(
        name = uiState.name,
        email = uiState.email,
        imageUrl = "",
        onDismiss = onDismiss,
        onSignOut = { onSignOut()
            accountViewModel.signOut() }
    )
}

@Composable
fun AccountDialogContent(
    name: String,
    email: String,
    imageUrl: String,
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
                    imageUrl = imageUrl,
                    modifier = Modifier
                        .size(70.dp)
                        .padding(dimensionResource(id = R.dimen.padding_small))
                        .clip(RoundedCornerShape(100))
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_small)))
                Column {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(text = email)
                    Divider(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)),
                        color = MaterialTheme.colorScheme.primary)
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


