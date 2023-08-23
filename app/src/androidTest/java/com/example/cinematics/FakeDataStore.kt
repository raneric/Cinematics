package com.example.cinematics

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.Job
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope

private const val TEST_DATA_STORE = "test_pref_datastore"
private val testDispacher: TestCoroutineDispatcher = TestCoroutineDispatcher()
private val coroutineScope: TestCoroutineScope = TestCoroutineScope(testDispacher + Job())
private val testContext: Context = ApplicationProvider.getApplicationContext()

val testDataStore = PreferenceDataStoreFactory.create(
    scope = coroutineScope,
    produceFile = { testContext.preferencesDataStoreFile(TEST_DATA_STORE) }
)