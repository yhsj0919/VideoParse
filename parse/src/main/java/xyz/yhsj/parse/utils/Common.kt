package xyz.yshj.parse

import java.util.regex.Pattern

/**
 * Created by LOVE on 2017/3/1 001.
 */
fun match1(text: String, pattern: String): String? {
    val matcher = Pattern.compile(pattern).matcher(text)
    if (matcher.find()) {
        return matcher.group(1)
    } else {
        return null
    }
}