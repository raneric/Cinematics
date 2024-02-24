package com.sgg.cinematics.data

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.Job
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope

private const val TEST_DATA_STORE = "test_pref_datastore"
private val testDispatcher = StandardTestDispatcher()
private val coroutineScope = TestScope(testDispatcher + Job())
private val testContext: Context = ApplicationProvider.getApplicationContext()

val testDataStore = PreferenceDataStoreFactory.create(
    scope = coroutineScope,
    produceFile = { testContext.preferencesDataStoreFile(TEST_DATA_STORE) }
)