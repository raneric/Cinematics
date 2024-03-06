package com.sgg.cinematics

import com.sgg.cinematics.utils.validateEmail
import com.sgg.cinematics.utils.validatePassword
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

@HiltAndroidTest
class InputValidatorTest : BaseTest() {

    @Test
    fun test_email_validator() {
        assertTrue(validateEmail("sample@mail.com"))
        assertFalse(validateEmail("saple@mailcom"))
        assertFalse(validateEmail("samplemail.com"))
    }

    @Test
    fun test_password_validator() {
        assertFalse(validatePassword("1"))
        assertFalse(validatePassword("1234567"))
        assertFalse(validatePassword("dkAd15"))
        assertFalse(validatePassword("dkAd15f"))
        assertTrue(validatePassword("sD48ftR!u"))
    }
}