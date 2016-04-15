package xyz.yhsj.videoparse.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;
import xyz.yhsj.event.OnItemClickListener;
import xyz.yhsj.videoparse.R;
import xyz.yhsj.videoparse.extractors.letv.Letv;
import xyz.yhsj.videoparse.extractors.letv.entity.LetvEntity;
import xyz.yhsj.videoparse.extractors.letv.impl.LetvDataImpl;
import xyz.yhsj.videoparse.extractors.youku.YouKu;
import xyz.yhsj.videoparse.extractors.youku.entity.VideoDownLoadEntity;
import xyz.yhsj.videoparse.extractors.youku.entity.YouKuEntity;
import xyz.yhsj.videoparse.extractors.youku.impl.YouKuImpl;
import xyz.yhsj.videoparse.ui.adapter.YouKuVideoListAdapter;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    private YouKuVideoListAdapter adapter;

    private YouKuImpl youKuImpl;
    private LetvDataImpl letvImpl;

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
        letvImpl = new LetvDataImpl();

        adapter = new YouKuVideoListAdapter(recyclerView);

        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //getYouKu();

                getLetv();


            }
        });


        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View itemView, int position) {

                youKuImpl.getVideoDownloadUrl(adapter.getItem(position).getSegs().get(0).getDownlad_url())
                        .subscribe(new Action1<List<VideoDownLoadEntity>>() {
                            @Override
                            public void call(List<VideoDownLoadEntity> videoDownLoadEntities) {
                                for (VideoDownLoadEntity item : videoDownLoadEntities) {
                                    System.out.println(item.getServer());
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


    private void getLetv() {

        letvImpl.getLetvData(Letv.getVideoId("http://www.letv.com/ptv/vplay/21832160.html")).subscribe(new Action1<LetvEntity>() {
            @Override
            public void call(LetvEntity letvEntity) {
                if (letvEntity.getStatuscode() == 1001) {
                    System.out.println(letvEntity.getPlayurl().getTitle());
                } else {
                    System.out.println("第一次请求失败，" + new Gson().toJson(letvEntity));
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }

}
