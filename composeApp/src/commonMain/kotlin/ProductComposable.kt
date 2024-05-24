import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import viewmodel.ProductViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductComposable() {
    val viewmodel = getViewModel(ProductViewModel(), viewModelFactory { ProductViewModel() })
    val products = viewmodel.products.collectAsState()
    LaunchedEffect(Unit) {
        viewmodel.getProducts()
    }
    MaterialTheme {
        println(products.value.toString())
        var showContent by remember { mutableStateOf(false) }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }

//            Text(
//                products.value.toString(),
//                modifier = Modifier.verticalScroll(rememberScrollState())
//            )

            LazyColumn(state = rememberLazyListState()) {
                items(products.value.size) { itemIndex ->

                    Column(
                        Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text("Product Brand: ${products.value[itemIndex].brand}")
                        Text("Product Name: ${products.value[itemIndex].title}")
                        Text("Product Description: ${products.value[itemIndex].description}")
                        Text("Product Price: \$ ${products.value[itemIndex].price}")
                        HorizontalPager(
                            state =
                                rememberPagerState(pageCount = {
                                    products.value[itemIndex].images.size
                                }),
                        ) {
                            KamelImage(
                                modifier = Modifier.fillMaxWidth().height(200.dp),
                                resource = asyncPainterResource(products.value[itemIndex].images[it]),
                                contentDescription = null,
                            )
                        }
                    }
                }
            }
        }
    }
}
