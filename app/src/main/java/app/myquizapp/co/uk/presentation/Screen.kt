package app.myquizapp.co.uk.presentation

sealed class Screen(val route: String) {

    object EntryScreen : Screen("entry_screen")
    object QuizListScreen : Screen("quiz_list_screen")

}