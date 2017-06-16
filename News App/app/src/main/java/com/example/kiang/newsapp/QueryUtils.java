package com.example.kiang.newsapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.thumbnail;

public final class QueryUtils {
    public static final String LOG_TAG = MainActivity.class.getName();

    private QueryUtils() {
        //Empty and private because no one should create a QueryUtils object.
    }

    public static List<News> fetchBookData(String requestURL) {
        URL queryURL = createUrl(requestURL);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(queryURL);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }
        List<News> books = extractFeatureFromJson(jsonResponse);
        return books;
    }


    public static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error creating URL", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        // If URL is null, return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            // If the response was OK (response code = 200), read stream and parse response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem receiving JSON results", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<News> extractFeatureFromJson(String articleJsonStr) {
        // Return early if JSON string is empty.
        if (TextUtils.isEmpty(articleJsonStr)) {
            return null;
        }
        // Create an empty ArrayList to start adding Book objects.
        List<News> news = new ArrayList<>();
        // Parse the JSON response and watch out for JSONException.
        try {
            JSONObject articlesJson = new JSONObject(articleJsonStr);
            JSONObject responseJson = articlesJson.getJSONObject("response");
            JSONArray resultsJsonArray = responseJson.getJSONArray("results");
            Bitmap bitmap = null;
            String webUrl;

            // Parsing thumbnail URL into a Bitmap
            for (int i = 0; i < responseJson.length(); i++) {
                JSONObject articleJson = resultsJsonArray.getJSONObject(i);
                String title = articleJson.getString("webTitle");
                webUrl = articleJson.getString("webUrl");
                String thumbnailUrl = null;
                if (articleJson.has("fields")) {
                    JSONObject fieldsJson = articleJson.getJSONObject("fields");
                    if (fieldsJson.has("thumbnail")) {
                        thumbnailUrl = fieldsJson.getString("thumbnail");
                    }
                }else{
                    thumbnailUrl = "http://ctt.trains.com/sitefiles/images/no-preview-available.png";
                }
                try {
                    Log.v(LOG_TAG, "bitmap");
                    URL url = new URL(thumbnailUrl);
                    bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Error", e);
                }


                news.add(new News(title, bitmap, webUrl));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the JSON results", e);
        }
        return news;
    }
}
