package app.myquizapp.co.uk.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import app.myquizapp.co.uk.presentation.composables.Navigation
import app.myquizapp.co.uk.presentation.ui.theme.MyQuizAppKotlinTheme
import com.auth0.android.Auth0
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var account: Auth0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.applicationContext

        setContent {
            MyQuizAppKotlinTheme {
                Navigation()
                }
            }
        }
    }

