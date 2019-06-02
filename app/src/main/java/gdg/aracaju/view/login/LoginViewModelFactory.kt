package gdg.aracaju.view.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

internal class LoginViewModelFactory(private val service: AuthControl) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(service) as T
    }
}