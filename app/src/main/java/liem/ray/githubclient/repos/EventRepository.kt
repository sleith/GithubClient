package liem.ray.githubclient.repos

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import liem.ray.githubclient.api.interactors.EventApiInteractor
import liem.ray.githubclient.data.toData

class EventRepository(
    private val eventApiInteractor: EventApiInteractor,
) {
    suspend fun getEventList(username: String, page: Int = 1, pageSize: Int) = withContext(Dispatchers.IO) {
        Result.runCatching {
            val events = eventApiInteractor.getEventList(username = username, page = page, pageSize = pageSize)
            events.map { it.toData() }
        }
    }
}