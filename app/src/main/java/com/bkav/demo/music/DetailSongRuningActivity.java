package com.bkav.demo.music;

import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DetailSongRuningActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mHinhDanhSach;
    private ImageView mPopupMenu;
    private ImageView mLike;
    private ImageView mPrevious;
    private ImageView mPlayStart;
    private ImageView mNext;
    private ImageView mDisLike;
    private ImageView mHinhCaSy;
    private TextView mTenCaSy;
    private TextView mTenBaiHat;
    private TextView mTimeStart;
    private TextView mTimeAll;
    private SeekBar mSeekBar;
    private Intent intent;
    private Bundle bundle;
    private int mLocaltionSong;
    private ArrayList<String> arrList;
    private Uri uri;
    private MediaPlayer mediaPlayer;
    private int time,t=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_song_runing);
        intent = getIntent();
        initView();
        getDataPush();
        backHome();

    }

    private void initView() {
        mHinhDanhSach = (ImageView) findViewById(R.id.danhsach);
        mPopupMenu = (ImageView) findViewById(R.id.popup_song);
        mLike = (ImageView) findViewById(R.id.like);
        mDisLike = (ImageView) findViewById(R.id.dis_like);
        mPrevious = (ImageView) findViewById(R.id.previous);
        mPlayStart = (ImageView) findViewById(R.id.pause_play);
        mNext = (ImageView) findViewById(R.id.next);
        mHinhCaSy = (ImageView) findViewById(R.id.hinh_casy);

        mTenCaSy = (TextView) findViewById(R.id.tencasy);
        mTenBaiHat = (TextView) findViewById(R.id.tenbaihat);
        mTimeStart = (TextView) findViewById(R.id.time_start);
        mTimeAll = (TextView) findViewById(R.id.time_all);

        mSeekBar = (SeekBar) findViewById(R.id.seekbar);

        mPlayStart.setOnClickListener(this);
        mPrevious.setOnClickListener(this);
        mNext.setOnClickListener(this);

    }

    private void backHome() {
        mHinhDanhSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(DetailSongRuningActivity.this,MainActivity.class);
                i.putExtra("time",t);
                setResult(000,i);
                if (mediaPlayer.isPlaying()) mediaPlayer.pause();
                finish();
            }
        });

    }

    private void getDataPush() {
        bundle = intent.getBundleExtra("dulieu");
        mLocaltionSong = bundle.getInt("vitri");
        arrList = bundle.getStringArrayList("tenbai");
        time=bundle.getInt("thoigian");
        playSongLocaltion();
        updateSong();
        updateTime();
        runSeekbar();
        allTimeSong();

    }



    private void runSeekbar() {
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b){
                    mediaPlayer.seekTo(i);
                    mSeekBar.setProgress(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
//                if (mTimeStart.equals(simpleDateFormat.format(mediaPlayer.getCurrentPosition() + ""))) {
//                    mLocaltionSong += 1;
//                    playSongLocaltion();
//                    mPlayStart.setImageResource(R.drawable.ic_play_black);
//                    allTimeSong();
//                    updateTime();
//
//                }
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                    while (mediaPlayer != null){
                        try{
                            Message msg = new Message();
                            msg.what = mediaPlayer.getCurrentPosition();
                            handler.sendMessage(msg);

                            Thread.sleep(1000);

                        } catch (InterruptedException e){}

                    }
            }
        }).start();

    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            mSeekBar.setProgress(msg.what);
        }
    };

    private void playSongLocaltion() {
        uri = Uri.parse(arrList.get(mLocaltionSong).toString());
        mediaPlayer=new MediaPlayer();
        mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
        mediaPlayer.seekTo(time);
        mediaPlayer.start();
//        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mediaPlayer) {
//
//            }
//        });   TODO: để nhạc chạy bài tiếp theo khi hết nhạc

        updateSong();

    }


    private void playSongLocaltion1() {
        uri = Uri.parse(arrList.get(mLocaltionSong).toString());
        mediaPlayer = new MediaPlayer();
        mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
        mediaPlayer.start();

        updateSong();
    }

        private void allTimeSong() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        mTimeAll.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
        mSeekBar.setMax(mediaPlayer.getDuration());


    }

    private void updateTime() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                mTimeStart.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                mSeekBar.setProgress(mediaPlayer.getCurrentPosition());
                t=mediaPlayer.getCurrentPosition();
                handler.postDelayed(this, 500);
            }
        }, 100);


    }

    private void updateSong() {

        MediaMetadataRetriever retriever = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.GINGERBREAD_MR1) {
            retriever = new MediaMetadataRetriever();
            retriever.setDataSource(arrList.get(mLocaltionSong));

            String tenAlbum = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
            String ten = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);

            mTenBaiHat.setText(ten);
            mTenCaSy.setText(tenAlbum);
            mHinhCaSy.setImageResource(R.drawable.anhtho);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.previous:
                mLocaltionSong -= 1;
                if (mLocaltionSong < 0) {
                    mLocaltionSong = arrList.size() - 1;
                } else {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                    }
                    playSongLocaltion1();
                    //mPrevious.setImageResource(R.drawable.ic_fab_play_btn_normal);
                    allTimeSong();
                    updateTime();
                }
                break;

            case R.id.pause_play:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    mPlayStart.setImageResource(R.drawable.ic_fab_play_btn_normal);
                } else {
                    mediaPlayer.start();

                }
//               // mPlayStart.setImageResource(R.drawable.ic_fab_play_btn_normal);

                allTimeSong();
                updateTime();
                break;

            case R.id.next:
                mLocaltionSong += 1;
                if (mLocaltionSong > arrList.size() - 1) {
                    mLocaltionSong = 0;
                }
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                playSongLocaltion1();
               // mNext.setImageResource(R.drawable.ic_fab_play_btn_normal);
                allTimeSong();
                updateTime();
                break;


        }
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(DetailSongRuningActivity.this,MainActivity.class);
        i.putExtra("time",t);
        setResult(000,i);
        if (mediaPlayer.isPlaying()) mediaPlayer.pause();
        finish();
        super.onBackPressed();
    }
}
