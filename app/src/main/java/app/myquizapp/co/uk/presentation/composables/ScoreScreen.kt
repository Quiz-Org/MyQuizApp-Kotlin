package app.myquizapp.co.uk.presentation.composables

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ScoreScreen(scoreFlow : StateFlow<Int>){

    val score = scoreFlow.collectAsState()

    Text(text = score.value.toString())

}