package com.exercise.urlshortener

import kotlin.math.ceil
import kotlin.math.log

/*
 * Produces a sequential id made up of digits, lowercase and uppercase letters
 * starting from 1 going up to Int.MAX_VALUE
 * If maximum value is reached 1 will be provided as the next value
 */

class IdGenerator(seed : Int = 1) {

    private var counter = seed.toLong()
    private val characters : MutableList<Char> = ArrayList()
    private val baseDigits : MutableList<Int> = ArrayList()

    init {
        if (seed <= 0) throw IllegalArgumentException("seed must be positive")

        for (ch in '0'..'9') characters.add(ch)
        for (ch in 'a'..'z') characters.add(ch)
        for (ch in 'A'..'Z') characters.add(ch)

        val base = characters.size
        val maxPower = log(Int.MAX_VALUE.toDouble(), base.toDouble())
        val numCharacters = ceil(maxPower).toInt()

        var currentPower = 1
        for (i in 0 until numCharacters) {
            baseDigits.add(currentPower)
            currentPower *= base
        }
        baseDigits.reverse()
    }

    fun getNext() : String {
        val digits = getDigits(counter.toInt())
        var result = ""
        for (d in digits) {
            result += characters[d]
        }
        counter = (counter + 1) % Int.MAX_VALUE
        return result.trimStart('0')
    }

    private fun getDigits(num: Int) : IntArray {
        var remaining = num
        val result = IntArray(baseDigits.size)
        for (i in 0 until baseDigits.size) {
            result[i] = remaining / baseDigits[i]
            if (remaining >= baseDigits[i]) {
                remaining -= result[i] * baseDigits[i]
            }
        }
        return result
    }

}
