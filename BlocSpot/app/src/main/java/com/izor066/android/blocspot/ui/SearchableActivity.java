package com.izor066.android.blocspot.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import com.izor066.android.blocspot.R;
import com.izor066.android.blocspot.Yelp.YelpAPI;
import com.izor066.android.blocspot.api.model.PointOfInterest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by igor on 16/10/15.
 */
public class SearchableActivity extends AppCompatActivity {

    YelpAPI yelpAPI;
    String results;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        handleIntent(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            searchYelp(query, "London");

        }
    }


    private void searchYelp(final String query, final String location) {

        new AsyncTask<Void, Void, List<PointOfInterest>>() {

            @Override
            protected void onPreExecute() {
                yelpAPI = YelpAPI.instantitate();
            }

            @Override
            protected List<PointOfInterest> doInBackground(Void... params) {
                return parseJson(yelpAPI.searchForBusinessesByLocation(query, location));

            }

            @Override
            protected void onPostExecute(List<PointOfInterest> result) {
                ArrayList<PointOfInterest> pointsOfInterestToSend = new ArrayList<PointOfInterest>(result);
                Log.v("SEARCH", "Results: " + result);
                Intent intent = new Intent(SearchableActivity.this, MapSearchResults.class);
                intent.putParcelableArrayListExtra("points_of_interest", pointsOfInterestToSend);
                startActivity(intent);
            }

        }.execute();


    }


    private List<PointOfInterest> parseJson(String json) {
        List<PointOfInterest> pointsOfInterest = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray businesses = jsonObject.getJSONArray("businesses");
            for (int i = 0; i < businesses.length(); i++) {
                JSONObject business = businesses.getJSONObject(i);
                String title = business.getString("name");
                String address = (String) business.getJSONObject("location").getJSONArray("address").get(0) + ", " + (String) business.getJSONObject("location").getString("city");
                float latitude = (float) business.getJSONObject("location").getJSONObject("coordinate").getDouble("latitude");
                float longitude = (float) business.getJSONObject("location").getJSONObject("coordinate").getDouble("longitude");
                PointOfInterest pointOfInterest = new PointOfInterest(-1, title, address, latitude, longitude, "Unsorted");
                pointsOfInterest.add(pointOfInterest);

            }

        } catch (JSONException e) {
            Log.e("JSONExecption", String.valueOf(e));
        }

        return pointsOfInterest;
    }
}
