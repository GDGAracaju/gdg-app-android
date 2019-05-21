package gdg.aracaju.data.api

import com.google.firebase.database.FirebaseDatabase
import gdg.aracaju.data.api.DatabaseReferences.EVENT
import gdg.aracaju.data.api.events.EventsResponse
import gdg.aracaju.data.api.extensions.readList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext

internal object ApiBuilder : ServerGateway, CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = IO

    override suspend fun fetchEvents(): Deferred<List<EventsResponse>> = coroutineScope {
        async { FirebaseDatabase.getInstance().getReference(EVENT).readList<EventsResponse>() }
    }
}