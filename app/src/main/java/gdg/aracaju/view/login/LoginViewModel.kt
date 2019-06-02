package gdg.aracaju.view.login

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import gdg.aracaju.domain.model.LoginState
import gdg.aracaju.domain.model.ScreenState
import gdg.aracaju.view.add
import gdg.aracaju.view.getOrError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


internal class LoginViewModel(private val manager: AuthControl) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private val loginResult: MutableLiveData<ScreenState<LoginState>> = MutableLiveData()

    fun listenLoginResult(): LiveData<ScreenState<LoginState>> = loginResult

    private val jobs = ArrayList<Job>()

    init {
        loginResult.value = ScreenState.Loading
        loginResult.value = ScreenState.Content(manager.loginStatus())
    }

    fun startLogin() {
        manager.login()
    }

    fun loginResult(data: Intent?) {

        jobs add launch {
            loginResult.value = ScreenState.Loading
            loginResult.value = getOrError { manager.loginResult(data) }
        }
    }

    override fun onCleared() {
        super.onCleared()
        jobs.forEach { it.cancel() }
    }
}