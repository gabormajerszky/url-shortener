package com.exercise.urlshortener

import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class IdGeneratorTests {

    val testMap = mapOf(
            1 to "1",
            10 to "a",
            61 to "Z",
            62 to "10",
            3843 to "ZZ",
            3844 to "100",
            238327 to "ZZZ",
            238328 to "1000",
            Int.MAX_VALUE to "2lkCB1",
            46133 to "c05"
    )

    @Test
    fun throwsExceptionWithIncorrectSeed() {
        try {
            IdGenerator(0)
            fail()
        } catch (e : IllegalArgumentException) {
            assertEquals("seed must be positive", e.message)
        }
    }

    @Test
    fun generatesCorrectIds() {
        for (item in testMap) {
            val idGenerator = IdGenerator(item.key)
            val expectedId = item.value
            val actualId = idGenerator.getNext()
            assertEquals(expectedId, actualId)
        }
    }

    @Test
    fun generatesCorrectNext() {
        var idGenerator = IdGenerator(61)
        idGenerator.getNext()
        assertEquals("10", idGenerator.getNext())
        idGenerator = IdGenerator(Int.MAX_VALUE)
        idGenerator.getNext()
        assertEquals("1", idGenerator.getNext())
    }

}
