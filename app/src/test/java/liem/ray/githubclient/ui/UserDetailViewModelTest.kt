package liem.ray.githubclient.ui

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import liem.ray.githubclient.data.EventData
import liem.ray.githubclient.data.UserDetailData
import liem.ray.githubclient.navigation.NavigatorService
import liem.ray.githubclient.repos.EventRepository
import liem.ray.githubclient.repos.UserRepository
import liem.ray.githubclient.rules.CoroutinesTestRule
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class UserDetailViewModelTest {
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val userRepository = mockk<UserRepository>(relaxed = true)
    private val eventRepository = mockk<EventRepository>(relaxed = true)
    private val navigator = mockk<NavigatorService>(relaxed = true)
    private val viewModel = UserDetailViewModel(
        username = "raymond",
        userRepository = userRepository,
        eventRepository = eventRepository,
        navigator = navigator,
    )

    @Test
    fun `Should get user details and events when view start observing`() = runTest {
        val userDetailData = mockk<UserDetailData>()
        coEvery {
            userRepository.getUserDetail(username = any())
        } returns Result.success(userDetailData)

        val eventItems = listOf(mockk<EventData>(), mockk<EventData>(), mockk<EventData>())
        coEvery {
            eventRepository.getEventList(username = any(), page = any(), pageSize = any())
        } returns Result.success(eventItems)

        viewModel.state

        coVerify {
            userRepository.getUserDetail(username = "raymond")
            eventRepository.getEventList(username = "raymond", page = 1, pageSize = 10)
        }
        assertEquals(expected = userDetailData, actual = viewModel.state.value.userDetail)
        assertEquals(expected = eventItems, actual = viewModel.state.value.events)
    }

    @Test
    fun `On get user detail failure should show error dialog`() = runTest {
        coEvery {
            userRepository.getUserDetail(username = any())
        } returns Result.failure(Throwable(message = "Error"))

        val eventItems = listOf(mockk<EventData>(), mockk<EventData>(), mockk<EventData>())
        coEvery {
            eventRepository.getEventList(username = any(), page = any(), pageSize = any())
        } returns Result.success(eventItems)

        val errorDialogItem = viewModel.state.value.dialogItem
        assertNotNull(actual = errorDialogItem)
        assertEquals(expected = "Error", actual = errorDialogItem.message)
    }

    @Test
    fun `On get events failure should show error dialog`() = runTest {
        coEvery {
            userRepository.getUserDetail(username = any())
        } returns Result.success(mockk())

        coEvery {
            eventRepository.getEventList(username = any(), page = any(), pageSize = any())
        } returns Result.failure(Throwable(message = "Error"))

        val errorDialogItem = viewModel.state.value.dialogItem
        assertNotNull(actual = errorDialogItem)
        assertEquals(expected = "Error", actual = errorDialogItem.message)
    }

    @Test
    fun `On back click should navigate back`() {
        viewModel.onBackClick()

        verify {
            navigator.goBack()
        }
    }
}