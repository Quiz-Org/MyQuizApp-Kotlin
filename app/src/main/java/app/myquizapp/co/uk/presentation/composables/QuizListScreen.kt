package app.myquizapp.co.uk.presentation.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.myquizapp.co.uk.presentation.QuizState

@Composable
fun QuizListScreen(
    state: QuizState,
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = innerPadding
        )
        {
            items(items = state.quizzes.orEmpty()) { quiz ->
                ListItem(
                    headlineContent = { Text(quiz.name) },
                    supportingContent = { Text(quiz.desc) },
                    overlineContent = { Text(quiz.category.toString()) },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = quiz.colour
                        )
                    }
                )
            }

        }
    }
}
