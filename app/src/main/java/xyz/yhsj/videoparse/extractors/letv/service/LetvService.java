package xyz.yhsj.videoparse.extractors.letv.service;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import rx.Observable;
import xyz.yhsj.videoparse.extractors.letv.entity.LetvEntity;

/**
 * Created by LOVE on 2016/4/11 0011.
 */
public interface LetvService {

    @GET("http://api.letv.com/mms/out/video/playJson?platid=1&splatid=101&format=1&domain=www.letv.com")
    Observable<LetvEntity> getLetvData(@Header("User-Agent") String user_Agent,
                                       @Header("Connection") String connection,
                                       @Header("Accept-Encoding") String accept_Encoding,
                                       @Query("id") String id,
                                       @Query("tkey") long tkey
    );

}
