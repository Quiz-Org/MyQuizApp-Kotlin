package app.myquizapp.co.uk.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.myquizapp.co.uk.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EntryViewModel @Inject constructor(): ViewModel() {

    private val navigationChannel = Channel<EntryNavigationEvent>()
    val navigationEventsChannelFlow = navigationChannel.receiveAsFlow()

    fun showQuizzesClicked(){
        viewModelScope.launch {
            navigationChannel.send(EntryNavigationEvent.NavigateToQuizList)
        }
    }
}


sealed interface EntryNavigationEvent {
    data object NavigateToQuizList: EntryNavigationEvent
}