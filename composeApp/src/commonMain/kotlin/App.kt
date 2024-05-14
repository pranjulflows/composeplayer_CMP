import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview


fun App() {

    MaterialTheme {
        MusicPlayer()

    }

}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun MusicPlayer() {

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

                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Gray,
                    disabledTextColor = Color.Transparent,
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )
            IconButton(
                onClick = {

                },
                modifier = Modifier.fillMaxWidth().padding(end = 4.dp)
                    .border(
                        border = BorderStroke(color = Color(0xFFD3D3D3), width = 1.dp),
                        shape = RoundedCornerShape(10),
                    )
            ) {
                Text(text = "Search" )
            }
        }

//        KamelImage(){

//        }


    }
}
