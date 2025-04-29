package app.myquizapp.co.uk.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.myquizapp.co.uk.domain.quiz.Question
import kotlinx.coroutines.flow.StateFlow

@Composable
fun QuestionAnswerScreen(

    currentQuestionFlow: StateFlow<Question>,
    currentlySelectedFlow: StateFlow<Int>,
    isLoadingFlow: StateFlow<Boolean>,
    selectOption: (Int) -> Unit = {},
    nextQuestion: () -> Unit = {}
) {

    val currentQuestion = currentQuestionFlow.collectAsState()
    val currentlySelected = currentlySelectedFlow.collectAsState()
    val isLoading = isLoadingFlow.collectAsState()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

        if (isLoading.value) {
            LoadingIndicator(isLoading)
        } else if (!isLoading.value) {

            Column(
                modifier = Modifier.fillMaxWidth(),
            )
            {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(3F),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 50.dp
                    )
                ) {
                    Text(
                        text = currentQuestion.value.questionText,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineLarge,
                    )
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(2F)
                        .padding(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                    items(
                        items = currentQuestion.value.answers, key = { it.id },
                    ) { answer ->
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                            elevation = CardDefaults.cardElevation(defaultElevation = 50.dp),
                            modifier = Modifier.padding(10.dp).fillMaxWidth(),
                        ) {
                            Row(
                                modifier = Modifier.selectable(
                                    selected = false,
                                    onClick = { selectOption(answer.id) },
                                    role = Role.RadioButton
                                )
                                    .padding(10.dp)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            ) {
                                RadioButton(
                                    selected = (answer.id == currentlySelected.value),
                                    onClick = null,
                                    modifier = Modifier
                                )
                                Text(
                                    text = answer.answerText,
                                    modifier = Modifier.padding(start = 10.dp)
                                )
                            }

                        }

                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1F)
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.Center
                )
                {
                    Button(onClick = nextQuestion) {
                        Text(text = "Next Question")
                    }
                }

            }
        }

    }
}