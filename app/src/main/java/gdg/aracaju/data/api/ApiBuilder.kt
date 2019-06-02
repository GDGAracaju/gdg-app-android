package gdg.aracaju.data.api

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase
import gdg.aracaju.data.api.DatabaseReferences.DETAIL
import gdg.aracaju.data.api.DatabaseReferences.EVENT
import gdg.aracaju.data.api.detail.DetailResponse
import gdg.aracaju.data.api.events.EventsResponse
import gdg.aracaju.data.api.extensions.readList
import gdg.aracaju.data.api.extensions.readValue
import gdg.aracaju.data.api.extensions.signIn
import gdg.aracaju.domain.model.LoginState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withTimeout
import kotlin.coroutines.CoroutineContext

internal object ApiBuilder : ServerGateway, CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = IO

    private const val TIMEOUT = 10_000L

    private val instance by lazy { FirebaseDatabase.getInstance() }
    private val auth by lazy { FirebaseAuth.getInstance() }

    override suspend fun fetchEvents(): List<EventsResponse> {
        return withTimeout(TIMEOUT) {
            instance.getReference(EVENT).orderByKey().readList<EventsResponse>()
        }
    }

    override suspend fun fetchDetails(id: Int): DetailResponse {
        return withTimeout(TIMEOUT) {
            instance.getReference(DETAIL).child(id.toString()).readValue<DetailResponse>()
        }
    }

    override suspend fun authenticate(token: String?): LoginState {
        val credential = GoogleAuthProvider.getCredential(token, null)
        return auth.signIn(credential)
    }

    override fun hasUser(): Boolean = auth.currentUser != null

}