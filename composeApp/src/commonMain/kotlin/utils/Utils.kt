package utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import getResourceFile
import io.kamel.core.config.KamelConfig
import io.kamel.core.utils.File
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.kamel.image.config.svgDecoder
import kotlinx.coroutines.launch


@Composable
fun loadImageWithKamel(resPath: String,modifier: Modifier=Modifier) {
    val scope = rememberCoroutineScope()
    var file: File? by remember { mutableStateOf(null) }
    scope.launch {
        file = getResourceFile(resPath)

    }
    file?.let {
        KamelImage(asyncPainterResource(it), contentDescription = null, modifier=modifier)

    }


}