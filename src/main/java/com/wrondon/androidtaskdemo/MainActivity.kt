package com.wrondon.androidtaskdemo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.wrondon.androidtaskdemo.data.User
import com.wrondon.androidtaskdemo.ui.theme.AndroidTaskDemoTheme
import com.wrondon.androidtaskdemo.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AndroidTaskDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    //Greeting("Android",viewModel.userslistResponse, viewModel.loginMessage)
                    InitialNavigation(viewModel)
                    //viewModel.getUsers()
                    //viewModel.login("eve.holt@reqres.in","cityslicka")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, userList: List<User>, loginMess : String) {
    val openDialog = remember { mutableStateOf(false) }

    val l1 = mutableListOf<String>("uno","dos","tres")
    Column{
        Text(text = "Hello $name!")
        Button(onClick = {
            Log.d("empty=compose"," ...")
            openDialog.value = true
         }) {
        }
        ShowAlertDialog(openDialog, "The response for the login button is :\n\n $loginMess")
        UserList2(userList)
        Divider()
        LazyColumn{
            items(l1){
                Text(" > $it")
            }
        }
    }
}
@Composable
fun UserList2(users: List<User>) {
    LazyColumn {
        items(users) { user ->
            Text(" >>> ${user.email} <<<")
        }
    }
}
@Composable
fun ShowAlertDialog(openDialog: MutableState<Boolean>, texto: String) {
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text("Login")
            },
            text = {
                Text(texto)
            },
            buttons = {
                Row(
                    modifier = Modifier.padding(all = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { openDialog.value = false }
                    ) {
                        Text("OK. Dismiss")
                    }
                }
            }
        )
    }
}
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AndroidTaskDemoTheme {
        Greeting("Android", listOf(User(1,"qwe@qwe.com","qwe","qwe","qwe.com")),"qazqaz")
    }
}