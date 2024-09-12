package com.example.login

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.designsystem.component.MyMusicLoginBackground
import com.example.designsystem.component.SpotifyIsNotInstalledDialog
import com.example.designsystem.theme.MyMusicTheme
import com.example.designsystem.util.darker
import com.example.designsystem.util.saturation


@Composable
fun LoginScreen(
    onNavigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val startForResult =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult()) { result ->
            run {
                if (result.resultCode == Activity.RESULT_OK) {
                    viewModel.handleAuthorizationResponse(result.data!!) {
                        viewModel.refresh() // Load user's music
                        onNavigateToHome()
                    }
                }
            }
        }

    LoginContent(
        onLoginClick = { startForResult.launch(viewModel.signIn()) },
        onDismissClick = { /*TODO*/ },
        isSpotifyInstalled = true,
        modifier = modifier
    )
}

@Composable
fun LoginContent(
    onLoginClick: () -> Unit,
    onDismissClick: () -> Unit,
    isSpotifyInstalled: Boolean,
    modifier: Modifier = Modifier
) {
    // Show alert dialog only once
    var wasDialogShown: Boolean by rememberSaveable {
        mutableStateOf(false)
    }
    val transition = updateTransition(isSpotifyInstalled, label = "color state")

    val primaryColor by transition.animateColor( label = "color animation"
    ) { _ ->
        if (isSpotifyInstalled) MaterialTheme.colorScheme.primary.saturation(6f) else Color.DarkGray
    }

    MyMusicLoginBackground(
        color = primaryColor,
        contentAlignment = Alignment.TopCenter,
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)
        ) {
            Spacer(modifier = Modifier
                .fillMaxHeight()
                .weight(2f))
            SpotifyLogo()
            Spacer(modifier = Modifier
                .fillMaxHeight()
                .weight(4f)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(7f)
            ) {
                Text(
                    text = stringResource(id = R.string.login_text),
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.SemiBold)
                )
                Text(
                    text = stringResource(id = R.string.login_description),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    modifier = Modifier.alpha(0.5f)
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = onLoginClick,
                    enabled = isSpotifyInstalled,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primaryColor
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(64.dp)
                        .height(48.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.login),
                        color = primaryColor.darker(0.9f),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    )
                }
                Spacer(modifier = Modifier.weight(4f))
            }
        }
    }

    if (!isSpotifyInstalled && !wasDialogShown)
    {
        SpotifyIsNotInstalledDialog(onDismissClick =  {
            onDismissClick()
            // Show alert dialog only once
            wasDialogShown = true
        })
    }
}

@Composable
private fun SpotifyLogo(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.size(150.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.spotify_logo),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview
@Composable
fun SpotifyLogoPreview() {
    MyMusicTheme {
        SpotifyLogo()
    }
}

@Preview
@Composable
fun AuthorizationScreenPreview() {
    MyMusicTheme {
        LoginContent(onLoginClick = {}, onDismissClick = {} , isSpotifyInstalled = false)
    }
}

@Preview
@Composable
fun AuthorizationScreenAlertPreview() {
    MyMusicTheme {
        LoginContent(onLoginClick = {}, onDismissClick = {}, isSpotifyInstalled = true)
    }
}