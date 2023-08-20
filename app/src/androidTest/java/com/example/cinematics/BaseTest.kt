package com.example.cinematics

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule

open class BaseTest {
    @get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()
}