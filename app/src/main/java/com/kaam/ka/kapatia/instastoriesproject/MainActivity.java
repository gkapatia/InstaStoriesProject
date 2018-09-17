package com.kaam.ka.kapatia.instastoriesproject;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static com.kaam.ka.kapatia.instastoriesproject.ApiUtil.getStoriesFomJson;

public class MainActivity extends AppCompatActivity {

    private ProgressBar mLoadingProgress;
    private RecyclerView rvStories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        initializePlayer();
        setContentView(R.layout.activity_main);
        mLoadingProgress = (ProgressBar) findViewById(R.id.pb_loading);
        rvStories = (RecyclerView) findViewById(R.id.rv_story);

        LinearLayoutManager storiesLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        rvStories.setLayoutManager(storiesLayoutManager);

        try{
            URL url = ApiUtil.buildUrl("");
            new QueryTask().execute(url);

        } catch (Exception e){
            Log.e("Error",e.toString());
        }

    }

//    private void initializePlayer(){
//        // Create a default TrackSelector
//        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
//        TrackSelection.Factory videoTrackSelectionFactory =
//                new AdaptiveTrackSelection.Factory(bandwidthMeter);
//        TrackSelector trackSelector =
//                new DefaultTrackSelector(videoTrackSelectionFactory);
//
//        //Initialize the player
//        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
//
//        //Initialize simpleExoPlayerView
//        SimpleExoPlayerView simpleExoPlayerView = findViewById(R.id.exoplayer);
//        simpleExoPlayerView.setPlayer(player);
//
//        // Produces DataSource instances through which media data is loaded.
//        DataSource.Factory dataSourceFactory =
//                new DefaultDataSourceFactory(this, Util.getUserAgent(this, "CloudinaryExoplayer"));
//
//        // Produces Extractor instances for parsing the media data.
//        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
//
//        // This is the MediaSource representing the media to be played.
//        Uri videoUri = Uri.parse("any Cloudinary URL");
//        MediaSource videoSource = new ExtractorMediaSource(videoUri,
//                dataSourceFactory, extractorsFactory, null, null);
//
//        // Prepare the player with the source.
//        player.prepare(videoSource);
//
//    }

    public class QueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            URL url = urls[0];
            String result = null;
            try {
                result = ApiUtil.getJson(url);
            } catch (Exception e){
                Log.e("Error",e.toString());
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result){
            TextView errorTextBox = (TextView) findViewById(R.id.error_text);
            ArrayList<Story> stories = getStoriesFomJson(result);
            if(result == null){
                rvStories.setVisibility(View.INVISIBLE);
                errorTextBox.setVisibility(View.VISIBLE);
            } else {

                rvStories.setVisibility(View.VISIBLE);
                errorTextBox.setVisibility(View.INVISIBLE);
            }
            mLoadingProgress.setVisibility(View.INVISIBLE);

            StoriesAdapter adapter = new StoriesAdapter(stories);
            rvStories.setAdapter(adapter);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingProgress.setVisibility(View.VISIBLE);
        }
    }
}
