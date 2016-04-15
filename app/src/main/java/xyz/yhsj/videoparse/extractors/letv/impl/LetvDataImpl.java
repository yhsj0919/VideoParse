package xyz.yhsj.videoparse.extractors.letv.impl;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import xyz.yhsj.videoparse.common.RetrofitClient;
import xyz.yhsj.videoparse.extractors.letv.Letv;
import xyz.yhsj.videoparse.extractors.letv.entity.LetvEntity;
import xyz.yhsj.videoparse.extractors.letv.service.LetvService;
import xyz.yhsj.videoparse.utils.DateUtils;

/**
 * Created by LOVE on 2016/4/11 0011.
 */
public class LetvDataImpl {

    private LetvService letvService;

    public LetvDataImpl() {
        letvService = RetrofitClient.getInstance().create(LetvService.class);
    }


    public Observable<LetvEntity> getLetvData(String id) {
        return letvService
                .getLetvData("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36 LBBROWSER",
                        "close",
                        "identity",
                        id,
                        Letv.getTkey(DateUtils.getCurrentTime()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
