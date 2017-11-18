package in.handmademess.cokestudioraaga;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MediaPlayerActivity extends AppCompatActivity {

    TextView tvArtistName, tvSongName;
    ImageView ivPlay, ivPrev, ivNext, ivPause;

    MediaPlayer mediaPlayer;

    private double startTime = 0;
    private double finalTime = 0;

    private Handler myHandler = new Handler();
    ;
    private int forwardTime = 5000;
    private int backwardTime = 5000;
    private SeekBar seekbar;
    public static int oneTimeOnly = 0;

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);
        actionBar = getSupportActionBar();
        actionBar.hide();

        tvArtistName = (TextView) findViewById(R.id.tvArtistName);
        tvSongName = (TextView) findViewById(R.id.tvSongName);
        ivPlay = (ImageView) findViewById(R.id.iv_play);
        ivPause = (ImageView) findViewById(R.id.iv_pause);
        ivNext = (ImageView) findViewById(R.id.iv_next);
        ivPrev = (ImageView) findViewById(R.id.iv_prev);
        seekbar = (SeekBar) findViewById(R.id.seekBar);
        seekbar.setClickable(false);
        seekbar.setProgress(0);

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            final String url = intent.getStringExtra("url");
            String song = intent.getStringExtra("song");
            String artist = intent.getStringExtra("artist");

            tvArtistName.setText(artist);
            tvSongName.setText(song);
            mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(url));

            ivPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   // Toast.makeText(getApplicationContext(), "Playing sound", Toast.LENGTH_SHORT).show();
                    if (mediaPlayer.isPlaying()){
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            mediaPlayer.stop();
                            mediaPlayer.reset();
                        }
                    });
                    mediaPlayer.start();
                    finalTime = mediaPlayer.getDuration();
                    startTime = mediaPlayer.getCurrentPosition();

                    if (oneTimeOnly == 0) {
                        seekbar.setMax((int) finalTime);
                        oneTimeOnly = 1;
                    }

                    seekbar.setProgress((int) startTime);
                    myHandler.postDelayed(UpdateSongTime, 100);
                    ivPlay.setVisibility(View.GONE);
                    ivPause.setVisibility(View.VISIBLE);

                }
            });

            ivPause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), "Pausing sound", Toast.LENGTH_SHORT).show();
                    mediaPlayer.pause();
                    ivPause.setVisibility(View.GONE);
                    ivPlay.setVisibility(View.VISIBLE);
                }
            });

            ivNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int temp = (int)startTime;

                    if((temp+forwardTime)<=finalTime){
                        startTime = startTime + forwardTime;
                        mediaPlayer.seekTo((int) startTime);
                        //Toast.makeText(getApplicationContext(),"You have Jumped forward 5 seconds",Toast.LENGTH_SHORT).show();
                    }else{
                        //Toast.makeText(getApplicationContext(),"Cannot jump forward 5 seconds",Toast.LENGTH_SHORT).show();
                    }
                }
            });

            ivPrev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int temp = (int)startTime;

                    if((temp-backwardTime)>0){
                        startTime = startTime - backwardTime;
                        mediaPlayer.seekTo((int) startTime);
                        //Toast.makeText(getApplicationContext(),"You have Jumped backward 5 seconds",Toast.LENGTH_SHORT).show();
                    }else{
                        //Toast.makeText(getApplicationContext(),"Cannot jump backward 5 seconds",Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.stop();
        mediaPlayer.reset();
        seekbar.setProgress(0);
        startTime =0;
        finalTime=0;
    }

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();

            seekbar.setProgress((int) startTime);
            myHandler.postDelayed(this, 100);
        }
    };
}
