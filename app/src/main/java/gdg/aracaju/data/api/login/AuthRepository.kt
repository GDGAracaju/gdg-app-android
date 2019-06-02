package gdg.aracaju.data.api.login

import gdg.aracaju.data.api.ApiBuilder
import gdg.aracaju.data.api.ServerGateway
import gdg.aracaju.domain.model.LoginState
import gdg.aracaju.domain.service.AuthService

internal class AuthRepository(private val api: ServerGateway = ApiBuilder) : AuthService {

    override fun loginStatus(): LoginState {
        return when (api.hasUser()) {
            true -> LoginState.Authenticated
            false -> LoginState.NotAuthenticated
        }
    }

    override suspend fun authenticate(token: String?): LoginState = api.authenticate(token)
}