package com.izor066.android.blocspot.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import com.izor066.android.blocspot.R;
import com.izor066.android.blocspot.Yelp.YelpAPI;
import com.izor066.android.blocspot.api.model.PointOfInterest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by igor on 16/10/15.
 */
public class SearchableActivity extends AppCompatActivity {


    YelpAPI yelpAPI;
    String results;

    Location currentLocation;

    

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        currentLocation =  getLocation();
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
            String city = getCityFromLocation(currentLocation);
            //searchYelp(query, "London, UK");
            searchYelp(query, city);

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
                finish();
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
                PointOfInterest pointOfInterest = new PointOfInterest(-1, title, address, latitude, longitude, "Unsorted","notSetYet");
                pointsOfInterest.add(pointOfInterest);

            }

        } catch (JSONException e) {
            Log.e("JSONExecption", String.valueOf(e));
        }

        return pointsOfInterest;
    }

    // ToDo: getCityFromLocation - still loooking how to get last current location in here

    public String getCityFromLocation(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        String city;

        List<Address> addresses = new ArrayList<Address>();

        Geocoder geoCoder = new Geocoder(this, Locale.UK);

        try {
            addresses = geoCoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            Log.e("IOException", String.valueOf(e));
        }

        if (addresses.size() != 0) {
            Address address = addresses.get(0);
            city = address.getLocality();
            return city;
        } else {
            Toast.makeText(this, "Couldn't get your location. Defaulting to: London, UK.", Toast.LENGTH_SHORT).show();
            return "London, UK";
        }
    }

    public Location getLocation()
    {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(bestProvider);
        return location;
    }



}
