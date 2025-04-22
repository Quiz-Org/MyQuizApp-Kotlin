package app.myquizapp.co.uk.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.myquizapp.co.uk.domain.repository.QuizRepository
import app.myquizapp.co.uk.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val repository: QuizRepository
): ViewModel() {

    var state by mutableStateOf(QuizState())
        private set

    fun loadQuizList() {
        viewModelScope.launch { state = state.copy(
                isLoading = true,
                error = null
            )
            when(val result = repository.getAllQuizzes()){
                is Resource.Success -> {
                    state = state.copy(
                        quizzes = result.data,
                        isLoading = false,
                        error = null
                    )
                }
                is Resource.Error -> {
                    state = state.copy(
                        quizzes = null,
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }

    }
}