package liem.ray.githubclient.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import liem.ray.githubclient.api.interactors.UserApiInteractor
import liem.ray.githubclient.common.item.DialogItem
import liem.ray.githubclient.data.UserDetailData
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
        viewModelScope.launch {
            userApiInteractor.getUserDetail(username = username)
                .fold(
                    onSuccess = { _state.value = _state.value.copy(userDetail = it) },
                    onFailure = { throwable ->
                        throwable.localizedMessage?.let {
                            _state.value = _state.value.copy(
                                dialogItem = DialogItem(
                                    message = it,
                                    onDismiss = { _state.value = _state.value.copy(dialogItem = null) },
                                )
                            )
                        }
                    },
                )
        }
    }

    override fun onBackClick() {
        navigator.goBack()
    }

    data class State(
        val title: String,
        val userDetail: UserDetailData? = null,
        val dialogItem: DialogItem? = null,
    )
}

interface UserDetailViewModelActionHandler {
    fun onBackClick() = Unit
}
