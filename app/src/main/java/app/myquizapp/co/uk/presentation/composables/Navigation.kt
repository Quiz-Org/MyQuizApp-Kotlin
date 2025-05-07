package app.myquizapp.co.uk.presentation.composables

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import app.myquizapp.co.uk.presentation.viewModels.EntryNavigationEvent
import app.myquizapp.co.uk.presentation.viewModels.EntryViewModel
import app.myquizapp.co.uk.presentation.LoadEvent
import app.myquizapp.co.uk.presentation.viewModels.QuizListViewModel
import app.myquizapp.co.uk.presentation.Screen
import app.myquizapp.co.uk.presentation.viewModels.QuestionAnswerViewModel
import app.myquizapp.co.uk.presentation.viewModels.QuestionNavigationEvent
import app.myquizapp.co.uk.presentation.viewModels.QuizNavigationEvent

@Composable
fun Navigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.EntryScreen.route) {
        composable(route = Screen.EntryScreen.route) {

            val viewModel = hiltViewModel<EntryViewModel>()

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
            EntryScreen(onQuizzesClick = viewModel::showQuizzesClicked, viewModel::logIn, viewModel::logOut, viewModel.isLoggedInFlow, viewModel.userFlow)
        }

        composable(route = Screen.QuizListScreen.route) {

            val viewModel = hiltViewModel<QuizListViewModel>()

            val context = LocalContext.current
            val lifecycleOwner = LocalLifecycleOwner.current

            LaunchedEffect(lifecycleOwner.lifecycle) {
                lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.quizLoadChannelFlow.collect { event ->
                        when (event) {
                            LoadEvent.LoadError ->{
                                Toast.makeText(context, viewModel.error.value, Toast.LENGTH_SHORT).show()
                                navController.navigate(Screen.EntryScreen.route)
                            }
                        }
                    }
                }
            }

            LaunchedEffect(lifecycleOwner.lifecycle) {
                lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.navigationEventsChannelFlow.collect { event ->
                        when (event) {
                            is QuizNavigationEvent.NavigateToQuiz -> {
                                navController.navigate(Screen.QuestionAnswerScreen.withArgs(event.quizId.toString()))
                            }
                        }
                    }
                }
            }

            QuizListScreen(viewModel.quizzes, viewModel.isLoading, viewModel::showQuizClicked)

        }

        composable(
            route = Screen.QuestionAnswerScreen.route + "/{quizId}",
            arguments = listOf(
                navArgument("quizId") {
                    type = NavType.IntType
                    nullable = false
                }
            )) {

            val quizId: Int = it.arguments?.getInt("quizId") ?: 1000000
            val viewModel = hiltViewModel<QuestionAnswerViewModel, QuestionAnswerViewModel.QuestionAnswerViewModelFactory>() { factory -> factory.create(quizId)}

            val lifecycleOwner = LocalLifecycleOwner.current

            LaunchedEffect(lifecycleOwner.lifecycle) {
                lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.navigationEventsChannelFlow.collect { event ->
                        when (event) {
                            is QuestionNavigationEvent.NavigateToScore -> {
                                navController.navigate(Screen.ScoreScreen.route)
                            }
                            is QuestionNavigationEvent.NavigateToQuiz -> {
                                navController.navigate(Screen.QuestionAnswerScreen.withArgs(event.quizId.toString()))
                            }
                            is QuestionNavigationEvent.NavigateToQuizList -> {
                                navController.navigate(Screen.QuizListScreen.route)
                            }
                        }
                    }
                }
            }

            val context = LocalContext.current

            LaunchedEffect(lifecycleOwner.lifecycle) {
                lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.questionsLoadChannelFlow.collect { event ->
                        when (event) {
                            LoadEvent.LoadError ->{
                                Toast.makeText(context, viewModel.error.value, Toast.LENGTH_SHORT).show()
                                navController.navigate(Screen.QuizListScreen.route)
                            }
                        }
                    }
                }
            }

            QuestionAnswerScreen(viewModel.currentQuestion, viewModel.currentlySelected, viewModel.isLoading, viewModel::updateSelected,viewModel::nextQuestion)
        }

        composable(route = Screen.ScoreScreen.route){

            val previousBackStackEntry = remember {
                navController.previousBackStackEntry
            }

            val viewModel: QuestionAnswerViewModel = hiltViewModel(previousBackStackEntry!!)


            val lifecycleOwner = LocalLifecycleOwner.current

            LaunchedEffect(lifecycleOwner.lifecycle) {
                lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.navigationEventsChannelFlow.collect { event ->
                        when (event) {
                            is QuestionNavigationEvent.NavigateToScore -> {
                                navController.navigate(Screen.ScoreScreen.route)
                            }
                            is QuestionNavigationEvent.NavigateToQuiz -> {
                                navController.navigate(Screen.QuestionAnswerScreen.withArgs(event.quizId.toString()))
                            }
                            is QuestionNavigationEvent.NavigateToQuizList -> {
                                navController.navigate(Screen.QuizListScreen.route)
                            }
                        }
                    }
                }
            }

            ScoreScreen(viewModel.getScore(),viewModel.getTotal(), viewModel::replayQuiz, viewModel::newQuiz)

        }

    }
}
