package app.myquizapp.co.uk.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ScoreScreen(scoreFlow : StateFlow<Int>, replayQuiz: () -> Unit, newQuiz: () -> Unit) {

    val score = scoreFlow.collectAsState()
    Column(modifier = Modifier.fillMaxWidth()) {

        Row(
            modifier = Modifier.fillMaxSize()
                .fillMaxWidth()
                .weight(4F),
            horizontalArrangement = Arrangement.Center) {
            Text(text = score.value.toString(), modifier = Modifier.fillMaxWidth().fillMaxSize())
        }
        Button(onClick = {replayQuiz()}, modifier = Modifier.fillMaxWidth()) { Text(text = "Try Again") }
        Button(onClick = {newQuiz()}, modifier = Modifier.fillMaxWidth()) { Text(text = "New Quiz") }

    }


}
