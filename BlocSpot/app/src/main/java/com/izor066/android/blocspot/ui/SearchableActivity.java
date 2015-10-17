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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
                // ToDo: Parse Results
            }

            @Override
            protected void onPostExecute(List<PointOfInterest> result) {
                //TODO result is List<PointOfInterest>
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
        JSONParser parser = new JSONParser();
        JSONObject response = null;
        try {
            response = (JSONObject) parser.parse(json);
        } catch (ParseException pe) {
            Log.e("json", "Error: could not parse JSON response:");
            Log.e("json", json);
        }

        JSONArray businesses = (JSONArray) response.get("businesses");
        for (int i = 0; i < businesses.size(); i++) {
            JSONObject business = (JSONObject) businesses.get(i);
            String title = (String) business.get("name");
            JSONObject location = (JSONObject) business.get("location");
            String address = location.get("address").toString();
            float latitude = (float)(double)((JSONObject)location.get("coordinate")).get("latitude");
            float longitude = (float)(double)((JSONObject)location.get("coordinate")).get("longitude");
            PointOfInterest pointOfInterest = new PointOfInterest(-1, title, address, latitude, longitude, "Unsorted");
            pointsOfInterest.add(pointOfInterest);
        }

        return pointsOfInterest;
    }
}
