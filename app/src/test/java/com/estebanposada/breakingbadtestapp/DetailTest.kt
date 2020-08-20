package com.estebanposada.breakingbadtestapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.estebanposada.breakingbadtestapp.ui.MainViewModel
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import java.util.*

class DetailTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val observer: Observer<MainViewModel.detail> = mock()
}