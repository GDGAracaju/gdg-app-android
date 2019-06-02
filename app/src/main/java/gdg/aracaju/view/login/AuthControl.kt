package gdg.aracaju.view.login

import android.content.Intent
import gdg.aracaju.domain.model.LoginState
import gdg.aracaju.domain.model.LogoutState

internal interface AuthControl {

    fun login()

    fun loginStatus(): LoginState

    suspend fun loginResult(data: Intent?): LoginState

    fun logout(): LogoutState
}