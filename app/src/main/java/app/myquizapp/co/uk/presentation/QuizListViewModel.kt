package app.myquizapp.co.uk.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.myquizapp.co.uk.domain.quiz.Quiz
import app.myquizapp.co.uk.domain.repository.QuizRepository
import app.myquizapp.co.uk.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizListViewModel @Inject constructor(
    private val repository: QuizRepository
): ViewModel() {


    private val _quizzes = MutableStateFlow<List<Quiz>>(listOf())
    val quizzes = _quizzes.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private val quizLoadChannel = Channel<LoadEvent>()
    val quizLoadChannelFlow = quizLoadChannel.receiveAsFlow()

    fun loadQuizList() {
        viewModelScope.launch {

            _isLoading.value = true
            _error.value = null

            when(val result = repository.getAllQuizzes()){
                is Resource.Success -> {
                    _quizzes.value = result.data ?: emptyList()
                    _isLoading.value = false
                }
                is Resource.Error -> {
                    _isLoading.value = false
                    _error.value = result.message
                }
            }

            if (_quizzes.value.isEmpty()) {
                if (_error.value != null) {
                    _error.value = "No Quizzes were found"
                }
                quizLoadChannel.send(LoadEvent.QuizLoadError)
            }
        }

    }
}

sealed interface LoadEvent {
    data object QuizLoadError: LoadEvent
}