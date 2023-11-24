package com.appstr.timecontrol

import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.appstr.timecontrol.ui.MainActivity
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ActivityTestRunner {

    @Test
    fun testActivityThing(){
        launchActivity<MainActivity>().use {

        }
    }


}