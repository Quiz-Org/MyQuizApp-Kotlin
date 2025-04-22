package app.myquizapp.co.uk.presentation

sealed class Screen(val route: String) {

    data object EntryScreen : Screen("entry_screen")
    data object QuizListScreen : Screen("quiz_list_screen")

}