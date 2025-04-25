package app.myquizapp.co.uk.presentation

sealed class Screen(val route: String) {

    data object EntryScreen : Screen("entry_screen")
    data object QuizListScreen : Screen("quiz_list_screen")
    data object QuestionAnswerScreen: Screen("question_answer_screen")
    data object ScoreScreen: Screen("score_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
    }}

}