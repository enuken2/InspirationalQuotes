package com.example.ethan.inspirationalquotes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.StaticLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import com.squareup.picasso.Picasso;

public class Main3Activity extends AppCompatActivity {
    /** Default logging tag for messages from the main activity. */
    private static final String TAG = "FinalProject:Main2";

    /** Request queue for our API requests. */
    private static RequestQueue requestQueue;

    /** Request queue for our API requests. */
    private TextView quoteText;
    /** Request queue for our API requests. */
    private TextView authorText;
    private ImageView imageView;

    /** Request queue for our API requests. */
    private Button love;
    /** Request queue for our API requests. */
    private Button funny;
    /** Request queue for our API requests. */
    private Button sports;
    /** Request queue for our API requests. */
    private Button life;
    private ImageButton lastPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        // Set up the queue for our API requests
        requestQueue = Volley.newRequestQueue(this);

        quoteText = findViewById(R.id.quote);
        authorText = findViewById(R.id.author);
        imageView = findViewById(R.id.imageView2);

        love = findViewById(R.id.love);
        love.setOnClickListener(v -> {
            Log.d(TAG, "Love Button");
            categoryRequest("love");
        });

        funny = findViewById(R.id.fun);
        funny.setOnClickListener(v -> {
            Log.d(TAG, "Funny Button");
            categoryRequest("funny");
        });

        sports = findViewById(R.id.sport);
        sports.setOnClickListener(v -> {
            Log.d(TAG, "Sports Button");
            categoryRequest("sports");
        });

        life = findViewById(R.id.life);
        life.setOnClickListener(v -> {
            Log.d(TAG, "Life Button");
            categoryRequest("life");
        });

        lastPage = findViewById(R.id.lastPage);
        lastPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity1();
            }
        });
    }

    /**
     * Run when this activity is no longer visible.
     */
    @Override
    protected void onPause() {
        super.onPause();
    }


    /**
         * query the API to retrieve the JSON object.
         * @param category type of quote we want to retrieve.
         */
        void categoryRequest(final String category) {
            try {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.GET,
                        "http://quotes.rest/qod.json?category=" + category,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(final JSONObject response) {
                                System.out.println(response);
                                categoryPrint(response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(final VolleyError error) {
                        Log.e(TAG, error.toString());
                    }
                });

                jsonObjectRequest.setShouldCache(false);
                requestQueue.add(jsonObjectRequest);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * Handle the response from the category request.
         *
         * @param response response from our quote API.
         */
        void categoryPrint(final JSONObject response) {
            try {
                Log.d(TAG, response.toString(2));
                String quote = response.getJSONObject("contents").getJSONArray("quotes").getJSONObject(0).getString("quote");
                System.out.println(quote);
                String author = response.getJSONObject("contents").getJSONArray("quotes").getJSONObject(0).getString("author");
                String url = response.getJSONObject("contents").getJSONArray("quotes").getJSONObject(0).getString("background");
                System.out.println(url);
                quoteText.setText(quote);
                authorText.setText("~" + author);
                Picasso.get().load(url).into(imageView);
            } catch (JSONException ignored) { }
        }

        public void openActivity1(){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

}

