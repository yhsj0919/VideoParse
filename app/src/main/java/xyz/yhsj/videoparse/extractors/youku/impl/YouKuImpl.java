package xyz.yhsj.videoparse.extractors.youku.impl;

import android.accounts.NetworkErrorException;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import xyz.yhsj.videoparse.common.RetrofitClient;
import xyz.yhsj.videoparse.extractors.youku.YouKu;
import xyz.yhsj.videoparse.extractors.youku.entity.SecurityEntity;
import xyz.yhsj.videoparse.extractors.youku.entity.StreamEntity;
import xyz.yhsj.videoparse.extractors.youku.entity.VideoDownLoadEntity;
import xyz.yhsj.videoparse.extractors.youku.entity.YouKuEntity;
import xyz.yhsj.videoparse.extractors.youku.service.YouKuService;

/**
 * Created by LOVE on 2016/4/11 0011.
 */
public class YouKuImpl {

    private YouKuService youKuService;

    public YouKuImpl() {
        youKuService = RetrofitClient.getInstance().create(YouKuService.class);
    }

    /**
     * 获取数据1
     *
     * @param vid
     * @return
     */
    private Observable<YouKuEntity> getYouKuData12(String vid) {
        return youKuService
                .getYouKuData12("http://static.youku.com/", "__ysuid=1447684637230HKz", vid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取数据2
     *
     * @param vid
     * @return
     */
    private Observable<YouKuEntity> getYouKuData10(String vid) {
        return youKuService
                .getYouKuData10(vid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取格式化后的数据实体
     *
     * @param vid
     * @return
     */
    public Observable<YouKuEntity> getYouKuData(final String vid) {
        return getYouKuData12(vid)
                .flatMap(new Func1<YouKuEntity, Observable<YouKuEntity>>() {
                    @Override
                    public Observable<YouKuEntity> call(YouKuEntity youKuEntity) {

                        final SecurityEntity securityEntity = youKuEntity.getData().getSecurity();

                        return getYouKuData10(vid)
                                .map(new Func1<YouKuEntity, YouKuEntity>() {
                                    @Override
                                    public YouKuEntity call(YouKuEntity youKuEntity) {

                                        youKuEntity.getData().setSecurity(securityEntity);

                                        List<StreamEntity> streamEntitys = youKuEntity.getData().getStream();

                                        for (int i = 0; i < streamEntitys.size(); i++) {

                                            StreamEntity newStreamEntity = YouKu.parseStreamEntity(streamEntitys.get(i), securityEntity);
                                            streamEntitys.set(i, newStreamEntity);
                                        }

                                        return youKuEntity;
                                    }
                                });
                    }
                })
                .retry(2);
    }


    public Observable<List<VideoDownLoadEntity>> getVideoDownloadUrl(final String url) {
        return youKuService.getVideoDownloadUrl(url).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

}
