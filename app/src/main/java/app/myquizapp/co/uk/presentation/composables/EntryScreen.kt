package app.myquizapp.co.uk.presentation.composables

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.myquizapp.co.uk.presentation.Screen

@Composable
fun EntryScreen( onQuizzesClick: () -> Unit, logIn: (context: Context) -> Unit){

    val context = LocalContext.current

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp)
    ) {
        Button(
        onClick = {onQuizzesClick()},
        modifier = Modifier.fillMaxWidth()
    ){
        Text(text = "Show All Quizzes")
    }

        Button(onClick = {logIn(context)}, modifier = Modifier.fillMaxWidth()){
            Text(text = "Log In")
        }

    }

}