package gdg.aracaju.data.api.extensions

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import gdg.aracaju.domain.model.LoginState
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

internal suspend inline fun FirebaseAuth.signIn(authCredential: AuthCredential): LoginState =
    suspendCancellableCoroutine { continuation ->

        signInWithCredential(authCredential)
            .addOnCompleteListener {
                when (it.isSuccessful) {
                    true -> LoginState.Authenticated
                    false -> LoginState.NotAuthenticated
                }.also {
                    continuation.resume(it)
                }
            }.addOnCanceledListener {
                continuation.resume(LoginState.NotAuthenticated)
            }.addOnFailureListener {
                continuation.resumeWithException(it)
            }
    }