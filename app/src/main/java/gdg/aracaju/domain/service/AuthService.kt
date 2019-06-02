package gdg.aracaju.domain.service

import gdg.aracaju.domain.model.LoginState

interface AuthService {

    fun loginStatus(): LoginState

    suspend fun authenticate(token: String?): LoginState

    fun logout()
}