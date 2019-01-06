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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
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

public class discoverActivity extends AppCompatActivity {

    Spinner spinner;

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

    //Default Genre
    int genre = 0;

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
                    Intent intent1 = new Intent(discoverActivity.this, MainActivity.class);
                    startActivity(intent1);
                    //finish();
                    return true;

                case R.id.navigation_search:
                    Intent intent2 = new Intent(discoverActivity.this, searchActivity.class);
                    startActivity(intent2);
                    //finish();
                    return true;

                case R.id.navigation_discover:
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);

        //Navigation Controller
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_discover);

        //Gesture
        mDetector = new GestureDetectorCompat(this, new OnSwipeTouchListener());

        //Recycler View
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRequestQueue = Volley.newRequestQueue(this);

        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    genre = 28;
                } else if (position == 1) {
                    genre = 12;
                } else if (position == 2) {
                    genre = 16;
                } else if (position == 3) {
                    genre = 35;
                } else if (position == 4) {
                    genre = 80;
                } else if (position == 5) {
                    genre = 99;
                } else if (position == 6) {
                    genre = 18;
                } else if (position == 7) {
                    genre = 10751;
                } else if (position == 8) {
                    genre = 14;
                } else if (position == 9) {
                    genre = 36;
                } else if (position == 10) {
                    genre = 17;
                } else if (position == 11) {
                    genre = 10402;
                } else if (position == 12) {
                    genre = 9648;
                } else if (position == 13) {
                    genre = 10749;
                } else if (position == 14) {
                    genre = 878;
                } else if (position == 15) {
                    genre = 10770;
                } else if (position == 16) {
                    genre = 53;
                } else if (position == 17) {
                    genre = 10752;
                } else if (position == 18) {
                    genre = 37;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public void onClick(View view) {
        String MOVIES_URL = "https://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&with_genres=" + genre + "&page=1&api_key=" + API_KEY;
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

    class OnSwipeTouchListener implements GestureDetector.OnGestureListener {
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;
        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }
        @Override
        public void onShowPress(MotionEvent e) {

        }
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
        public void onSwipeRight() {
            Intent i1 = new Intent(discoverActivity.this, MainActivity.class);
            startActivity(i1);
        }
        public void onSwipeLeft() {}
        public void onSwipeTop() {}
        public void onSwipeBottom() {}
    }

    //Async Task
    private class getMovies extends AsyncTask<Void, Void, Void> {
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
            } catch (JSONException ee) {
                ee.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (movieList != null) {
                mRecyclerAdapter = new RecyclerAdapter(discoverActivity.this, movieList);
                mRecyclerView.setAdapter(mRecyclerAdapter);
            } else {
                Toast.makeText(discoverActivity.this, "Not Found", Toast.LENGTH_LONG).show();
            }
        }
    }
}
