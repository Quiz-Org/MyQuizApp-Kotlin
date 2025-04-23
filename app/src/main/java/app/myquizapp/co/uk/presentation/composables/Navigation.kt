package app.myquizapp.co.uk.presentation.composables

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.myquizapp.co.uk.presentation.EntryNavigationEvent
import app.myquizapp.co.uk.presentation.EntryViewModel
import app.myquizapp.co.uk.presentation.LoadEvent
import app.myquizapp.co.uk.presentation.QuizListViewModel
import app.myquizapp.co.uk.presentation.Screen

@Composable
fun Navigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.EntryScreen.route) {
        composable(route = Screen.EntryScreen.route) {

            val viewModel = hiltViewModel<EntryViewModel>()
            val context = LocalContext.current

            val lifecycleOwner = LocalLifecycleOwner.current

            LaunchedEffect(lifecycleOwner.lifecycle) {
                lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.navigationEventsChannelFlow.collect { event ->
                        when (event) {
                            is EntryNavigationEvent.NavigateToQuizList -> {
                                navController.navigate(Screen.QuizListScreen.route)
                            }
                        }
                    }
                }
            }
            EntryScreen(onQuizzesClick = viewModel::showQuizzesClicked)
        }

        composable(route = Screen.QuizListScreen.route) {
            val viewModel = hiltViewModel<QuizListViewModel>()

            viewModel.loadQuizList()

            val context = LocalContext.current
            val lifecycleOwner = LocalLifecycleOwner.current

            LaunchedEffect(lifecycleOwner.lifecycle) {
                lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.quizLoadChannelFlow.collect { event ->
                        when (event) {
                            LoadEvent.QuizLoadError ->{
                                Toast.makeText(context, viewModel.error.value, Toast.LENGTH_SHORT).show()
                                navController.navigate(Screen.EntryScreen.route)
                            }
                        }
                    }
                }
            }

            QuizListScreen(viewModel.quizzes, viewModel.isLoading)

        }

    }
}