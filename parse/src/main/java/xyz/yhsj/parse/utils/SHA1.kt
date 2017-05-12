package xyz.yhsj.parse.utils

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import kotlin.experimental.and

/**
 * Created by LOVE on 2017/5/12 012.
 */

object SHA1 {

    fun sha1(decript: String): String {
        try {
            val digest = java.security.MessageDigest.getInstance("SHA-1")
            digest.update(decript.toByteArray())
            val messageDigest = digest.digest()
            // Create Hex String
            val hexString = StringBuffer()
            // 字节数组转换为 十六进制 数
            for (i in messageDigest.indices) {
                val shaHex = Integer.toHexString(messageDigest[i].toInt() and 0xFF)
                if (shaHex.length < 2) {
                    hexString.append(0)
                }
                hexString.append(shaHex)
            }
            return hexString.toString()

        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return ""
    }
}
