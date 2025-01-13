package liem.ray.githubclient.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import liem.ray.githubclient.common.item.DialogItem
import liem.ray.githubclient.data.EventData
import liem.ray.githubclient.data.UserDetailData
import liem.ray.githubclient.navigation.NavigatorService
import liem.ray.githubclient.repos.EventRepository
import liem.ray.githubclient.repos.UserRepository

class UserDetailViewModel(
    private val username: String,
    private val userRepository: UserRepository,
    private val eventRepository: EventRepository,
    private val navigator: NavigatorService,
) : ViewModel(), UserDetailViewModelActionHandler {

    private val _state = MutableStateFlow(State(title = username))
    val state: StateFlow<State> by lazy {
        onViewStartObserving()
        _state
    }
    private var canLoadMore = false
    private var currEventPage = 1
    private val pageSize = 10

    private fun onViewStartObserving() {
        viewModelScope.launch {
            userRepository.getUserDetail(username = username)
                .fold(
                    onSuccess = { _state.value = _state.value.copy(userDetail = it) },
                    onFailure = ::onApiError,
                )
        }

        loadEventData(isRefresh = true)
    }

    override fun onLoadMore() {
        if (!canLoadMore) return
        loadEventData()
    }

    private fun loadEventData(isRefresh: Boolean = false) {
        viewModelScope.launch {
            val page = if (isRefresh) 1 else currEventPage + 1
            eventRepository.getEventList(username = username, page = page, pageSize = pageSize)
                .fold(
                    onSuccess = { newItems ->
                        canLoadMore = newItems.size >= pageSize
                        currEventPage = page

                        val items = if (isRefresh) newItems else _state.value.events.orEmpty() + newItems
                        _state.value = _state.value.copy(events = items)
                    },
                    onFailure = ::onApiError,
                )
        }
    }

    private fun onApiError(throwable: Throwable) {
        throwable.localizedMessage?.let {
            _state.value = _state.value.copy(
                dialogItem = DialogItem(
                    message = it,
                    onDismiss = { _state.value = _state.value.copy(dialogItem = null) },
                )
            )
        }
    }

    override fun onBackClick() {
        navigator.goBack()
    }

    data class State(
        val title: String,
        val userDetail: UserDetailData? = null,
        val events: List<EventData>? = null,
        val dialogItem: DialogItem? = null,
    )
}

interface UserDetailViewModelActionHandler {
    fun onBackClick() = Unit
    fun onLoadMore() = Unit
}
