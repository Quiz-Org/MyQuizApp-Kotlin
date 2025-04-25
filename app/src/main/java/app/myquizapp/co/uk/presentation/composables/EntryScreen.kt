package app.myquizapp.co.uk.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.myquizapp.co.uk.presentation.Screen

@Composable
fun EntryScreen( onQuizzesClick: () -> Unit){

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
    } }

}