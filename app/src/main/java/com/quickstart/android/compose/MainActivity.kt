package com.quickstart.android.compose

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.quickstart.android.compose.ui.QuickstartAndroidComposeTheme

sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    object Login: Screen(
            "login",
            R.string.login,
            Icons.Filled.Lock,
    )
    object Home: Screen(
            "home",
            R.string.home,
            Icons.Filled.Home,
    )
    object Profile: Screen(
            "profile",
            R.string.profile,
            Icons.Filled.Person,
    )
}

val items = listOf(
    Screen.Login,
    Screen.Home,
    Screen.Profile,
)

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuickstartAndroidComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
//                    Greeting("Android")
                    Main()
                }
            }
        }
    }
}

@Composable
fun Main() {
    val navController = rememberNavController()
    Scaffold(
            bottomBar = {
                BottomNav(navController)
            }
    ) {
        NavHost(navController, startDestination = Screen.Home.route) {
            composable(Screen.Login.route) { Login() }
            composable(Screen.Home.route) { Home() }
            composable(Screen.Profile.route) { Profile() }
        }
    }
}

@Composable
fun BottomNav(navController: NavController) {
    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)
        items.forEach { screen ->
            BottomNavigationItem(
                    icon = { Icon(screen.icon) },
                    label = { Text(stringResource(screen.resourceId)) },
                    selected = currentRoute == screen.route,
                    onClick = {
                        // Pop back-stack entry to prevent back..back..back..back.. accumulation
                        navController.popBackStack(navController.graph.startDestination, false)
                        // Navigate if not already on the target route
                        if (currentRoute != screen.route) {
                            navController.navigate(screen.route)
                        }
                    },
            )
        }
    }
}

@Composable
fun Home() {
    Greeting(name = "Home page")
}

@Composable
fun Profile() {
    Greeting(name = "Profile page")
}

@Composable
fun Login() {
    Greeting(name = "Login page")
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!", modifier = Modifier.padding(24.dp))
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    QuickstartAndroidComposeTheme {
        Greeting("Android")
    }
}