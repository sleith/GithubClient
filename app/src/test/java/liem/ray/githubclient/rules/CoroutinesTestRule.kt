package liem.ray.githubclient.rules

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.ExternalResource

class CoroutinesTestRule(private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()) : ExternalResource() {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun before() {
        super.before()
        Dispatchers.setMain(dispatcher = testDispatcher)
    }

    override fun after() {
        super.after()
        Dispatchers.resetMain()
    }
}