package com.estebanposada.breakingbadtestapp

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule

@HiltAndroidTest
class ActivityTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)
}