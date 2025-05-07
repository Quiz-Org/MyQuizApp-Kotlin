package app.myquizapp.co.uk.presentation.viewModels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EntryViewModel @Inject constructor(private val application: Application): ViewModel() {


    private val account = Auth0.getInstance( application.applicationContext)


    private val navigationChannel = Channel<EntryNavigationEvent>()
    val navigationEventsChannelFlow = navigationChannel.receiveAsFlow()

    fun showQuizzesClicked(){
        viewModelScope.launch {
            navigationChannel.send(EntryNavigationEvent.NavigateToQuizList)
        }
    }

    fun logIn(context: Context){
        viewModelScope.launch { logInInternal(context) }
    }


    private suspend fun logInInternal(context: Context){
       withContext(Dispatchers.IO){


           //
            try {
                val credentials = WebAuthProvider.login(account).withScheme("app")
                    .await(context)
                Log.d("TAG", "logInInternal: ${credentials.accessToken}")
            } catch (e: AuthenticationException) {
                e.printStackTrace()
            }
        }
    }

}

sealed interface EntryNavigationEvent {
    data object NavigateToQuizList: EntryNavigationEvent
}