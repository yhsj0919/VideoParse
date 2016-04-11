package xyz.yhsj.videoparse.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;
import xyz.yhsj.event.OnItemClickListener;
import xyz.yhsj.videoparse.R;
import xyz.yhsj.videoparse.extractors.youku.YouKu;
import xyz.yhsj.videoparse.extractors.youku.entity.VideoDownLoadEntity;
import xyz.yhsj.videoparse.extractors.youku.entity.YouKuEntity;
import xyz.yhsj.videoparse.extractors.youku.impl.YouKuImpl;
import xyz.yhsj.videoparse.ui.adapter.VideoListAdapter;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    private VideoListAdapter adapter;

    private YouKuImpl youKuImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        init();
    }

    private void init() {
        youKuImpl = new YouKuImpl();

        adapter = new VideoListAdapter(recyclerView);

        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getYouKu();

//                getLeTv();


            }
        });


        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View itemView, int position) {

                System.out.println(adapter.getItem(position).getSegs().get(0).getDownlad_url());

                youKuImpl.getVideoDownloadUrl(adapter.getItem(position).getSegs().get(0).getDownlad_url())
                        .subscribe(new Action1<List<VideoDownLoadEntity>>() {
                            @Override
                            public void call(List<VideoDownLoadEntity> videoDownLoadEntities) {
                                for (VideoDownLoadEntity item : videoDownLoadEntities) {

                                }
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {

                            }
                        });

            }
        });
    }


    /**
     * 优酷测试
     */
    private void getYouKu() {

        String videoId = YouKu.getVideoId("http://v.youku.com/v_show/id_XODMxNzI4MjQ4.id_XODMxNzI4MjQ4html?from=y1.3-movie-grid-1095-9921.202960.1-1");
        youKuImpl.getYouKuData(videoId).subscribe(new Action1<YouKuEntity>() {
            @Override
            public void call(YouKuEntity youKuEntity) {
                adapter.setDatas(youKuEntity.getData().getStream());
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }


    private void getLeTv() {
//        RequestParams params = new RequestParams(Letv.getApiUrl(Letv.getVideoId("http://www.letv.com/ptv/vplay/21832160.html")));
//        params.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36 LBBROWSER");
//        params.addHeader("Connection", "close");
//        params.addHeader("Accept-Encoding", "identity");
//        x.http().get(params, new Callback.CommonCallback<LetvEntity>() {
//            @Override
//            public void onSuccess(LetvEntity result) {
//
//                if (result.getStatuscode() == 1001) {
//                    LogUtil.w(result.getPlayurl().getTitle());
//                } else {
//                    LogUtil.e("第一次请求失败，" + new Gson().toJson(result));
//                }
//
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                LogUtil.e("第一次请求失败", ex);
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//        });
    }

}
