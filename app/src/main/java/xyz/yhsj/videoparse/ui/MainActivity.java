package xyz.yhsj.videoparse.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import xyz.yhsj.event.OnItemClickListener;
import xyz.yhsj.videoparse.R;
import xyz.yhsj.videoparse.extractors.letv.Letv;
import xyz.yhsj.videoparse.extractors.youku.YouKu;
import xyz.yhsj.videoparse.extractors.youku.entity.SecurityEntity;
import xyz.yhsj.videoparse.extractors.youku.entity.StreamEntity;
import xyz.yhsj.videoparse.extractors.youku.entity.YouKuEntity;
import xyz.yhsj.videoparse.ui.adapter.VideoListAdapter;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private VideoListAdapter adapter;

    private SecurityEntity securityEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();

    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        adapter = new VideoListAdapter(recyclerView);

        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                getYouKu();

                Letv.getTkey(1456724440);

            }
        });


        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View itemView, int position) {
                StreamEntity streamEntity = YouKu.parseStreamEntity(adapter.getItem(position), securityEntity);
                RequestParams params = new RequestParams(streamEntity.getSegs().get(0).getDownlad_url());

                x.http().get(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        LogUtil.e(result);
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

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
        final String api_url = YouKu.getApiUrl(videoId);
        final String api_url2 = YouKu.getApiUrl2(videoId);

        RequestParams params = new RequestParams(api_url);
        params.addHeader("Referer", "http://static.youku.com/");
        params.addHeader("Cookie", "__ysuid=1447684637230HKz");

        x.http().get(params, new Callback.CommonCallback<YouKuEntity>() {
            @Override
            public void onSuccess(YouKuEntity result) {

                if (result.getData().getError() == null) {
                    securityEntity = result.getData().getSecurity();

                    RequestParams params = new RequestParams(api_url2);

                    x.http().get(params, new Callback.CommonCallback<YouKuEntity>() {
                        @Override
                        public void onSuccess(YouKuEntity result2) {
                            adapter.setDatas(result2.getData().getStream());
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {

                        }

                        @Override
                        public void onCancelled(CancelledException cex) {

                        }

                        @Override
                        public void onFinished() {

                        }
                    });
                } else {
                    Snackbar.make(fab, result.getData().getError().getNote(), Snackbar.LENGTH_LONG).show();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e(">>>>>>>", "", ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

}
