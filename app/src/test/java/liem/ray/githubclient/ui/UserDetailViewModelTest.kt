package liem.ray.githubclient.ui

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import liem.ray.githubclient.api.interactors.EventApiInteractor
import liem.ray.githubclient.api.interactors.UserApiInteractor
import liem.ray.githubclient.data.EventData
import liem.ray.githubclient.data.UserDetailData
import liem.ray.githubclient.navigation.NavigatorService
import liem.ray.githubclient.rules.CoroutinesTestRule
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class UserDetailViewModelTest {
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val userApiInteractor = mockk<UserApiInteractor>(relaxed = true)
    private val eventApiInteractor = mockk<EventApiInteractor>(relaxed = true)
    private val navigator = mockk<NavigatorService>(relaxed = true)
    private val viewModel = UserDetailViewModel(
        username = "raymond",
        userApiInteractor = userApiInteractor,
        eventApiInteractor = eventApiInteractor,
        navigator = navigator,
    )

    @Test
    fun `Should get user details and events when view start observing`() = runTest {
        val userDetailData = mockk<UserDetailData>()
        coEvery {
            userApiInteractor.getUserDetail(username = any())
        } returns Result.success(userDetailData)

        val eventItems = listOf(mockk<EventData>(), mockk<EventData>(), mockk<EventData>())
        coEvery {
            eventApiInteractor.getEventList(username = any(), page = any())
        } returns Result.success(eventItems)

        viewModel.state

        coVerify {
            userApiInteractor.getUserDetail(username = "raymond")
            eventApiInteractor.getEventList(username = "raymond", page = 1)
        }
        assertEquals(expected = userDetailData, actual = viewModel.state.value.userDetail)
        assertEquals(expected = eventItems, actual = viewModel.state.value.events)
    }

    @Test
    fun `On get user detail failure should show error dialog`() = runTest {
        coEvery {
            userApiInteractor.getUserDetail(username = any())
        } returns Result.failure(Throwable(message = "Error"))

        coEvery {
            eventApiInteractor.getEventList(username = any(), page = any())
        } returns Result.success(mockk())

        val errorDialogItem = viewModel.state.value.dialogItem
        assertNotNull(actual = errorDialogItem)
        assertEquals(expected = "Error", actual = errorDialogItem.message)
    }

    @Test
    fun `On get events failure should show error dialog`() = runTest {
        coEvery {
            userApiInteractor.getUserDetail(username = any())
        } returns Result.success(mockk())

        coEvery {
            eventApiInteractor.getEventList(username = any(), page = any())
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