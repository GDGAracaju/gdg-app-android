package gdg.aracaju.data.api

import com.google.firebase.database.FirebaseDatabase
import gdg.aracaju.data.api.DatabaseReferences.DETAIL
import gdg.aracaju.data.api.DatabaseReferences.EVENT
import gdg.aracaju.data.api.detail.DetailResponse
import gdg.aracaju.data.api.events.EventsResponse
import gdg.aracaju.data.api.extensions.readList
import gdg.aracaju.data.api.extensions.readValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withTimeout
import kotlin.coroutines.CoroutineContext

internal object ApiBuilder : ServerGateway, CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = IO

    private const val TIMEOUT = 10_000L

    private val instance by lazy { FirebaseDatabase.getInstance() }

    override suspend fun fetchEvents(): List<EventsResponse> {
        return withTimeout(TIMEOUT) {
            instance.getReference(EVENT).orderByKey().readList<EventsResponse>()
        }
    }

    override suspend fun fetchDetails(id: Int): DetailResponse {
        return instance.getReference(DETAIL).child(id.toString()).readValue()
    }
}