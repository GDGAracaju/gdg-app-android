package gdg.aracaju.domain.model

import java.lang.Exception

sealed class ScreenState {
    object Loading : ScreenState()
    data class Error(val e: Throwable) : ScreenState()
    data class Content<T>(val result: T) : ScreenState()
}