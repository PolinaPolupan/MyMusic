package com.example.mymusic.core.datastore

import com.example.mymusic.core.datastore.di.failingTestUserPreferencesDataStore
import com.example.mymusic.core.datastore.di.testUserPreferencesDataStore
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.IOException

class MyMusicPreferencesDataSourceTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testScope = TestScope(UnconfinedTestDispatcher())

    private lateinit var dataSource: MyMusicPreferencesDataSource

    @get:Rule
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    @Before
    fun setup() {
        dataSource = MyMusicPreferencesDataSource(
            tmpFolder.testUserPreferencesDataStore()
        )
    }

    @Test
    fun preferencesAreNullByDefault() = testScope.runTest {
        val userPreferences = dataSource.userPreferencesFlow.first()
        assertNull(userPreferences.authState)
        assertNull(userPreferences.email)
        assertNull(userPreferences.imageUrl)
        assertNull(userPreferences.displayName)
    }

    @Test
    fun updateAuthStateUpdatesPreference() = testScope.runTest {
        val authState = "LOGGED_IN"

        dataSource.updateAuthState(authState)

        val userPreferences = dataSource.userPreferencesFlow.first()
        assertEquals(authState, userPreferences.authState)
    }

    @Test
    fun updateUserDataUpdatesAllPreferences() = testScope.runTest {
        val displayName = "John Doe"
        val email = "user@example.com"
        val imageUrl = "http://example.com/image.jpg"

        dataSource.updateUserData(displayName, email, imageUrl)

        val userPreferences = dataSource.userPreferencesFlow.first()
        assertEquals(displayName, userPreferences.displayName)
        assertEquals(email, userPreferences.email)
        assertEquals(imageUrl, userPreferences.imageUrl)
    }

    @Test
    fun userPreferencesFlowReflectsUpdatedData() = testScope.runTest {
        // Initial state
        assertNull(dataSource.userPreferencesFlow.first().authState)

        // Update auth state
        val authState = "LOGGED_IN"
        dataSource.updateAuthState(authState)
        assertEquals(authState, dataSource.userPreferencesFlow.first().authState)

        // Update user data
        val displayName = "John Doe"
        val email = "user@example.com"
        val imageUrl = "http://example.com/image.jpg"
        dataSource.updateUserData(displayName, email, imageUrl)

        val userPreferences = dataSource.userPreferencesFlow.first()
        assertEquals(authState, userPreferences.authState)
        assertEquals(displayName, userPreferences.displayName)
        assertEquals(email, userPreferences.email)
        assertEquals(imageUrl, userPreferences.imageUrl)
    }

    @Test
    fun userPreferencesFlowHandlesIOException() = testScope.runTest {
        // Simulate an exception in the flow
        val failingDataStore = tmpFolder.failingTestUserPreferencesDataStore(IOException("Test IOException"))
        val failingDataSource = MyMusicPreferencesDataSource(failingDataStore)

        val userPreferences = failingDataSource.userPreferencesFlow.first()

        assertNull(userPreferences.authState)
        assertNull(userPreferences.email)
        assertNull(userPreferences.imageUrl)
        assertNull(userPreferences.displayName)
    }
}