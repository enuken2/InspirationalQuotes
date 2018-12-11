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

/**
 * Main class for our UI design.
 */
public final class MainActivity extends AppCompatActivity {
    /** Default logging tag for messages from the main activity. */
    private static final String TAG = "FinalProject:Main";

    /** Request queue for our API requests. */
    private static RequestQueue requestQueue;

    /** Request queue for our API requests. */
    private TextView quoteText;
    private ImageView imageView;
    /** Request queue for our API requests. */
    private TextView authorText;
    /** Request queue for our API requests. */
    private Button quoteOfTheDay;
    /** Request queue for our API requests. */
    private ImageButton nextPage;

    /**
     * Run when this activity comes to the foreground.
     *
     * @param savedInstanceState unused
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up the queue for our API requests
        requestQueue = Volley.newRequestQueue(this);

        setContentView(R.layout.activity_main);

        quoteText = findViewById(R.id.quote);
        authorText = findViewById(R.id.author);
        imageView = findViewById(R.id.imageView);

        quoteOfTheDay = findViewById(R.id.qOD);
        quoteOfTheDay.setOnClickListener(v -> {
            Log.d(TAG, "Quote of the Day Button");
            quoteOfTheDay("");
        });

        nextPage = findViewById(R.id.nextPage);
        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity3();
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
     * Make a call for the quote of the day
     *
     * @param category IP address to look up
     */

    //theysaidso.com/api/#
    void quoteOfTheDay(final String category) {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    "https://quotes.rest/qod" + category + ".json",
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            quoteOfDayPrint(response);
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
     * Handle the response from our quote of the Day API.
     *
     * @param response response from our quote of the day API.
     */
    void quoteOfDayPrint(final JSONObject response) {
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

    public void openActivity3(){
        Intent intent = new Intent(this, Main3Activity.class);
        startActivity(intent);
    }

}
