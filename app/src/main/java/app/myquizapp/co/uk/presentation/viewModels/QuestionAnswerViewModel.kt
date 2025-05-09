package app.myquizapp.co.uk.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.myquizapp.co.uk.domain.quiz.Answer
import app.myquizapp.co.uk.domain.quiz.Question
import app.myquizapp.co.uk.domain.repository.QuizRepository
import app.myquizapp.co.uk.domain.repository.ScoreRepository
import app.myquizapp.co.uk.domain.repository.UserRepository
import app.myquizapp.co.uk.domain.util.Resource
import app.myquizapp.co.uk.presentation.LoadEvent
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = QuestionAnswerViewModel.QuestionAnswerViewModelFactory::class)
class QuestionAnswerViewModel @AssistedInject
constructor(
    private val quizRepository: QuizRepository,
    private val userRepository: UserRepository,
    private val scoreRepository: ScoreRepository,
    @Assisted private val quizId: Int
) : ViewModel() {

    private var questions: List<Question> = listOf()

    private val _currentQuestion = MutableStateFlow(
        Question(
            id = 10101010,
            questionText = "",
            answers = listOf(Answer(
                id = 0,
                answerText = "",
                correct = false
            ))
        )
    )
    val currentQuestion = _currentQuestion.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private val _currentlySelected = MutableStateFlow(0)
    val currentlySelected = _currentlySelected.asStateFlow()

    private val questionsLoadChannel = Channel<LoadEvent>()
    val questionsLoadChannelFlow = questionsLoadChannel.receiveAsFlow()

    private val navigationChannel = Channel<QuestionNavigationEvent>()
    val navigationEventsChannelFlow = navigationChannel.receiveAsFlow()

    private val isLoggedInFlow = userRepository.isLoggedInFlow

    private var score = 0

    init {
        if (questions.isEmpty() && !isLoading.value)
            questions = loadQuestions(quizId)
    }

    private fun loadQuestions(quizId: Int): List<Question> {
        viewModelScope.launch {

            _isLoading.value = true
            _error.value = null

            when(val result = quizRepository.getQuestions(quizId)){
                is Resource.Success -> {
                    questions = result.data ?: emptyList()
                    _isLoading.value = false
                }
                is Resource.Error -> {
                    _isLoading.value = false
                    _error.value = result.message
                }
            }

            if (questions.isEmpty()) {
                if (_error.value != null) {
                    _error.value = "Quiz Data Not Found"
                }
                questionsLoadChannel.send(LoadEvent.LoadError)
            }
            if(questions.isNotEmpty()){
                questions = questions.shuffled()
                _currentQuestion.value = questions.first()
            }
        }
        return questions
    }

    fun updateSelected(key: Int){
        _currentlySelected.value = key
    }

    fun nextQuestion(){
       _currentQuestion.value.answerGiven = currentlySelected.value
        if(questions.indexOf(_currentQuestion.value) < questions.size - 1){
            _currentQuestion.value = questions[questions.indexOf(_currentQuestion.value) + 1]
        } else {
            score = calculateScore()
            if(isLoggedInFlow.value) submitScore()
            viewModelScope.launch {
                navigationChannel.send(QuestionNavigationEvent.NavigateToScore)
            }
        }
    }

    private fun calculateScore(): Int{
        var score = 0
        for(question in questions){
            val correctAnswer = question.answers.find { it.correct }
            if (correctAnswer != null) {
                if (correctAnswer.id == question.answerGiven){score++}
            }
        }

        return score
    }

    private fun submitScore(){
        viewModelScope.launch {
            scoreRepository.postScore(
                userRepository.tokenFlow.value ?: "",
                quizId,
                score,
                questions.size
            )
        }
    }

    fun newQuiz(){
        viewModelScope.launch {
            navigationChannel.send(QuestionNavigationEvent.NavigateToQuizList)
        }
    }

    fun replayQuiz(){
        viewModelScope.launch {
            navigationChannel.send(QuestionNavigationEvent.NavigateToQuiz(quizId))
        }
    }

    fun toEntry(){
        viewModelScope.launch {
            navigationChannel.send(QuestionNavigationEvent.NavigateToEntry)
        }
    }

    fun getScore(): Int{ return score }
    fun getTotal(): Int{ return questions.size }

    @AssistedFactory
    interface QuestionAnswerViewModelFactory {
        fun create(quizId: Int): QuestionAnswerViewModel
    }

}

sealed interface QuestionNavigationEvent {
    data object NavigateToScore : QuestionNavigationEvent
    data class NavigateToQuiz(val quizId: Int): QuestionNavigationEvent
    data object NavigateToQuizList: QuestionNavigationEvent
    data object NavigateToEntry: QuestionNavigationEvent
}