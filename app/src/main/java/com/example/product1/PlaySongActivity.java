package com.example.product1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;

import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class PlaySongActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://p.scdn.co/mp3-preview/";
    private String title = "";
    private String artiste = "";
    private String fileLink = "";
    private Double songLength = null;
    private String coverArt = "";
    private String url = "";
    private SeekBar seekBar;
    private boolean shuffleBoolean=false;
    private boolean repeatBoolean = false;
    private boolean favouriteBoolean = false;

    private MediaPlayer player = null;
    private int musicPosition;
    private ImageView btnPlayPause, btnRepeat, btnShuffle, btnFav;
    private TextView txtPlayedLength;

    private SongCollection songCollection = new SongCollection();
    //private ArrayList<Song> songList = songCollection.songList;
    public static ArrayList<Song> favList = new ArrayList<Song>();
    public static ArrayList<Song> shuffleListFull = new ArrayList<Song>(SongCollection.songList);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);
        btnPlayPause = findViewById(R.id.btnPlayPause);
        btnShuffle = findViewById(R.id.btnShuffle);
        btnRepeat = findViewById(R.id.btnRepeat);
        btnFav = findViewById(R.id.btnFav);
        retrieveData();
        displaySong(title, artiste, coverArt, songLength);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);
        menu.findItem(R.id.search).setVisible(false); //remove search icon from PlaySongActivityScreen
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.sleep_timer:
                startActivity(new Intent(PlaySongActivity.this, SleepTimer.class));
                break;
            case R.id.settings:
                Toast.makeText(this,"Settings", Toast.LENGTH_SHORT).show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void displaySong(String title, String artiste, String coverArt, Double songLength) {
        TextView txtSongTitle = findViewById(R.id.txtSongTitle);
        txtSongTitle.setText(title);
        txtSongTitle.setSelected(true);

        TextView txtSongArtiste = findViewById(R.id.txtSongArtiste);
        txtSongArtiste.setText(artiste);

        TextView txtTotalLength = findViewById(R.id.txtTotalLength);
        txtTotalLength.setText(String.valueOf(songLength));

        ImageView imgCoverArt = findViewById(R.id.imgCoverArt);
        Picasso.with(this).load(coverArt).into(imgCoverArt);

        seekBar = findViewById(R.id.seekBar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(player != null && fromUser){
                    player.seekTo(progress*1000);
                    txtPlayedLength.setText(updateCurrentTime(progress/1000));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private String updateCurrentTime(int time) {
        String currentTime = "";
        int min = time / 60;
        int sec = time % 60;

        currentTime += min + ":";
        if(sec<10) currentTime += "0";
        currentTime += sec;

        return currentTime;
    }

    private void retrieveData() {
        Bundle songData = this.getIntent().getExtras();
        title = songData.getString("title");
        artiste = songData.getString("artiste");
        fileLink = songData.getString("fileLink");
        songLength = songData.getDouble("songLength");
        coverArt = songData.getString("coverArt");
        url = BASE_URL + fileLink;
    }

    public void playPrevious(View view){
        Song prevSong = songCollection.getPrevSong(title);
        if(prevSong != null){
            title = prevSong.getTitle();
            artiste = prevSong.getArtiste();
            songLength = prevSong.getSongLength();
            fileLink = prevSong.getFileLink();
            coverArt = prevSong.getCoverArt();

            url = BASE_URL + fileLink;
            displaySong(title, artiste, coverArt, songLength);
            stopActivities();
            playOrPauseMusic(view);
        }
    }

    public void playNext(View view){
        Song nextSong = songCollection.getNextSong(title);
        if(nextSong != null){
            title = nextSong.getTitle();
            artiste = nextSong.getArtiste();
            songLength = nextSong.getSongLength();
            fileLink = nextSong.getFileLink();
            coverArt = nextSong.getCoverArt();

            url = BASE_URL + fileLink;
            displaySong(title, artiste, coverArt, songLength);
            stopActivities();
            playOrPauseMusic(view);
        }
    }

    public void playOrPauseMusic(View view) {
        if(player == null){
            preparePlayer();
        }
        if(!player.isPlaying()){
            player.start();
            btnPlayPause.setImageResource(R.drawable.ic_pause_black);
            final Handler handler = new Handler();
            txtPlayedLength = findViewById(R.id.txtPlayedLength);

            // to update seekbar and played length
            PlaySongActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (player != null){
                        int mCurrentPosition = player.getCurrentPosition()/1000;
                        seekBar.setProgress(mCurrentPosition);
                        txtPlayedLength.setText(updateCurrentTime(mCurrentPosition));
                    }
                    handler.postDelayed(this, 1000);
                }
            });
            stopWhenMusicEnds();
        }
        else{
            pauseMusic();
        }
    }

    private void preparePlayer() {
        player = new MediaPlayer();

        try{
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setDataSource(url);
            player.prepare();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    private void pauseMusic() {
        player.pause();
        musicPosition = player.getCurrentPosition();
        btnPlayPause.setImageResource(R.drawable.ic_play_black);
    }

    private void stopWhenMusicEnds() {
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (repeatBoolean) {
                    playOrPauseMusic(null);
                } else {
                    stopActivities();
                }
            }
        });
    }

    private void stopActivities() {
        if (player!=null){
            btnFav.setImageResource(R.drawable.ic_heart_black);
            btnPlayPause.setImageResource(R.drawable.ic_play_black);
            musicPosition=0;
            player.stop();
            player.release();
            player = null;
        }
    }


    public void shuffleMusic(View view){
        if(shuffleBoolean){
            btnShuffle.setImageResource(R.drawable.ic_shuffle_black);
            shuffleListFull = SongCollection.songList; //use copy of originally ordered songs
        }
        else{
            btnShuffle.setImageResource(R.drawable.ic_shuffle_blue);
            Collections.shuffle(shuffleListFull);
            System.out.println("Original list: " + SongCollection.songList);
            System.out.println("Shuffled list: " + shuffleListFull);
            /*ArrayList shuffledArray = songCollection.prepareSongs();
            *//**//*shuffledArray.clear(); // clear currently ordered song list
            shuffledArray.addAll(shuffleList); // add shuffled song list into array list*//**//*
*/        }
        shuffleBoolean = !shuffleBoolean;
    }

    public void repeatMusic(View view){
        if(repeatBoolean){
            btnRepeat.setImageResource(R.drawable.ic_repeat_black);
        }
        else{
            btnRepeat.setImageResource(R.drawable.ic_repeat_blue);
        }
        repeatBoolean = !repeatBoolean;
    }

    public void addToFavourites (View view){
        Song song = songCollection.searchByTitle(title);
        if(favList.contains(song)){
            btnFav.setImageResource(R.drawable.ic_heart_black);
            favList.remove(song);
            Toast.makeText(this, "Song removed from favourites", Toast.LENGTH_SHORT).show();
        }
        else {
            btnFav.setImageResource(R.drawable.ic_heart_pink);
            favList.add(song);
            Toast.makeText(this, "Song added to favourites", Toast.LENGTH_SHORT).show();
        }
        favouriteBoolean = !favouriteBoolean;
    }

    }