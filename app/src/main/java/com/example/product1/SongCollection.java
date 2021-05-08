package com.example.product1;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SongCollection {

    public static ArrayList<Song> songList = prepareSongs();
    private ArrayList<Song> shuffleListFull = PlaySongActivity.shuffleListFull;

    public SongCollection() {
        prepareSongs();
    }

    public Song searchByTitle(String title){
        Song song = null;

        for (int index=0; index < songList.size(); index++){
            song = songList.get(index);

            if (song.getTitle().equals(title)){
                return song;
            }
        }
        return song;
    }

    public static ArrayList<Song> prepareSongs() {
        ArrayList<Song> songList = new ArrayList<>();

        Song becauseOfYou = new Song("Because of You", "Kelly Clarkson",
                "54d2e5c13b2be3c1f5c98fb243c57391ea7c3d40?cid=2afe87a64b0042dabf51f37318616965",
                4.23, "https://i.scdn.co/image/ab67616d0000b273ec7178dacb8672fbbcef48c9");
        Song burnItDown = new Song("Burn It Down - One More Light Live", "Linkin Park",
                "aa721f6c42992305237a0c9bdce67559f23d4a7a?cid=2afe87a64b0042dabf51f37318616965",
                3.66, "https://i.scdn.co/image/ab67616d0000b273c0dde03a3c671cf75da4f436");

        Song galwayGirl = new Song("Galway Girl", "Ed Sheeran",
                "cec1fc40a0220f20d3b91dd28d8e1141ad5e7e25?cid=2afe87a64b0042dabf51f37318616965", 2.85,
                "https://i.scdn.co/image/ab67616d0000b273ba5db46f4b838ef6027e6f96");

        Song photograph = new Song("Photograph", "Ed Sheeran",
                "097c7b735ceb410943cbd507a6e1dfda272fd8a8?cid=2afe87a64b0042dabf51f37318616965",
                4.32, "https://i.scdn.co/image/ab67616d0000b273407981084d79d283e24d428e");

        Song setFireToTheRain = new Song("Set Fire to the Rain", "Adele",
                "70e56ff5287602e2720bec6838e51cb89fa2ce2d?cid=2afe87a64b0042dabf51f37318616965",
                4.03, "https://i.scdn.co/image/ab67616d0000b273ed19d0c720dbe4d1450cb98a");
        Song timeForTheMoonNight = new Song ("Time for the moon night", "GFRIEND",
                "9ac0b006d35ccca35bdb78d11576a1a6302d82a8?cid=2afe87a64b0042dabf51f37318616965",
                3.78, "https://i.scdn.co/image/ab67616d0000b273674f692fd1a34630b818b39b");

        songList.add(becauseOfYou);
        songList.add(burnItDown);
        songList.add(galwayGirl);
        songList.add(photograph);
        songList.add(setFireToTheRain);
        songList.add(timeForTheMoonNight);

        return songList;
    }

    public Song getPrevSong(String currentSongTitle) {
        Song song = null;
        for (int index = 0; index < shuffleListFull.size(); index++) {
            String tempSongTitle = shuffleListFull.get(index).getTitle();
            if (tempSongTitle.equals(currentSongTitle) && (index > 0)) {
                song = shuffleListFull.get(index - 1);
                break;
            }
        }
        return song;
    }

    public Song getNextSong(String currentSongTitle) {
        Song song = null;
        for (int index = 0; index < shuffleListFull.size(); index++) {
            String tempSongTitle = shuffleListFull.get(index).getTitle();
            if (tempSongTitle.equals(currentSongTitle) && (index < shuffleListFull.size()-1)) {
                song = shuffleListFull.get(index + 1);
                break;
            }
        }
        return song;
    }
}
