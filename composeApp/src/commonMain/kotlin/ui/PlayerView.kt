package ui


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
import org.jetbrains.compose.resources.DrawableResource

import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

import org.jetbrains.compose.ui.tooling.preview.Preview
import utils.loadImageWithKamel


@OptIn(ExperimentalResourceApi::class)
@Preview
@Composable
fun PlayerView(modifier: Modifier = Modifier) {
    var sliderValue by remember { mutableStateOf(0f) }
    Scaffold() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()
        ) {
            Text("MUSIC PLAYER")

            loadImageWithKamel(
                modifier = Modifier.padding(horizontal = 56.dp, vertical = 16.dp).fillMaxWidth()
                    .height(300.dp)
                    .border(shape = RoundedCornerShape(20.dp), width = 0.dp, color = Color.White),
                resPath = "drawable/song_ph.png"
            )
            Text("Song Name", style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold))
            Text(
                "Artist Name", style = TextStyle(
                    fontSize = 18.sp, fontWeight = FontWeight.Normal, color = Color(0xFF117777)
                )
            )

            Slider(colors = SliderDefaults.colors(activeTrackColor = Color(0xFF9570FF)),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
                value = sliderValue,
                onValueChange = {

                    sliderValue = it
                })
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(onClick = {}) {
                    loadImageWithKamel(ImagesRes.previousIc, modifier = Modifier.size(40.dp))
                }

                loadImageWithKamel(
                    ImagesRes.playIc,
                    modifier = Modifier.paint(painterResource(DrawableResource(ImagesRes.playBg)))
                        .size(70.dp)
                        .padding(16.dp)
                )

                IconButton(onClick = {}) {
                    loadImageWithKamel(ImagesRes.nextIc, modifier = Modifier.size(40.dp))
                }

            }

        }

    }

}
