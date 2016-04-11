package xyz.yhsj.videoparse.extractors.youku.service;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import rx.Observable;
import xyz.yhsj.videoparse.extractors.youku.entity.YouKuEntity;

/**
 * Created by LOVE on 2016/4/11 0011.
 */
public interface YouKuService {

    @GET("http://play.youku.com/play/get.json?ct=12")
    Observable<YouKuEntity> getYouKuData12(@Header("Referer") String Cookie, @Header("Cookie") String Referer, @Query("vid") String vid);

    @GET("http://play.youku.com/play/get.json?ct=10")
    Observable<YouKuEntity> getYouKuData10(@Query("vid") String vid);

}
