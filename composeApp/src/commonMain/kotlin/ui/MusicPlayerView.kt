package ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import org.jetbrains.compose.ui.tooling.preview.Preview
import player.AudioPlayer
import player.rememberPlayerState
import viewmodel.MusicPlayerViewModel

// Old Music Player View
// Not is use right now
@Composable
@Preview
fun MusicPlayer() {
    val viewModel = viewModel { MusicPlayerViewModel() }
    val song = viewModel.song.collectAsState()
    val image = remember { mutableStateOf<String?>(null) }
    val playerState = rememberPlayerState()
    val player = remember { AudioPlayer(playerState) }
    LaunchedEffect(Unit) {
//        song.value.data?.let{
//            it[0].downloadUrl?.map { it?.url!! }?.let { player.addSongsUrls(it) }
//        }
        image.value = song.value?.image?.get(1)?.url
        player.addSongsUrls(listOf("https://aac.saavncdn.com/038/c4264a7fa9010487ee0857403925e5f8_160.mp4"))
    }
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(backgroundColor = Color.White) {
            Text(text = "Compose Player")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                shape = RoundedCornerShape(20),
                modifier = Modifier.fillMaxWidth(.8f).padding(horizontal = 10.dp, vertical = 12.dp),
                value = "",
                onValueChange = {
                },
                leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = "") },
                colors =
                    TextFieldDefaults.textFieldColors(
                        textColor = Color.Gray,
                        disabledTextColor = Color.Transparent,
                        backgroundColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                    ),
            )
            IconButton(
                onClick = {
                },
                modifier =
                    Modifier.fillMaxWidth().padding(end = 4.dp)
                        .border(
                            border = BorderStroke(color = Color(0xFFD3D3D3), width = 1.dp),
                            shape = RoundedCornerShape(10),
                        ),
            ) {
                Text(text = "Search")
            }
        }

//        PlayerView(
//            songImage = image.value,
//            onPlayPause = {
//            player.onPlay(0)
//
//        }, onNext = {
//        }, onPrevious = {
//        }, onSeek = {
//
//            player.seekTo(it)
//        })
    }
}
