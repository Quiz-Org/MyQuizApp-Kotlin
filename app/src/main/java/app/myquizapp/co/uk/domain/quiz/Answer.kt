package app.myquizapp.co.uk.domain.quiz

class Answer(val id: Int, val answerText: String, val correct: Boolean, var selected: Boolean = false) {
}