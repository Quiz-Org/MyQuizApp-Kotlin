package app.myquizapp.co.uk.presentation.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.myquizapp.co.uk.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EntryViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {

    private val navigationChannel = Channel<EntryNavigationEvent>()
    val navigationEventsChannelFlow = navigationChannel.receiveAsFlow()
    val isLoggedInFlow = userRepository.isLoggedInFlow
    val userFlow = userRepository.userFlow

    fun showQuizzesClicked(){
        viewModelScope.launch {
            navigationChannel.send(EntryNavigationEvent.NavigateToQuizList)
        }
    }

    fun logIn(context: Context){
        viewModelScope.launch { userRepository.logIn(context) }
    }
    fun logOut(context: Context){
        viewModelScope.launch { userRepository.logOut(context) }
    }


}

sealed interface EntryNavigationEvent {
    data object NavigateToQuizList: EntryNavigationEvent
}