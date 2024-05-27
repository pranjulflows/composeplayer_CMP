import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.rememberNavController
//import navigation.NavState
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.HomeScreen

@Composable
@Preview
fun App() {
    MaterialTheme {

//        val navController = rememberNavController()
//        NavHost(navController = navController, startDestination = NavState.HomeScreen.name) {
//            composable(NavState.HomeScreen.name) {
                HomeScreen()
//            }
//        }


    }
}

