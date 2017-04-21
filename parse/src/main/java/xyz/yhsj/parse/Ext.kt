package xyz.yhsj.parse

import android.os.Handler
import android.os.Looper
import org.json.JSONArray
import org.json.JSONObject
import java.util.regex.Pattern

/**扩展程序
 * Created by LOVE on 2017/4/13 013.
 */
val String.jsonObject
    get() = JSONObject(this)

val String.jsonArray
    get() = JSONArray(this)


fun String.match1(text: String): String? {
    val matcher = Pattern.compile(this).matcher(text)
    if (matcher.find()) {
        return matcher.group(1)
    } else {
        return null
    }
}

fun String.matchAll(text: String): List<String> {
    val results = ArrayList<String>()
    val matcher = Pattern.compile(this).matcher(text)
    while (matcher.find()) {
        results.add(matcher.group(1))
    }
    return results

}


fun runAsync(action: () -> Unit) = Thread(Runnable(action)).start()

fun runOnUiThread(action: () -> Unit) = Handler(Looper.getMainLooper()).post(Runnable(action))

fun runDelayed(delayMillis: Long, action: () -> Unit) = Handler().postDelayed(Runnable(action), delayMillis)

fun runDelayedOnUiThread(delayMillis: Long, action: () -> Unit) = Handler(Looper.getMainLooper()).postDelayed(Runnable(action), delayMillis)