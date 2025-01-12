package liem.ray.githubclient.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import liem.ray.githubclient.api.interactors.UserApiInteractor
import liem.ray.githubclient.common.item.DialogItem
import liem.ray.githubclient.navigation.NavigatorService

class UserDetailViewModel(
    private val username: String,
    private val userApiInteractor: UserApiInteractor,
    private val navigator: NavigatorService,
) : ViewModel(), UserDetailViewModelActionHandler {

    private val _state = MutableStateFlow(State(title = username))
    val state: StateFlow<State> by lazy {
        onViewStartObserving()
        _state
    }

    private fun onViewStartObserving() {

    }

    data class State(
        val title: String,
        val dialogItem: DialogItem? = null,
    )
}

interface UserDetailViewModelActionHandler {

}
