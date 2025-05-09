package app.myquizapp.co.uk.presentation.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ScoreScreen(score: Int,
                total: Int,
                replayQuiz: () -> Unit,
                newQuiz: () -> Unit,
                toEntry: () -> Unit
) {

    Column(modifier = Modifier.fillMaxWidth()) {


        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(4F),
        ) {
            Text(text = "Score", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.fillMaxWidth())
            Text(text = " $score / $total")
        }
        Button(onClick = {replayQuiz()}, modifier = Modifier.fillMaxWidth().weight(0.5F).padding(bottom = 10.dp)) { Text(text = "Try Again") }
        Button(onClick = {newQuiz()}, modifier = Modifier.fillMaxWidth().weight(0.5F).padding(bottom = 10.dp)) { Text(text = "New Quiz") }
        Button(onClick = {toEntry()}, modifier = Modifier.fillMaxWidth().weight(0.5F).padding(bottom = 10.dp)) { Text(text = "Back to Menu") }

    }


}
