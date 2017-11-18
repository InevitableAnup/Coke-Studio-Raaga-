package in.handmademess.cokestudioraaga.Parser;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import in.handmademess.cokestudioraaga.Model.SongsInfo;

/**
 * Created by Anup on 18-11-2017.
 */

public class ParseStudioSongs {

    public static final String KEY_SONG="song";
    public static final String KEY_URL="url";
    public static final String KEY_ARTISTS="artists";
    public static final String KEY_IMAGE="cover_image";

    private String mJson;
    public SongsInfo songsObject;
    public String[] cover_image,redirectedImage,song,url,artists;

    public ParseStudioSongs(String json) {
        this.mJson = json;
    }

    public void parseJson(){
        try {
            final JSONArray songsArray = new JSONArray(mJson);

            song = new String[songsArray.length()];
            url=new String[songsArray.length()];
            artists=new String[songsArray.length()];
            cover_image=new String[songsArray.length()];
            redirectedImage=new String[songsArray.length()];

            for (int i=0;i< songsArray.length(); i++) {
                JSONObject studioSongsObject = songsArray.getJSONObject(i);

                song[i] = studioSongsObject.getString(KEY_SONG);
                url[i] = studioSongsObject.getString(KEY_URL);
                artists[i] = studioSongsObject.getString(KEY_ARTISTS);
                cover_image[i] = studioSongsObject.getString(KEY_IMAGE);



            }



        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    public ArrayList<SongsInfo> prepareSongsList() {
        ArrayList songs_ver = new ArrayList<>();
        if (song.length == 0) {
            Log.d("NOSONG INFOAVAILABLE", "NOSONG INFOAVAILABLE");
        } else {
            for (int i = 0; i < song.length; i++) {

                SongsInfo songsInfo = new SongsInfo();
                songsInfo.setSong(song[i]);
                songsInfo.setUrl(url[i]);
                songsInfo.setArtists(artists[i]);
                songsInfo.setCover_image(cover_image[i]);

                songs_ver.add(songsInfo);
            }
        }


        return songs_ver;
    }



}


