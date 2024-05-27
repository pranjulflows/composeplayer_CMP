package utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import getResourceFile
import io.kamel.core.utils.File
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.launch

@Composable
fun loadImageWithKamel(
    resPath: String,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    var file: File? by remember { mutableStateOf(null) }
    scope.launch {
        file = getResourceFile(resPath)
    }
    file?.let {
        KamelImage(asyncPainterResource(it), contentDescription = null, modifier = modifier)
    }
}

val pastelColors = listOf(
    Color(0xFF77DD77), // Pastel Green
    Color(0xFFAEC6CF), // Pastel Blue
    Color(0xFFF49AC2), // Pastel Pink
    Color(0xFFFFB347), // Pastel Orange
    Color(0xFFB39EB5), // Pastel Purple
    Color(0xFFD5A6BD), // Pastel Lavender
    Color(0xFFFFDF6B), // Pastel Yellow
    Color(0xFFFF6961), // Pastel Red
    Color(0xFFD7A9E3), // Pastel Violet
    Color(0xFF93C572)  // Pastel Lime Green
)
@Composable
inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier =
    composed {
        clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() },
        ) {
            onClick()
        }
    }
