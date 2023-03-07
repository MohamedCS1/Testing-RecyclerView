package com.example.testingrecyclerview.ui.movie

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.codingwithmitch.espressouitestexamples.ui.movie.MoviesListAdapter
import com.codingwithmitch.espressouitestexamples.ui.movie.StarActorsFragment
import com.example.testingrecyclerview.R
import com.example.testingrecyclerview.data.FakeMovieData
import com.example.testingrecyclerview.util.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
internal class MovieListFragmentTest
{
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    val positionItemsInTest = 4
    val movieInTest = FakeMovieData.movies[positionItemsInTest]

    @Before
    fun registerIdlingResource()
    {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlResource)
    }

    @After
    fun unregisterIdlingResource()
    {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlResource)
    }

    @Test
    fun testIsListFragmentVisibleOnAppLaunch()
    {
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
    }

    @Test
    fun testSelectItemIsDetailFragmentVisible()
    {
        onView(withId(R.id.recycler_view)).perform(actionOnItemAtPosition<MoviesListAdapter.MovieViewHolder>(positionItemsInTest ,click()))
        onView(withId(R.id.movie_title)).check(matches(withText(movieInTest.title)))
    }

    @Test
    fun testBackNavigationToMovieListFragment()
    {
        onView(withId(R.id.recycler_view)).perform(actionOnItemAtPosition<MoviesListAdapter.MovieViewHolder>(positionItemsInTest , click()))
        onView(withId(R.id.movie_title)).check(matches(withText(movieInTest.title)))
        pressBack()
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
    }

    @Test
    fun testNavDirectorsFragmentValidateDirectorsList()
    {
        onView(withId(R.id.recycler_view)).perform(actionOnItemAtPosition<MoviesListAdapter.MovieViewHolder>(positionItemsInTest ,click()))
        onView(withId(R.id.movie_title)).check(matches(withText(movieInTest.title)))

        onView(withId(R.id.movie_directiors)).perform(click())


        val directors = movieInTest.directors
        val verifyDirectorsValue = DirectorsFragment.stringBuilderForDirectors(directors!!)

        onView(withId(R.id.directors_text)).check(matches(withText(verifyDirectorsValue)))
    }

    @Test
    fun testNavStarActorsFragmentValidateStarActorsList()
    {
        onView(withId(R.id.recycler_view)).perform(actionOnItemAtPosition<MoviesListAdapter.MovieViewHolder>(positionItemsInTest ,click()))
        onView(withId(R.id.movie_title)).check(matches(withText(movieInTest.title)))

        onView(withId(R.id.movie_star_actors)).perform(click())


        val starActors = movieInTest.star_actors
        val verifyStarActorsValue = StarActorsFragment.stringBuilderForStarActors(starActors!!)

        onView(withId(R.id.star_actors_text)).check(matches(withText(verifyStarActorsValue)))
    }
}