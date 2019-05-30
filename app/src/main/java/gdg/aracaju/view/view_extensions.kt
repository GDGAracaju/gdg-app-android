package gdg.aracaju.view

import gdg.aracaju.domain.model.ScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async

suspend fun <T> getOrError(block: suspend CoroutineScope.() -> T): ScreenState<T> {
    return try {
        GlobalScope.async { block() }.let {
            it.await().let { ScreenState.Content(it) }
        }
    } catch (e: Exception) {
        ScreenState.Error(e)
    }
}

suspend fun <T> getOrErrorBy(
    block: suspend CoroutineScope.() -> T,
    onSuccess: (T) -> Unit,
    onError: (Throwable) -> Unit
) {
    try {
        GlobalScope.async { block() }.let {
            onSuccess(it.await())
        }
    } catch (e: Exception) {
        onError(e)
    }
}

infix fun ArrayList<Job>.add(job: Job) {
    this.add(job)
}