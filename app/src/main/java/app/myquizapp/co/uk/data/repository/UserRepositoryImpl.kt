package app.myquizapp.co.uk.data.repository

import android.content.Context
import app.myquizapp.co.uk.domain.repository.UserRepository
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.UserProfile
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(@ApplicationContext private val context: Context): UserRepository {

    private val account = Auth0.getInstance("NdsyAsvZxpr8QxeMkAkorN81W2dlCr31","auth.myquizapp.co.uk")

    override val isLoggedInFlow = MutableStateFlow(false)
    override val userFlow = MutableStateFlow<UserProfile?>(null)

    override suspend fun logIn(context: Context){
        withContext(Dispatchers.IO){
            try {
                val credentials = WebAuthProvider.login(account).withScheme("app")
                    .await(context)
                userFlow.value = credentials.user
                isLoggedInFlow.value = true
            } catch (e: AuthenticationException) {
                e.printStackTrace()
            }
        }
    }

    override suspend fun logOut(context: Context){
        withContext(Dispatchers.IO){
            WebAuthProvider.logout(account)
                .withScheme("app")
                .await(context)
            userFlow.value = null
        }
        isLoggedInFlow.value = false
    }
}