package liem.ray.githubclient.ui

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import liem.ray.githubclient.data.UserData
import liem.ray.githubclient.navigation.NavigatorService
import liem.ray.githubclient.repos.UserRepository
import liem.ray.githubclient.rules.CoroutinesTestRule
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class UserListViewModelTest {
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val userRepository = mockk<UserRepository>(relaxed = true)
    private val navigator = mockk<NavigatorService>(relaxed = true)
    private val viewModel = UserListViewModel(
        userRepository = userRepository,
        navigator = navigator,
    )

    @Test
    fun `Should get user list when view start observing`() = runTest {
        val userList = listOf(
            UserData(login = "ray1", id = 1L, avatarUrl = "avatarUrl"),
            UserData(login = "ray2", id = 2L, avatarUrl = "avatarUrl"),
        )
        coEvery {
            userRepository.getUserList(since = any())
        } returns Result.success(userList)

        viewModel.state

        coVerify {
            userRepository.getUserList(since = any())
        }
        val stateValue = viewModel.state.value
        assertEquals(expected = userList, stateValue.users)
    }

    @Test
    fun `On get user list failure should show error dialog`() = runTest {
        coEvery {
            userRepository.getUserList(since = any())
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

    @Test
    fun `On load more data calls load more items correctly`() = runTest {
        val firstPageItems = listOf(
            UserData(login = "ray1", id = 1L, avatarUrl = "avatarUrl"),
            UserData(login = "ray2", id = 2L, avatarUrl = "avatarUrl"),
        )
        coEvery {
            userRepository.getUserList(since = null)
        } returns Result.success(firstPageItems)

        val secondPageItems = listOf(
            UserData(login = "ray3", id = 1L, avatarUrl = "avatarUrl"),
            UserData(login = "ray4", id = 2L, avatarUrl = "avatarUrl"),
        )
        coEvery {
            userRepository.getUserList(since = 2)
        } returns Result.success(secondPageItems)

        viewModel.state
        coVerify {
            userRepository.getUserList(since = null)
        }

        viewModel.onLoadMore()

        coVerify {
            userRepository.getUserList(since = 2)
        }

        val stateValue = viewModel.state.value
        assertEquals(expected = firstPageItems + secondPageItems, stateValue.users)
    }

    @Test
    fun `On refresh should reload data correctly`() = runTest {
        val firstPageItems = listOf(
            UserData(login = "ray1", id = 1L, avatarUrl = "avatarUrl"),
            UserData(login = "ray2", id = 2L, avatarUrl = "avatarUrl"),
        )
        coEvery {
            userRepository.getUserList(since = null)
        } returns Result.success(firstPageItems)

        coEvery {
            userRepository.getUserList(since = 2)
        } returns Result.success(
            listOf(
                UserData(login = "ray3", id = 3L, avatarUrl = "avatarUrl"),
                UserData(login = "ray4", id = 4L, avatarUrl = "avatarUrl"),
            )
        )

        coEvery {
            userRepository.getUserList(since = 4)
        } returns Result.success(
            listOf(
                UserData(login = "ray3", id = 3L, avatarUrl = "avatarUrl"),
                UserData(login = "ray4", id = 4L, avatarUrl = "avatarUrl"),
            )
        )


        viewModel.state
        viewModel.onLoadMore()
        viewModel.onLoadMore()

        assertEquals(expected = 6, viewModel.state.value.users.size)

        viewModel.onRefresh()

        coVerify {
            userRepository.getUserList(since = null)
        }
        assertEquals(expected = firstPageItems, viewModel.state.value.users)
    }
}