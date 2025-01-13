package liem.ray.githubclient.api.interactors

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import liem.ray.githubclient.api.EventApi
import liem.ray.githubclient.data.toData


class EventApiInteractor(private val eventApi: EventApi) {
    suspend fun getEventList(username: String, page: Int = 1) = withContext(Dispatchers.IO) {
        Result.runCatching {
            val events = eventApi.getEvents(username, page)
            events.map { it.toData() }
        }
    }
}