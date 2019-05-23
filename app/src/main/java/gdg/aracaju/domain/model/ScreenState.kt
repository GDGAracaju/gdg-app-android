package gdg.aracaju.domain.model

sealed class ScreenState<out T> {
    object Loading : ScreenState<Nothing>()
    data class Error(val e: Throwable) : ScreenState<Nothing>()
    data class Content<out T>(val result: T) : ScreenState<T>()
}