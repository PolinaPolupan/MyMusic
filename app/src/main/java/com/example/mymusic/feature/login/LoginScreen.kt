package com.example.mymusic.feature.login

import android.app.Activity
import android.content.Intent
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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mymusic.MainActivity
import com.example.mymusic.R
import com.example.mymusic.core.designSystem.component.MyMusicGradientBackground
import com.example.mymusic.core.designSystem.component.MyMusicLoginBackground
import com.example.mymusic.core.designSystem.theme.MyMusicTheme
import com.example.mymusic.core.designSystem.util.saturation

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit
) {
    LoginContent(onLoginClick = onLoginClick)
}

@Composable
fun LoginContent(
    onLoginClick: () -> Unit
) {
    val context = LocalContext.current

    MyMusicLoginBackground(
        contentAlignment = Alignment.TopCenter
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
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.SemiBold)
                )
                Text(
                    text = stringResource(id = R.string.login_description),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    modifier = Modifier.alpha(0.5f)
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = onLoginClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary.saturation(5f)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(64.dp)
                        .height(48.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.login),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    )
                }
                Spacer(modifier = Modifier.weight(4f))
            }
        }
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
        LoginContent(onLoginClick = {})
    }
}
