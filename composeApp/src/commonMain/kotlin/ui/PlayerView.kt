package ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.ImagesRes
import core.PlayPauseState
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import utils.loadImageWithKamel
import utils.noRippleClickable

// *
// This compose is not in use in the project.
// */
@OptIn(ExperimentalResourceApi::class, ExperimentalFoundationApi::class)
@Preview
@Composable
fun PlayerView(
    songImage: String?,
    modifier: Modifier = Modifier,
    onPlayPause: () -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    onSeek: (Double) -> Unit,
) {
    var sliderValue by remember { mutableStateOf(0f) }
    var playPauseState by remember { mutableStateOf(PlayPauseState.PLAY) }
    Scaffold {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize(),
        ) {
            if (songImage == null) {
                loadImageWithKamel(
                    modifier =
                        Modifier.padding(horizontal = 56.dp, vertical = 16.dp).fillMaxWidth()
                            .height(300.dp)
                            .border(
                                shape = RoundedCornerShape(20.dp),
                                width = 0.dp,
                                color = Color.White,
                            ),
                    resPath = "drawable/song_ph.png",
                )
            } else {
                KamelImage(
                    asyncPainterResource(songImage),
                    contentDescription = null,
                    modifier =
                        Modifier.padding(horizontal = 56.dp, vertical = 16.dp).fillMaxWidth()
                            .height(300.dp)
                            .border(shape = RoundedCornerShape(20.dp), width = 0.dp, color = Color.White),
                )
            }


            Slider(
                colors = SliderDefaults.colors(activeTrackColor = Color(0xFF9570FF)),
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
                value = sliderValue,
                onValueChange = {
                    sliderValue = it
                    onSeek(sliderValue.toDouble())
                },
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                IconButton(onClick = onPrevious) {
                    loadImageWithKamel(ImagesRes.previousIcon, modifier = Modifier.size(40.dp))
                }

                loadImageWithKamel(
                    playPauseState.icon,
                    modifier =
                        Modifier.paint(painterResource(DrawableResource(ImagesRes.playBg)))
                            .size(70.dp).padding(16.dp).noRippleClickable {
                                onPlayPause()
                                playPauseState =
                                    if (playPauseState == PlayPauseState.PLAY) PlayPauseState.PAUSE else PlayPauseState.PLAY
                            },
                )

                IconButton(onClick = onNext) {
                    loadImageWithKamel(ImagesRes.nextIcon, modifier = Modifier.size(40.dp))
                }
            }
        }
    }
}
