package xyz.yhsj.parse.entity

import java.io.Serializable

/**媒体文件实体
 * Created by LOVE on 2017/4/24 024.
 */
data class MediaFile(
        var code: Int = 0,
        var name: String? = null
) : Serializable