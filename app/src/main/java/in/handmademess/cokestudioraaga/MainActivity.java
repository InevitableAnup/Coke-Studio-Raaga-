package in.handmademess.cokestudioraaga;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

import in.handmademess.cokestudioraaga.Adapter.CokeStudioAdapter;
import in.handmademess.cokestudioraaga.Model.SongsInfo;
import in.handmademess.cokestudioraaga.Parser.ParseStudioSongs;

public class MainActivity extends AppCompatActivity {

    private static final String GET_SONGS = "http://starlord.hackerearth.com/cokestudio";
    RecyclerView recyclerView;
    private GridLayoutManager mLayoutManager;

    CokeStudioAdapter adapter;
    ArrayList<SongsInfo> mSongsInfoArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSongsInfoArrayList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.songs_recyclerView);
        mLayoutManager = new GridLayoutManager(MainActivity.this, numberOfColumns());
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, numberOfColumns()));
        adapter = new CokeStudioAdapter(MainActivity.this, mSongsInfoArrayList);
        if (isNetworkAvailable()) {

            getSongs();
        } else {
            Toast.makeText(this, "Please connect to the internet before continuing.", Toast.LENGTH_SHORT).show();
        }
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // You can change this divider to adjust the size of the poster
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 2;
        return nColumns;
    }


    //Get the list of songs from http://starlord.hackerearth.com/cokestudio
    public void getSongs() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, GET_SONGS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String resp = response.toString().trim();
                Log.d("StudioResp", resp);

                ParseStudioSongs parseStudioSongs = new ParseStudioSongs(resp);
                parseStudioSongs.parseJson();
                ArrayList<SongsInfo> ListOfSongs = parseStudioSongs.prepareSongsList();

                adapter = new CokeStudioAdapter(MainActivity.this, ListOfSongs);
                recyclerView.setAdapter(adapter);

                recyclerView.setHasFixedSize(true);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError) {
                    Toast.makeText(MainActivity.this, "Please connect to the internet before continuing", Toast.LENGTH_SHORT).show();
                }

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
