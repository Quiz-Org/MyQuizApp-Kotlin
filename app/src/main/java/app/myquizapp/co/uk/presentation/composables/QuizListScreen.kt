package app.myquizapp.co.uk.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.myquizapp.co.uk.domain.quiz.Quiz
import kotlinx.coroutines.flow.StateFlow

@Composable
fun QuizListScreen(
    quizzesFlow: StateFlow<List<Quiz>>,
    isLoadingFlow: StateFlow<Boolean>,
) {

    val quizzes = quizzesFlow.collectAsState()
    val isLoading = isLoadingFlow.collectAsState()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentAlignment = Alignment.Center,
        ) {
            IndeterminateCircularIndicator(isLoading)
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = innerPadding
        )
        {
            items(items = quizzes.value) { quiz ->
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

@Composable
fun IndeterminateCircularIndicator(isLoading: State<Boolean>) {
    if (!isLoading.value) return
    CircularProgressIndicator(
        modifier = Modifier.width(64.dp),
        color = MaterialTheme.colorScheme.secondary,
        trackColor = MaterialTheme.colorScheme.surfaceVariant,
    )
}