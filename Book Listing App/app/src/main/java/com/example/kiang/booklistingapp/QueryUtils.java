package com.example.kiang.booklistingapp;

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

public final class QueryUtils {
    public static final String LOG_TAG = MainActivity.class.getName();

    private QueryUtils() {
        //Empty and private because no one should create a QueryUtils object.
    }

    public static List<Book> fetchBookData(String requestURL) {
        URL queryURL = createUrl(requestURL);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(queryURL);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }
        List<Book> books = extractFeatureFromJson(jsonResponse);
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

    private static List<Book> extractFeatureFromJson(String bookJson) {
        // Return early if JSON string is empty.
        if (TextUtils.isEmpty(bookJson)) {
            return null;
        }
        // Create an empty ArrayList to start adding Book objects.
        List<Book> books = new ArrayList<>();
        // Parse the JSON response and watch out for JSONException.
        try {
            JSONObject baseJsonResponse = new JSONObject(bookJson);
            JSONArray bookArray = baseJsonResponse.getJSONArray("items");
            for (int i = 0; i < bookArray.length(); i++) {
                JSONObject currentBook = bookArray.getJSONObject(i);
                JSONObject volumeInfo = currentBook.getJSONObject("volumeInfo");
                // Extract key values and store them.
                String title = volumeInfo.getString("title");
                // Start with an empty string for authors in case the JSON doesn't return any.
                String authors = "";
                if (volumeInfo.has("authors")) {
                    JSONArray authorsArray = volumeInfo.getJSONArray("authors");
                    if (authorsArray.length() > 0) {
                        for (int j = 0; j < authorsArray.length(); j++) {
                            authors += authorsArray.getString(j);
                            if (j < authorsArray.length() - 1) {
                                authors += ", ";
                            }
                        }
                    }
                } else {
                    authors = "No author(s) available";
                }
                // Start with an empty String for description in case the JSON doesn't return any.
                String description = "No description available for this book.";
                if (volumeInfo.has("description")) {
                    description = volumeInfo.getString("description");
                }
                // Parsing thumbnail URL into a Bitmap
                String thumbnailUrl = null;
                Bitmap bitmap = null;
                if (volumeInfo.has("imageLinks")) {
                    JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                    thumbnailUrl = imageLinks.getString("thumbnail");
                    try {
                        InputStream bitmapStream = new URL(thumbnailUrl).openStream();
                        bitmap = BitmapFactory.decodeStream(bitmapStream);
                    } catch (Exception e) {
                        Log.e(LOG_TAG, "Error parsing the Bitmap");
                    }
                } // If no "imageLinks" value is found, set default cover from the Google Books resources.
                else {
                    thumbnailUrl = "https://books.google.com/googlebooks/images/no_cover_thumb.gif";
                    try {
                        InputStream bitmapStream = new URL(thumbnailUrl).openStream();
                        bitmap = BitmapFactory.decodeStream(bitmapStream);
                    } catch (Exception e) {
                        Log.e(LOG_TAG, "Error parsing the Bitmap");
                    }
                }

                double rating = 0.0;
                if (volumeInfo.has("averageRating")) {
                    rating = volumeInfo.getDouble("averageRating");
                }
                String url = volumeInfo.getString("infoLink");

                Book book = new Book(authors, description, rating, thumbnailUrl, title, url, bitmap);
                // Add the created Book to the ArrayList.
                books.add(book);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the JSON results", e);
        }
        return books;
    }
}
