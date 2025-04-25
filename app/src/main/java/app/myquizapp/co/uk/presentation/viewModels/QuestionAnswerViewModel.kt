package app.myquizapp.co.uk.presentation.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.myquizapp.co.uk.domain.quiz.Answer
import app.myquizapp.co.uk.domain.quiz.Question
import app.myquizapp.co.uk.domain.repository.QuizRepository
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
    private val repository: QuizRepository,
    @Assisted quizId: Int
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

    private val _currentlySelected = MutableStateFlow(0)
    val currentlySelected = _currentlySelected.asStateFlow()

    private val questionsLoadChannel = Channel<LoadEvent>()
    val questionsLoadChannelFlow = questionsLoadChannel.receiveAsFlow()


    init {
        if (questions.isEmpty() && !isLoading.value)
            questions = loadQuestions(quizId)
    }

    private fun loadQuestions(quizId: Int): List<Question> {
        viewModelScope.launch {

            Log.d("load", "Load")

            _isLoading.value = true
            _error.value = null

            when(val result = repository.getQuestions(quizId)){
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
                    _error.value = "No Quizzes were found"
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
        questions[_currentQuestion.value.id - 1].answerGiven = _currentlySelected.value
        if(questions.indexOf(_currentQuestion.value) < questions.size){
            _currentQuestion.value = questions[questions.indexOf(_currentQuestion.value) + 1]
        }
    }

    @AssistedFactory
    interface QuestionAnswerViewModelFactory {
        fun create(quizId: Int): QuestionAnswerViewModel
    }

}