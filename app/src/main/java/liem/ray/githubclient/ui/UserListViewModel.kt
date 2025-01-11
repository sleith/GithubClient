package liem.ray.githubclient.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import liem.ray.githubclient.api.interactors.UserApiInteractor
import liem.ray.githubclient.data.UserData
import liem.ray.githubclient.navigation.NavigatorService

class UserListViewModel(
    private val userApiInteractor: UserApiInteractor,
    private val navigator: NavigatorService,
) : ViewModel(), UserListViewModelActionHandler {

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> by lazy {
        onViewStartObserving()
        _state
    }

    private fun onViewStartObserving() {
        viewModelScope.launch {
            userApiInteractor.getUserList()
                .fold(
                    onSuccess = {},
                    onFailure = {},
                )
        }
    }


    override fun onUserClick(userData: UserData) {
        TODO("Not yet implemented")
    }

    data class State(
        val users: List<UserData> = emptyList(),
    )
}

interface UserListViewModelActionHandler {
    fun onUserClick(userData: UserData) = Unit
}
