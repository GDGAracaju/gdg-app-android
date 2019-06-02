package gdg.aracaju.view.news

sealed class Action {

    object Logout : Action()
    object Contribute : Action()
}

sealed class CallToAction {
    object Logout : CallToAction()
    data class Contribute(val url: String) : CallToAction()
}