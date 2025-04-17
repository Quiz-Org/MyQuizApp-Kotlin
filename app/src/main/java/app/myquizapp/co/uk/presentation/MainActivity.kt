package app.myquizapp.co.uk.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import app.myquizapp.co.uk.presentation.ui.theme.MyQuizAppKotlinTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: QuizViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadQuizList()
        setContent {
            MyQuizAppKotlinTheme {
                QuizListEntry(viewModel.state)
                }
            }
        }
    }

