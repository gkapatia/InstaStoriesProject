package com.kaam.ka.kapatia.instastoriesproject;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public abstract class ApiUtil {

    private static final String BASE_API_URL = "https://instastoriesproject.firebaseio.com/.json";

    public static URL buildUrl(String query){
        URL url = null;
        Uri uri =  Uri.parse(BASE_API_URL).buildUpon()
                .build();
        try{
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getJson(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            InputStream stream = connection.getInputStream();
            Scanner scanner = new Scanner(stream);
            scanner.useDelimiter("\\A");
            boolean hasData = scanner.hasNext();
            if(hasData){
                return scanner.next();
            } else {
                return  null;
            }
        } catch (Exception ex){
            Log.d("Error",ex.toString());
            return null;
        } finally {
            connection.disconnect();
        }
    }

    public static ArrayList<Story> getStoriesFomJson(String jsonString){

        ArrayList<Story> stories = new ArrayList<>();
        try {
            JSONArray jsonStories = new JSONArray(jsonString);
            for (int i = 0; i<  jsonStories.length(); i++) {
                Log.d("Info", jsonStories.getJSONObject(i).toString());
                JSONObject jsonStory = jsonStories.getJSONObject(i);
                Story story = new Story(jsonStory.getString("thumbnail"),
                        jsonStory.getString("thumbnail"),
                        jsonStory.getString("first_name"),
                        jsonStory.getString("topic_name"),
                        jsonStory.getString("question_text"),
                        jsonStory.getString("video_url"));
                stories.add(story);
            }
        } catch (JSONException ex) {
            Log.d("Error",ex.toString());
        }
        return stories;
    }

}
