package liem.ray.githubclient.ui

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import liem.ray.githubclient.api.interactors.UserApiInteractor
import liem.ray.githubclient.data.UserData
import liem.ray.githubclient.navigation.NavigatorService
import liem.ray.githubclient.rules.CoroutinesTestRule
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class UserListViewModelTest {
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val userApiInteractor = mockk<UserApiInteractor>(relaxed = true)
    private val navigator = mockk<NavigatorService>(relaxed = true)
    private val viewModel = UserListViewModel(
        userApiInteractor = userApiInteractor,
        navigator = navigator,
    )

    @Test
    fun `Should get user list when view start observing`() = runTest {
        val userList = listOf(
            UserData(login = "ray1", id = 1L, avatarUrl = "avatarUrl"),
            UserData(login = "ray2", id = 2L, avatarUrl = "avatarUrl"),
        )
        coEvery {
            userApiInteractor.getUserList(since = any())
        } returns Result.success(userList)

        viewModel.state

        coVerify {
            userApiInteractor.getUserList(since = any())
        }
        val stateValue = viewModel.state.value
        assertEquals(expected = userList, stateValue.users)
    }

    @Test
    fun `On get user list failure should show error dialog`() = runTest {
        coEvery {
            userApiInteractor.getUserList(since = any())
        } returns Result.failure(Throwable(message = "Error"))

        val errorDialogItem = viewModel.state.value.dialogItem
        assertNotNull(actual = errorDialogItem)
        assertEquals(expected = "Error", actual = errorDialogItem.message)
    }

    @Test
    fun `On user item click should navigate to user detail screen`() {
        val userData = UserData(login = "ray", id = 1L, avatarUrl = "avatarUrl")
        viewModel.onUserClick(userData = userData)
        verify {
            navigator.navigateToUserDetail(username = "ray")
        }
    }
}