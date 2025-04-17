package app.myquizapp.co.uk.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.myquizapp.co.uk.domain.quiz.Quiz

@Composable
fun QuizRow(
    modifier: Modifier,
    quiz: Quiz
) {
    Card(modifier = Modifier.padding(8.dp)) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(quiz.name)
            Text(quiz.desc)
        }
    }
}


@Composable
fun QuizListEntry(
    state: QuizState,
    modifier: Modifier = Modifier
) {
    state.quizzes?.let { data ->
        LazyColumn {
            items(data) { quiz ->
                QuizRow(modifier.fillParentMaxWidth(), quiz)
            }
         }
    }

}