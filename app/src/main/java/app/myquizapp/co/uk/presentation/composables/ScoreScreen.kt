package app.myquizapp.co.uk.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ScoreScreen(scoreFlow : StateFlow<Int>) {

    val score = scoreFlow.collectAsState()

    Row(modifier = Modifier.fillMaxSize().fillMaxWidth(),
        horizontalArrangement = Arrangement.Center) {
        Text(text = score.value.toString(), modifier = Modifier.fillMaxWidth().fillMaxSize())
    }

}
