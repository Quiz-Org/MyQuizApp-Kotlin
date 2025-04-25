package app.myquizapp.co.uk.presentation.composables

import android.util.Log
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.myquizapp.co.uk.domain.quiz.Quiz
import app.myquizapp.co.uk.presentation.viewModels.QuizListViewModel
import kotlinx.coroutines.flow.StateFlow

@Composable
fun QuizListScreen(
    quizzesFlow: StateFlow<List<Quiz>>,
    isLoadingFlow: StateFlow<Boolean>,
    onQuizClicked: (Int) -> Unit,
) {

    val quizzes = quizzesFlow.collectAsState()
    val isLoading = isLoadingFlow.collectAsState()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

        LoadingIndicator(isLoading)

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = innerPadding
        )
        {
            items(items = quizzes.value, key = { it.id }) { quiz ->
                ListItem(
                    modifier = Modifier.clickable(onClick = { onQuizClicked(quiz.id) }),
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


