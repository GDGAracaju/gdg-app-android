package gdg.aracaju.view.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar.LENGTH_SHORT
import com.google.android.material.snackbar.Snackbar.make
import gdg.aracaju.domain.model.LoginState
import gdg.aracaju.domain.model.ScreenState
import gdg.aracaju.news.R
import gdg.aracaju.view.login.AuthManager.Companion.RC_SIGN_IN
import gdg.aracaju.view.news.DashboardActivity
import gdg.aracaju.view.selfInject
import kotlinx.android.synthetic.main.activity_login.*
import gdg.aracaju.view.viewModel
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

class LoginActivity : AppCompatActivity(), KodeinAware {

    override val kodein: Kodein = selfInject()

    private val viewModel by viewModel<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        listenToLogin()
        signInButton.setOnClickListener { viewModel.startLogin() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            viewModel.loginResult(data)
        }
    }

    private fun listenToLogin() {
        viewModel.listenLoginResult().observe(this, Observer {
            when (it) {
                is ScreenState.Loading -> loading()
                is ScreenState.Content -> it.result.verify()
                is ScreenState.Error -> error()
            }
        })
    }

    private fun LoginState.verify() {
        when (this) {
            LoginState.Authenticated -> {
                startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
                finish()
            }
            LoginState.NotAuthenticated -> {
                loading.isVisible = false
                signInButton.isClickable = true
            }
        }
    }

    private fun loading() {
        loading.isVisible = true
        signInButton.isClickable = false
    }

    private fun error() {
        make(container, getString(R.string.login_failed), LENGTH_SHORT).show()
        signInButton.isClickable = true
    }
}