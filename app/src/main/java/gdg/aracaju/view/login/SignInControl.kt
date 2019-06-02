package gdg.aracaju.view.login

import android.content.Intent
import gdg.aracaju.domain.model.LoginState

internal interface SignInControl {

    fun start()

    suspend fun loginResult(data: Intent?): LoginState

    fun loginStatus(): LoginState
}