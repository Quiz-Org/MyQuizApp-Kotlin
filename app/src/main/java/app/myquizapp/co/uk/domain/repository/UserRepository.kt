package app.myquizapp.co.uk.domain.repository

import android.content.Context
import com.auth0.android.result.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow

interface UserRepository {

    val isLoggedInFlow: MutableStateFlow<Boolean>
    val userFlow: MutableStateFlow<UserProfile?>
    val tokenFlow: MutableStateFlow<String?>

    suspend fun logIn(context: Context)
    suspend fun logOut(context: Context)

}