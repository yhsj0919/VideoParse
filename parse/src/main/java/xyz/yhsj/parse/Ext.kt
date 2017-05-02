package xyz.yhsj.parse

import android.os.Environment
import android.os.Handler
import android.os.Looper
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
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

fun String.match0(text: String): String? {
    val matcher = Pattern.compile(this).matcher(text)
    if (matcher.find()) {
        return matcher.group(0)
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

/**
 * 获取sd卡路径
 */
fun getSDPath(): String {

    val sdCardExist = Environment.getExternalStorageState() == android.os.Environment.MEDIA_MOUNTED//判断sd卡是否存在
    if (sdCardExist) {
        return Environment.getExternalStorageDirectory().absolutePath//获取跟目录
    }
    return ""
}

/**
 * 文本的写入操作

 * @param filePath 文件路径。一定要加上文件名字 <br></br>
 * *                 例如：../a/a.txt
 * *
 * @param content  写入内容
 * *
 * @param append   是否追加
 */
fun writeToFile(filePath: String, fileName: String, content: String, append: Boolean): String {

    val file = File(filePath)
    if (!file.exists()) {
        file.mkdirs()
    }
    var bufw: BufferedWriter? = null
    try {
        bufw = BufferedWriter(OutputStreamWriter(FileOutputStream(filePath + fileName, append)))
        bufw.write(content)
    } catch (e1: Exception) {
        e1.printStackTrace()
    } finally {
        if (bufw != null) {
            try {
                bufw.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
    return filePath + fileName
}


fun runAsync(action: () -> Unit) = Thread(Runnable(action)).start()

fun runOnUiThread(action: () -> Unit) = Handler(Looper.getMainLooper()).post(Runnable(action))

fun runDelayed(delayMillis: Long, action: () -> Unit) = Handler().postDelayed(Runnable(action), delayMillis)

fun runDelayedOnUiThread(delayMillis: Long, action: () -> Unit) = Handler(Looper.getMainLooper()).postDelayed(Runnable(action), delayMillis)