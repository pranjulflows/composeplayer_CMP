@file:OptIn(ExperimentalFoundationApi::class)

package ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import core.ImagesRes
import core.PlayPauseState
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import player.AudioPlayer
import player.rememberPlayerState
import utils.loadImageWithKamel
import viewmodel.MusicPlayerViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen() {
    val playerState = rememberPlayerState()
    val player = remember { AudioPlayer(playerState) }
    val viewModel = viewModel<MusicPlayerViewModel>()

    val songData = viewModel.song.collectAsState()
    var sliderValue by remember { mutableStateOf(0f) }
    val scope = rememberCoroutineScope()


    LaunchedEffect(Unit) {
        viewModel.getSong()
    }
    scope.launch {
        viewModel.songUrl.collectLatest {
            println("songUrl ${it}")
            if (it != null) player.addSongsUrls(listOf(it))
        }
    }
//    songData.value?.let { }
//        LazyColumn(state = rememberLazyListState()){}
    Column {
        TopAppBar(backgroundColor = Color.White) {
            Text(text = "Compose Player")
        }
        VerticalPager(state = rememberPagerState(pageCount = { 5 })) {
            PlayerScreen(songName = "${songData?.value?.name}",
                songImage = songData.value?.image?.get(2)?.url,
                onPlayPause = { state ->

                    println("${viewModel.songUrl.value}")
                    when (state) {
                        PlayPauseState.PLAY -> player.onPause()
                        PlayPauseState.PAUSE -> player.onPlay()
                    }
                },
                onSearch = { println("vuogougovoubou ${songData.value?.name}") },
                onSeek = { durationSeek ->
                    sliderValue = durationSeek.toFloat()
                })
        }
    }
}

@Composable
fun PlayerScreen(
    onPlayPause: (PlayPauseState) -> Unit,
    onSearch: () -> Unit,
    onSeek: (Double) -> Unit,
    songName: String,
    songImage: String? = null,
    sliderValue: Float = 0f,
) {
    var playPauseState by remember { mutableStateOf(PlayPauseState.PLAY) }

    Scaffold(bottomBar = {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Text(
                "Artist Name",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF117777),
                ),
            )
            Text(
                songName,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
                    .basicMarquee(delayMillis = 1_500),
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
            )

            Slider(
                colors = SliderDefaults.colors(activeTrackColor = Color(0xFF9570FF)),
                modifier = Modifier.padding(horizontal = 20.dp),
                value = sliderValue,
                onValueChange = {
//                    sliderValue = it
                    onSeek(sliderValue.toDouble())
                },
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                IconButton(onClick = {
                    playPauseState =
                        if (playPauseState == PlayPauseState.PLAY) PlayPauseState.PAUSE else PlayPauseState.PLAY
                    onPlayPause(playPauseState)
                }) {
                    loadImageWithKamel(
                        playPauseState.icon,
                        modifier = Modifier.padding(horizontal = 10.dp).size(55.dp)
                            .padding(vertical = 10.dp),
                    )
                }

                IconButton(onClick = onSearch) {
                    loadImageWithKamel(
                        ImagesRes.searchIcon,
                        modifier = Modifier.size(40.dp),
                    )
                }
            }
        }
    }) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                songImage?.let {
                    KamelImage(asyncPainterResource(it), contentDescription = songName)
                } ?: run {
                    loadImageWithKamel(
                        modifier = Modifier.padding(horizontal = 56.dp, vertical = 16.dp)
                            .fillMaxWidth().height(300.dp).border(
                                shape = RoundedCornerShape(20.dp),
                                width = 0.dp,
                                color = Color.White,
                            ),
                        resPath = "drawable/song_ph.png",
                    )
                }


            }
        }
    }


}
