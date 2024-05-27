@file:OptIn(ExperimentalFoundationApi::class)

package ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Surface
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
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
import utils.pastelColors
import viewmodel.MusicPlayerViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen() {
    val playerState = rememberPlayerState()
    val player = remember { AudioPlayer(playerState) }
//    val viewModel = viewModel<MusicPlayerViewModel>()
    val viewModel =
        getViewModel(MusicPlayerViewModel(), viewModelFactory { MusicPlayerViewModel() })
    val songData = viewModel.song.collectAsState()
    val scope = rememberCoroutineScope()

    var currentPage = 0

    LaunchedEffect(Unit) {
        viewModel.getSong()

    }
    //TODO Migrate that in viewModel
    scope.launch {
        viewModel.songUrl.collectLatest {
            println("songUrl ${it}")
            if (!it.isNullOrEmpty()) player.addSongsUrls(it.map { it!! })
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {

        // Content with a blurred background

        Column {
            TopAppBar(backgroundColor = Color.White) {
                Text(text = "From ${songData.value?.album?.name ?: "Us"}")
            }

            VerticalPager(
                state = rememberPagerState(pageCount = { 2 }),
                modifier = Modifier.background(color = Color.Transparent)
            ) {
                if(it>=currentPage){
                    player.onNext()
                    currentPage++
                }
//                else{
//                    player.onBack()
//                }
                Box(modifier = Modifier.fillMaxSize()) {
                    Surface(
                        modifier = Modifier.fillMaxSize().graphicsLayer(alpha = 0.8f)
                            .blur(16.dp), // Adjust the alpha value for opacity
//        shape = CircleShape,
                        color = Color.White
                    ) {
                        this@Column.AnimatedVisibility(
                            visible = songData.value?.image?.isNotEmpty() == true,
                        ) {
                            KamelImage(
                                asyncPainterResource(songData.value?.image?.last()?.url!!),
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                            )
                        }


                    }

                    PlayerScreen(isBuffering = playerState.isBuffering,
                        artistName = songData.value?.artists?.primary?.map { it?.name }
                            ?.joinToString(",") ?: "",
                        songName = songData.value?.name ?: "Loading...",
                        totalDuration = playerState.totalDuration,
                        currentDuration = playerState.currentTime,
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
                            player.seekTo(durationSeek.toDouble())
                        })


                }


            }


        }

    }


}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PlayerScreen(
    onPlayPause: (PlayPauseState) -> Unit,
    onSearch: () -> Unit,
    onSeek: (Float) -> Unit,
    isBuffering: Boolean = false,
    songName: String,
    artistName: String,
    songImage: String? = null,
    totalDuration: Long = 0L,
    currentDuration: Long = 0L,
) {
    var playPauseState by remember { mutableStateOf(PlayPauseState.PLAY) }
    var sliderValue by remember { mutableStateOf(0f) }
    sliderValue = currentDuration.toFloat()
    println("Seek Total Value $totalDuration ${sliderValue}  ${currentDuration.toFloat()}")
    Scaffold(
//        backgroundColor= pastelColors.random(),
        backgroundColor = Color.Transparent, bottomBar = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
//                Column(
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    modifier = Modifier.clip(
//                        shape = RoundedCornerShape(20)
//                    ).background(color = Color.White).padding(top = 16.dp, bottom = 16.dp,start = 20.dp,end = 20.dp)
//
//                ) {
                Text(
                    artistName,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                    ),
                )
                Text(
                    songName,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
                        .basicMarquee(delayMillis = 1_500),
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                )
//                }

                Slider(
//                    colors = SliderDefaults.colors(
//                        activeTrackColor = Color(0xFFFFFFFF),
//                        thumbColor = Color(0xFFFFFFAA)
//                    ),
                    modifier = Modifier.padding(horizontal = 20.dp),
                    value = sliderValue,
                    valueRange = 0F..totalDuration.toFloat(),
                    onValueChange = onSeek,
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
                ) {
                    Text(convertSecondsToMinutesAndSeconds(currentDuration))
                    Text(convertSecondsToMinutesAndSeconds(totalDuration))
                }
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
                            modifier = Modifier.padding(horizontal = 0.dp).size(55.dp)
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
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.fillMaxWidth().fillMaxHeight(.15f))
                AnimatedContent(songImage, transitionSpec = {
                    (fadeIn() + slideInVertically(animationSpec = tween(600),
                        initialOffsetY = { fullHeight -> fullHeight })).togetherWith(
                        fadeOut(
                            animationSpec = tween(300)
                        )
                    )
                }) {
                    it?.let {
                        Box(
                            modifier = Modifier.padding(top = 26.dp).width(300.dp).height(300.dp)
                        ) {
                            Card(
                                elevation = 2.dp,
                                shape = RoundedCornerShape(16.dp),
                            ) {
                                KamelImage(
                                    asyncPainterResource(it),
                                    contentDescription = songName,
                                )
                            }
                            if (isBuffering) CircularProgressIndicator(color = Color.White)
                        }

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


}

fun convertSecondsToMinutesAndSeconds(totalSeconds: Long): String {
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    val secondsString = if (seconds < 10) "0$seconds" else seconds.toString()
    val minutesString = if (minutes < 10) "0$minutes" else minutes.toString()
    return "$minutesString:$secondsString"
}
