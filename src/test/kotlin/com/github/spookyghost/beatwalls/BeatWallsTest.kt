package com.github.spookyghost.beatwalls

import org.junit.Before
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

class BeatwallsTest {

    @Test
    //TODO SETUP METHOD, WHICH CREATES A SAMPLE DIFFICULTY WITH ALL SPECIAL CASES
    //TODO TESTING THE SAMPLE DIFFICULTY
    //TODO TESTING THE MAIN METHOD
    fun testInport(){
        // Arrange
        val expected = _bookmarks(1.0,"test")
        val actual = readDifficulty(File(javaClass.getResource("/song/Test.dat").toURI()))

        // Act
        assertEquals(actual._bookmarks.first(), expected)
    }


}
