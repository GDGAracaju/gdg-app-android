package gdg.aracaju.data.api

import com.google.firebase.database.FirebaseDatabase
import gdg.aracaju.data.api.DatabaseReferences.EVENT
import gdg.aracaju.data.api.events.EventsResponse
import gdg.aracaju.data.api.extensions.readList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlin.coroutines.CoroutineContext

internal object ApiBuilder : ServerGateway, CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = IO

    override suspend fun fetchEvents(): List<EventsResponse> {
        return FirebaseDatabase.getInstance().getReference(EVENT).orderByKey().readList()
    }
}