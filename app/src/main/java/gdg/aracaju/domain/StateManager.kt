package gdg.aracaju.domain

sealed class ScreenState{
    object Loaging: ScreenState()
    data class Error(val message: String): ScreenState()
    object Empty: ScreenState()
}

interface MyView{
    fun showList()
    fun showEmptyState()
    fun showErrorState(message:String)
    fun showLoadingView()
    fun getState(state: ScreenState)

}