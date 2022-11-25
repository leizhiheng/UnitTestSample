package com.ubt.unittestsample

import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        println("addition_isCorrect")
        assertEquals(4, 2 + 2)
    }

    @Test
    fun multi_isCorrect() {
        println("multi_isCorrect")
        assertEquals(4, 2 * 2)
    }

    @Before
    fun runBefore() {
        println("runBefore")
    }

    @After
    fun runAfter() {
        println("runAfter")
    }
}