package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtSpaceTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable

data class Artwork(
    @DrawableRes val image: Int,
    @StringRes val title: Int,
    @StringRes val description: Int,
    @StringRes val artist: Int,
    @StringRes val year: Int
)

private val artworks = listOf(
    Artwork(
        image = R.drawable.photo_1,
        title = R.string.photo_1_title,
        description = R.string.photo_1_description,
        artist = R.string.artist_name,
        year = R.string.photo_year
    ),
    Artwork(
        image = R.drawable.photo_2,
        title = R.string.photo_2_title,
        description = R.string.photo_2_description,
        artist = R.string.artist_name,
        year = R.string.photo_year
    ),
    Artwork(
        image = R.drawable.photo_3,
        title = R.string.photo_3_title,
        description = R.string.photo_3_description,
        artist = R.string.artist_name,
        year = R.string.photo_year
    )
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ArtSpaceTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ArtSpaceScreen()
                }
            }
        }
    }
}

@Composable
fun ArtSpaceScreen() {
    var currentIndex by rememberSaveable { mutableStateOf(0) }
    val artwork = artworks[currentIndex]

    val onPrevious: () -> Unit = {
        currentIndex =
            if (currentIndex == 0) {
                artworks.lastIndex
            } else {
                currentIndex - 1
            }
    }

    val onNext: () -> Unit = {
        currentIndex =
            if (currentIndex == artworks.lastIndex) {
                0
            } else {
                currentIndex + 1
            }
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(24.dp)
    ) {
        val useWideLayout =
            maxWidth >= 600.dp && maxWidth > maxHeight

        if (useWideLayout) {
            val imageHeight = minOf(maxHeight, 480.dp)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ArtworkWall(
                    artwork = artwork,
                    modifier = Modifier
                        .weight(1.2f)
                        .height(imageHeight)
                )

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement =
                        Arrangement.spacedBy(24.dp)
                ) {
                    Text(
                        text = stringResource(R.string.gallery_title),
                        style = MaterialTheme.typography.headlineMedium
                    )

                    ArtworkDescriptor(
                        artwork = artwork,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        text = stringResource(
                            R.string.artwork_position,
                            currentIndex + 1,
                            artworks.size
                        )
                    )

                    DisplayController(
                        onPrevious = onPrevious,
                        onNext = onNext
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement =
                    Arrangement.spacedBy(24.dp)
            ) {
                Text(
                    text = stringResource(R.string.gallery_title),
                    style = MaterialTheme.typography.headlineMedium
                )

                ArtworkWall(
                    artwork = artwork,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(340.dp)
                )

                ArtworkDescriptor(
                    artwork = artwork,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = stringResource(
                        R.string.artwork_position,
                        currentIndex + 1,
                        artworks.size
                    )
                )

                DisplayController(
                    onPrevious = onPrevious,
                    onNext = onNext
                )
            }
        }
    }
}

@Composable
fun ArtworkWall(
    artwork: Artwork,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shadowElevation = 6.dp,
        color = MaterialTheme.colorScheme.surface
    ) {
        Image(
            painter = painterResource(artwork.image),
            contentDescription = stringResource(artwork.description),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        )
    }
}

@Composable
fun ArtworkDescriptor(
    artwork: Artwork,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(artwork.title),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = stringResource(
                    R.string.artwork_metadata,
                    stringResource(artwork.artist),
                    stringResource(artwork.year)
                ),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun DisplayController(
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(
            onClick = onPrevious,
            modifier = Modifier.weight(1f)
        ) {
            Text(stringResource(R.string.previous))
        }

        Button(
            onClick = onNext,
            modifier = Modifier.weight(1f)
        ) {
            Text(stringResource(R.string.next))
        }
    }
}