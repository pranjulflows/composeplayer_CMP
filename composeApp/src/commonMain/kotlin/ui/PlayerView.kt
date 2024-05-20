package ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import musicplayerkmp.composeapp.generated.resources.Res
import musicplayerkmp.composeapp.generated.resources.song_ph
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.ui.tooling.preview.Preview
import utils.Utils

@OptIn(ExperimentalResourceApi::class)
@Preview
@Composable
fun PlayerView(modifier: Modifier = Modifier) {

    Scaffold() {
        Column(modifier = Modifier.fillMaxSize()) {
            Box {
                val painterResource = asyncPainterResource("drawable/song_ph.png")
                KamelImage(painterResource,
                    contentDescription = "Compose",
                    modifier = Modifier.fillMaxSize(),
                    onFailure = { throw it })

            }
        }

    }

}
