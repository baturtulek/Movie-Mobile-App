package com.baturtulek.movie;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    //JSON URL AND KEY
    private static final String API_KEY = "22d24e5aa0add7939e390c9abfae118f";
    private static final String MOVIES_URL = "https://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key="+API_KEY;

    private JSONArray movies;
    private JSONObject movie;
    private RequestQueue mRequestQueue;
    private ArrayList<Movie> movieList = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerAdapter mRecyclerAdapter;
    private BottomNavigationView a;
    //JSON REQUEST CODE
    public static final String TAG_ID = "id";
    public static final String TAG_NAME = "title";
    public static final String TAG_VOTE_AVG = "vote_average";
    public static final String TAG_POPULARITY = "popularity";
    public static final String TAG_VOTE_CNT = "vote_count";
    public static final String TAG_LANG = "original_language";
    public static final String TAG_ADULT = "adult";
    public static final String TAG_PLOT = "overview";

    BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;

                case R.id.navigation_search:
                    Intent intent1 = new Intent(MainActivity.this, searchActivity.class);
                    startActivity(intent1);
                    //finish();
                    return true;

                case R.id.navigation_discover:
                    Intent intent2 = new Intent(MainActivity.this, discoverActivity.class);
                    startActivity(intent2);
                    //finish();
                    return true;
            }
            return false;
        }

    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRequestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest mJsonObjectRequest = new JsonObjectRequest (
                Request.Method.GET, MOVIES_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        movie = response;
                        new getMovies().execute();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Result",
                        "ERROR JSONObject request" + error.toString());
            }
        });
        mRequestQueue.add(mJsonObjectRequest);

    }


    private class getMovies extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                movies = movie.getJSONArray("results");

                for (int i = 0; i < movies.length(); i++) {
                    JSONObject m = movies.getJSONObject(i);
                    int id = m.getInt(TAG_ID);
                    String name = m.getString(TAG_NAME);
                    int voteCount = m.getInt(TAG_VOTE_CNT);
                    Double voteAvg = m.getDouble(TAG_VOTE_AVG);
                    String popularity = m.getString(TAG_POPULARITY);
                    String original_language = m.getString(TAG_LANG);
                    boolean isAdult = m.getBoolean(TAG_ADULT);
                    String plot = m.getString(TAG_PLOT);

                    Movie m1 = new Movie(id,name,voteCount,voteAvg,popularity,original_language,isAdult,plot);
                    //Log.i("object1",m1.toString());
                    movieList.add(m1);
                }

            }
            catch (JSONException ee) {
                ee.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(movieList != null) {
                mRecyclerAdapter = new RecyclerAdapter(MainActivity.this, movieList);
                mRecyclerView.setAdapter(mRecyclerAdapter);
            }else{
                Toast.makeText(MainActivity.this, "Not Found", Toast.LENGTH_LONG).show();
            }
        }
    }


}

