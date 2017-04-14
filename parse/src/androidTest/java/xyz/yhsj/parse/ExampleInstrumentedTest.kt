package xyz.yhsj.parse

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import xyz.yhsj.parse.extractors.KuGou

/**
 * Instrumentation test, which will execute on an Android device.

 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)
class ExampleInstrumentedTest {
    @Test
    @Throws(Exception::class)

    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        KuGou.kugou_download("http://5sing.kugou.com/yc/3287805.html")
    }
}
