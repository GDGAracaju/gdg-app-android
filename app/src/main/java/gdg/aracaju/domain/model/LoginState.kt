package gdg.aracaju.domain.model

sealed class LoginState {

    object Authenticated : LoginState()
    object NotAuthenticated : LoginState()
}