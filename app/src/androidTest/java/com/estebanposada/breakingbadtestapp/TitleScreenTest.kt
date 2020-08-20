package com.estebanposada.breakingbadtestapp

import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.estebanposada.breakingbadtestapp.ui.detail.DetailFragment
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TitleScreenTest {

    @Test
    fun testNavigationToDetailScreen(){
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.setGraph(R.navigation.nav_graph)

        val asdf = launchFragmentInContainer<DetailFragment>

        asdf.onFragment{fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        onView(ViewMatchers.withId(R.id.ch_favorite)).perform(ViewActions.click())
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.action_mainFragment_to_detailFragment)
    }
}