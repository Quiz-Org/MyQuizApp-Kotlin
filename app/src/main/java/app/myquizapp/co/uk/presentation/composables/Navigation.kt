package app.myquizapp.co.uk.presentation.composables

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.myquizapp.co.uk.presentation.QuizViewModel
import app.myquizapp.co.uk.presentation.Screen

@Composable
fun Navigation(){

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.EntryScreen.route){
        composable(route = Screen.EntryScreen.route){
            EntryScreen(navController = navController)
        }
        composable(route = Screen.QuizListScreen.route){
            val viewModel = hiltViewModel<QuizViewModel>()
            viewModel.loadQuizList()
            QuizListScreen(state = viewModel.state)
        }
    }

}