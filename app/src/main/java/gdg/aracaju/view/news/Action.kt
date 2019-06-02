package gdg.aracaju.view.news

sealed class Action {

    object Logout : Action()
    object Contribute : Action()
    object Site : Action()
}

sealed class CallToAction {
    object Logout : CallToAction()
    data class Contribute(val url: String) : CallToAction()
    data class Site(val url: String) : CallToAction()
}