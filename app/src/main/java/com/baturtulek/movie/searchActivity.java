package com.baturtulek.movie;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
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

public class searchActivity extends AppCompatActivity {

    String query;
    private EditText edt;

    //JSON KEY
    private static final String API_KEY = "22d24e5aa0add7939e390c9abfae118f";
    private ArrayList<Movie> movieList = new ArrayList<>();

    //JSON
    private JSONArray movies;
    private JSONObject movie;
    private RequestQueue mRequestQueue;

    //Recycler View
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerAdapter mRecyclerAdapter;

    //JSON REQUEST CODE
    public static final String TAG_ID = "id";
    public static final String TAG_NAME = "title";
    public static final String TAG_VOTE_AVG = "vote_average";
    public static final String TAG_POPULARITY = "popularity";
    public static final String TAG_VOTE_CNT = "vote_count";
    public static final String TAG_LANG = "original_language";
    public static final String TAG_ADULT = "adult";
    public static final String TAG_PLOT = "overview";
    public static final String TAG_IMG = "backdrop_path";

    //Gesture
    private GestureDetectorCompat mDetector;

    //Navigation Controller
    BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent1 = new Intent(searchActivity.this, MainActivity.class);
                    startActivity(intent1);
                    //finish();
                    return true;

                case R.id.navigation_search:
                    return true;

                case R.id.navigation_discover:
                    Intent intent2 = new Intent(searchActivity.this, discoverActivity.class);
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
        setContentView(R.layout.activity_search);

        //Navigation
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_search);

        edt = findViewById(R.id.queryText);

        //Gesture
        mDetector = new GestureDetectorCompat(this, new OnSwipeTouchListener());

        //Recycler View
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //Request Queue
        mRequestQueue = Volley.newRequestQueue(this);

    }
    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public void onClick(View view){
        query = edt.getText().toString().trim();

        if(query.equalsIgnoreCase("")) {
            Toast.makeText(searchActivity.this, "Enter a movie name !", Toast.LENGTH_LONG).show();
        }else{
            String MOVIES_URL = "https://api.themoviedb.org/3/search/movie?query=" + query + "&page=1&api_key=" + API_KEY;
            Log.d("URL-M", MOVIES_URL);
            //Json Request
            JsonObjectRequest mJsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET, MOVIES_URL, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            movie = response;
                            Log.d("Movies", movie.toString());
                            movieList.clear();
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
    }

    class OnSwipeTouchListener implements GestureDetector.OnGestureListener {
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;
        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }
        @Override
        public void onShowPress(MotionEvent e) { }
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }
        @Override
        public void onLongPress(MotionEvent e) {}
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                        result = true;
                    }
                }
                else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeBottom();
                    } else {
                        onSwipeTop();
                    }
                    result = true;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
        public void onSwipeRight() {}
        public void onSwipeLeft() {
            Intent i1 = new Intent(searchActivity.this, MainActivity.class);
            startActivity(i1);
        }
        public void onSwipeTop() {}
        public void onSwipeBottom() {}
    }

    //Async Task
    private class getMovies extends AsyncTask<Void,Void,Void> {

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
                    String img = m.getString(TAG_IMG);

                    Movie m1 = new Movie(id,name,voteCount,voteAvg,popularity,original_language,isAdult,plot,img);
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
                mRecyclerAdapter = new RecyclerAdapter(searchActivity.this, movieList);
                mRecyclerView.setAdapter(mRecyclerAdapter);
            }else{
                Toast.makeText(searchActivity.this, "Not Found", Toast.LENGTH_LONG).show();
            }
        }
    }
}
