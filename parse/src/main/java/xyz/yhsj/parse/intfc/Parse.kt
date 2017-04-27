package xyz.yhsj.parse.intfc

import xyz.yhsj.parse.entity.ParseResult

/**接口
 * Created by LOVE on 2017/4/21 021.
 */
interface Parse {
    fun download(url: String): ParseResult
}