package com.appstr.timecontrol.junit

import com.appstr.timecontrol.domain.models.formatTimeToText
import com.google.common.truth.Truth.assertThat
import org.junit.Test


class TimeUtilTest {

    @Test
    fun `does null time show question mark`(){
        val res = null.formatTimeToText()
        println("`does null time show question mark`----result:$res")
        assertThat(res == "?").isTrue()
    }

    @Test
    fun `does 1000 millis display correctly`(){
        val res = 1000.formatTimeToText()
        println("`does 1000 millis display correctly`----result:$res")
        assertThat(res == "0:01").isTrue()
    }

    @Test
    fun `does 11 seconds display correctly`(){
        val res = 11000.formatTimeToText()
        println("`does 11 seconds display correctly`----result:$res")
        assertThat(res == "0:11").isTrue()
    }

    @Test
    fun `does 61 seconds display correctly`(){
        val res = 61000.formatTimeToText()
        println("`does 61 seconds display correctly`----result:$res")
        assertThat(res == "1:01").isTrue()
    }

    @Test
    fun `does 59 minutes 59 seconds display correctly`(){
        val res = 3599000.formatTimeToText()
        println("`does 59 minutes 59 seconds display correctly`----result:$res")
        assertThat(res == "59:59").isTrue()
    }

    @Test
    fun `does 61 minutes display correctly`(){
        val res = 3660000.formatTimeToText()
        println("`does 61 minutes display correctly`----result:$res")
        assertThat(res == "1:01:00").isTrue()
    }

    @Test
    fun `does 1 hour 59 minutes 59 seconds display correctly`(){
        val res = 7199000.formatTimeToText()
        println("`does 1 hour 59 minutes 59 seconds display correctly`----result:$res")
        assertThat(res == "1:59:59").isTrue()
    }

}