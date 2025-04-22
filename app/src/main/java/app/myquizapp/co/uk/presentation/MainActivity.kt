package app.myquizapp.co.uk.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import app.myquizapp.co.uk.presentation.composables.Navigation
import app.myquizapp.co.uk.presentation.ui.theme.MyQuizAppKotlinTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyQuizAppKotlinTheme {
                Navigation()
                }
            }
        }
    }

