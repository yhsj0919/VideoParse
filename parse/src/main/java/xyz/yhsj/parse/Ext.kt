package xyz.yhsj.parse

import android.os.Handler
import android.os.Looper
import org.json.JSONArray
import org.json.JSONObject

/**扩展程序
 * Created by LOVE on 2017/4/13 013.
 */
val String.jsonObject
    get() = JSONObject(this)

val String.jsonArray
    get() = JSONArray(this)

fun runAsync(action: () -> Unit) = Thread(Runnable(action)).start()

fun runOnUiThread(action: () -> Unit) = Handler(Looper.getMainLooper()).post(Runnable(action))

fun runDelayed(delayMillis: Long, action: () -> Unit) = Handler().postDelayed(Runnable(action), delayMillis)

fun runDelayedOnUiThread(delayMillis: Long, action: () -> Unit) = Handler(Looper.getMainLooper()).postDelayed(Runnable(action), delayMillis)