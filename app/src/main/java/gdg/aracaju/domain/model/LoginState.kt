package gdg.aracaju.domain.model

sealed class LoginState {

    object Authenticated : LoginState()
    object NotAuthenticated : LoginState()
}

sealed class LogoutState {

    object Success : LogoutState()
    object Failed : LogoutState()
}