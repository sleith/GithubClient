package liem.ray.githubclient.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import liem.ray.githubclient.common.item.DialogItem
import liem.ray.githubclient.data.UserData
import liem.ray.githubclient.navigation.NavigatorService
import liem.ray.githubclient.repos.UserRepository

class UserListViewModel(
    private val userRepository: UserRepository,
    private val navigator: NavigatorService,
) : ViewModel(), UserListViewModelActionHandler {

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> by lazy {
        onViewStartObserving()
        _state
    }
    private var canLoadMore = false
    private val pageSize = 10

    private fun onViewStartObserving() {
        loadData(isRefresh = true)
    }

    override fun onLoadMore() {
        if (!canLoadMore) return
        loadData()
    }

    override fun onRefresh() {
        _state.value = _state.value.copy(isRefreshing = true)
        loadData(isRefresh = true)
    }

    private fun loadData(isRefresh: Boolean = false) {
        viewModelScope.launch {
            val since = if (isRefresh) null else _state.value.users.lastOrNull()?.id
            userRepository.getUserList(since = since, pageSize = pageSize)
                .fold(
                    onSuccess = { newItems ->
                        canLoadMore = newItems.size >= pageSize
                        val items = if (isRefresh) newItems else _state.value.users + newItems
                        _state.value = _state.value.copy(users = items)
                    },
                    onFailure = { throwable ->
                        throwable.localizedMessage?.let {
                            _state.value = _state.value.copy(
                                dialogItem = DialogItem(
                                    message = it,
                                    onDismiss = { _state.value = _state.value.copy(dialogItem = null) },
                                ),
                            )
                        }
                    },
                )
            _state.value = _state.value.copy(isRefreshing = false)
        }
    }


    override fun onUserClick(userData: UserData) {
        navigator.navigateToUserDetail(username = userData.login)
    }

    data class State(
        val users: List<UserData> = emptyList(),
        val dialogItem: DialogItem? = null,
        val isRefreshing: Boolean = false,
    )
}

interface UserListViewModelActionHandler {
    fun onUserClick(userData: UserData) = Unit
    fun onLoadMore() = Unit
    fun onRefresh() = Unit
}
