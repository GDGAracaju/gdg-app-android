package gdg.aracaju.view.login

import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import gdg.aracaju.domain.model.LoginState
import gdg.aracaju.domain.service.AuthService
import gdg.aracaju.news.R

internal class SignInManager(private val login: LoginActivity, private val service: AuthService) : SignInControl {

    private val gso by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(login.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }

    override fun start() {
        GoogleSignIn.getClient(login, gso).apply {
            login.startActivityForResult(this.signInIntent, RC_SIGN_IN)
        }
    }

    override fun loginStatus(): LoginState = service.loginStatus()

    override suspend fun loginResult(data: Intent?): LoginState {
        return try {
            val account = GoogleSignIn.getSignedInAccountFromIntent(data).getResult(ApiException::class.java)
            service.authenticate(account?.idToken)
        } catch (e: Exception) {
            LoginState.NotAuthenticated
        }
    }

    companion object {
        const val RC_SIGN_IN = 9001
    }
}