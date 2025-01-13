package liem.ray.githubclient.api.interactors

import liem.ray.githubclient.api.EventApi
import liem.ray.githubclient.data.apiModel.EventApiModel


class EventApiInteractor(private val eventApi: EventApi) {
    suspend fun getEventList(username: String, page: Int = 1, pageSize: Int): List<EventApiModel> {
        return eventApi.getEvents(username = username, page = page, pageSize = pageSize)
    }
}