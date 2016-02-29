package xyz.yhsj.videoparse.utils;

import com.google.gson.Gson;

import org.xutils.http.app.ResponseParser;
import org.xutils.http.request.UriRequest;

import java.lang.reflect.Type;

/**
 * Created by LOVE on 2015/12/4.
 * 返回数据的解析类
 * 在需要解析的实体类名上面上请加入下面的话，即可在回调中使用此类解析
 *
 * @HttpResponse(parser = JsonResponseParser.class)
 */
public class JsonResponseParser implements ResponseParser {

    @Override
    public void checkResponse(UriRequest request) throws Throwable {
        // custom check ?
        // check header ?
    }

    /**
     * 转换result为resultType类型的对象
     *
     * @param resultType  返回值类型(可能带有泛型信息)
     * @param resultClass 返回值类型
     * @param result      字符串数据
     * @return
     * @throws Throwable
     */
    @Override
    public Object parse(Type resultType, Class<?> resultClass, String result) throws Throwable {
//        LogUtil.w(result);
        return new Gson().fromJson(result, resultType);

    }
}
