package app.myquizapp.co.uk.domain.quiz

class Question( val id: Int,  val questionText: String, answers: List<Answer>, var answerGiven: Int = 1000) {
    val answers = answers.shuffled()
}