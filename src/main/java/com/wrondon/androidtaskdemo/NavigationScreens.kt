package com.wrondon.androidtaskdemo

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Cabin
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Login
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.wrondon.androidtaskdemo.viewmodels.UserViewModel
import kotlin.math.roundToInt

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Home : Screen("Home Profile", R.string.home)
    object List : Screen("List", R.string.list)
    object UI : Screen("UI", R.string.ui)
    object Login : Screen("Login", R.string.login)
}


@Composable
fun InitialNavigation(mainViewModel : UserViewModel) {
    val navController = rememberNavController()
    Scaffold(modifier = Modifier.fillMaxWidth(),
        topBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            TopAppBar(
                title = { Text("${currentDestination?.route}") },
                navigationIcon = {
                    AnimatedVisibility(visible = currentDestination?.route!=Screen.Home.route) {
                        IconButton(onClick = {
                            navController.navigate(Screen.Home.route)
                            {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = null)
                        }
                    }
                }
            )
        }) {

        Column(modifier= Modifier
            .padding(horizontal = 10.dp)
            .padding(vertical = 10.dp), verticalArrangement = Arrangement.spacedBy(200.dp)) {
            NavHost(navController = navController, startDestination = Screen.Home.route) {
                composable(Screen.Home.route) { Home(navController) }
                composable(Screen.List.route) { List(navController,mainViewModel) }
                composable(Screen.UI.route) { UI(navController) }
                composable(Screen.Login.route) { Login(navController,mainViewModel) }
            }
        }
    }

}

@Composable
fun Home(navController: NavController) {
    Box(Modifier.fillMaxSize()) {
        Image(
            painterResource(id = R.drawable.picture02),
            contentDescription = "",
            contentScale = ContentScale.FillBounds, // or some other scale
            modifier = Modifier.matchParentSize()
        )
        Column(modifier= Modifier
            .padding(horizontal = 10.dp)
            .padding(vertical = 200.dp) ){

            Button(modifier = Modifier.fillMaxWidth(), onClick = {
                navController.navigate(Screen.List.route) {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }

            }) {
                Icon(
                    Icons.Filled.List,
                    contentDescription = null,
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )

                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(text = "${Screen.List.route.uppercase()}",modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp))
            }
            Button(modifier = Modifier.fillMaxWidth(),onClick = {
                navController.navigate(Screen.Login.route) {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }

            }) {
                Icon(
                    Icons.Filled.Login,
                    contentDescription = null,
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(text = "${Screen.Login.route.uppercase()}",modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp))
            }
            Button(modifier = Modifier.fillMaxWidth(),onClick = {
                navController.navigate(Screen.UI.route) {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }

            }) {
                Icon(
                    Icons.Filled.Cabin,
                    contentDescription = null,
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(text = "${Screen.UI.route.uppercase()}",modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp))
            }

        }
    }

}

@Composable
fun UI(navController: NavController) {

    var flag by remember { mutableStateOf(false) }

    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        // `infiniteRepeatable` repeats the specified duration-based `AnimationSpec` infinitely.
        animationSpec = infiniteRepeatable(
            // The `keyframes` animates the value by specifying multiple timestamps.
            animation = keyframes {
                // One iteration is 1000 milliseconds.
                durationMillis = 1000
                // 0.7f at the middle of an iteration.
                0.7f at 500
            },
            // When the value finishes animating from 0f to 1f, it repeats by reversing the
            // animation direction.
            repeatMode = RepeatMode.Reverse
        )
    )

    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    Box(Modifier.fillMaxSize()) {
        Column{
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .height(50.dp))
            Image(
                painterResource(id = R.drawable.picture03),
                contentDescription = "",
                contentScale = ContentScale.FillBounds, // or some other scale
                modifier = Modifier
                    .height(40.dp)
                    .alpha(alpha = if (flag) alpha else 1f)
            )
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .height(100.dp))
            Button(modifier= Modifier
                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                .fillMaxWidth()
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consumeAllChanges()
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                    }
                },onClick = {
                flag = !flag
            }) {
                Text(text = "Animate")
            }
        }
    }
}

@Composable
fun List(navController: NavController, mainViewModel : UserViewModel) {
    Column{
        UserList(userList = mainViewModel.userslistResponse)
        mainViewModel.getUsers()
    }
}

@Composable
fun Login(navController: NavController, mainViewModel : UserViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var openDialog = remember { mutableStateOf(false) }
    var loginMessage = remember { mutableStateOf(mainViewModel.loginMessage) }

    Box(Modifier.fillMaxSize()){
        Image(
            painterResource(id = R.drawable.picture03),
            contentDescription = "",
            contentScale = ContentScale.FillBounds, // or some other scale
            modifier = Modifier.matchParentSize()
        )
        Column(){

            Button(modifier = Modifier.fillMaxWidth(),onClick = {
                navController.navigate(Screen.Home.route) {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }

            }) {
                Text(text = "${Screen.Home.route}")
            }
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") }
            )
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") }
            )
            Button(onClick = {
                openDialog.value = true
                mainViewModel.login(email,password)
                Log.d("empty-comp"," >>>>>>> ${mainViewModel.loginMessage}")
                //loginMessage = mainViewModel.loginMessage
            }) {
                Text("login")
            }
            ShowAlertDialog(openDialog, "The response for the login button is :\n\n $loginMessage")

        }
    }

}


