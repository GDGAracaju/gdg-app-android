package gdg.aracaju.view

import android.view.View
import gdg.aracaju.domain.model.ScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

suspend fun <T> getOrError(block: suspend CoroutineScope.() -> T): ScreenState {
    return try {
        GlobalScope.async { block() }.let {
            it.await().let { ScreenState.Content(it) }
        }
    } catch (e: Exception) {
        ScreenState.Error(e)
    }
}



