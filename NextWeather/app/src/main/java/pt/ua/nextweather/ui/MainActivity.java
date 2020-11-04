package pt.ua.nextweather.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import pt.ua.nextweather.R;
import pt.ua.nextweather.datamodel.City;
import pt.ua.nextweather.datamodel.Weather;
import pt.ua.nextweather.datamodel.WeatherType;
import pt.ua.nextweather.network.CityResultsObserver;
import pt.ua.nextweather.network.ForecastForACityResultsObserver;
import pt.ua.nextweather.network.IpmaWeatherClient;
import pt.ua.nextweather.network.WeatherTypesResultsObserver;

public class MainActivity extends AppCompatActivity {

    IpmaWeatherClient client = new IpmaWeatherClient();
    private HashMap<String, City> cities;
    private HashMap<Integer, WeatherType> weatherDescriptions;

    //for the cities and weather
    private RecyclerView mRecyclerView;
    private RecyclerView weatherRecyclerView;

    private WordListAdapter mAdapter;
    private WeatherListAdapter weatherAdapter;
    private final LinkedList<String> mCitiesList = new LinkedList<>();
    private final LinkedList<String> mWeatherList = new LinkedList<>();
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        
        //RecyclerViews
        mRecyclerView = findViewById(R.id.recyclerViewCities);
        //weatherRecyclerView = findViewById(R.id.recyclerViewWeather);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new WordListAdapter(this, mCitiesList);
        //weatherAdapter = new WeatherListAdapter(this, mWeatherList);
        // Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        //weatherRecyclerView.setAdapter(weatherAdapter);
        // Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //weatherRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        for(String c: cities.keySet()){
            callWeatherForecastForACityStep1(c);
        }
        callWeatherForecastForACityStep1("Aveiro");
        callWeatherForecastForACityStep1("Coimbra");
        callWeatherForecastForACityStep1("Porto");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void callWeatherForecastForACityStep1(String city) {

        int citiesListSize = mCitiesList.size();
        // Add a new word to the wordList.
        mCitiesList.addLast(city+"\n");
        // Notify the adapter, that the data has changed.
        mRecyclerView.getAdapter().notifyItemInserted(citiesListSize);

        // call the remote api, passing an (anonymous) listener to get back the results
        client.retrieveWeatherConditionsDescriptions(new WeatherTypesResultsObserver() {
            @Override
            public void receiveWeatherTypesList(HashMap<Integer, WeatherType> descriptorsCollection) {
                MainActivity.this.weatherDescriptions = descriptorsCollection;
                callWeatherForecastForACityStep2( city);
            }
            @Override
            public void onFailure(Throwable cause) {
                int citiesListSize = mCitiesList.size();
                // Add a new word to the wordList.
                mCitiesList.addLast("Failed to get weather conditions");
                // Notify the adapter, that the data has changed.
                mRecyclerView.getAdapter().notifyItemInserted(citiesListSize);
            }
        });

    }

    private void callWeatherForecastForACityStep2(String city) {
        client.retrieveCitiesList(new CityResultsObserver() {

            @Override
            public void receiveCitiesList(HashMap<String, City> citiesCollection) {
                MainActivity.this.cities = citiesCollection;
                City cityFound = cities.get(city);
                if( null != cityFound) {
                    int locationId = cityFound.getGlobalIdLocal();
                    //callWeatherForecastForACityStep3(locationId);
                } else {
                    int citiesListSize = mCitiesList.size();
                    // Add a new word to the wordList.
                    mCitiesList.addLast("unknown city: " + city);
                    // Notify the adapter, that the data has changed.
                    mRecyclerView.getAdapter().notifyItemInserted(citiesListSize);
                }
            }

            @Override
            public void onFailure(Throwable cause) {
                int citiesListSize = mCitiesList.size();
                // Add a new word to the wordList.
                mCitiesList.addLast("Failed to get cities list!");
                // Notify the adapter, that the data has changed.
                mRecyclerView.getAdapter().notifyItemInserted(citiesListSize);
            }
        });
    }

    private void callWeatherForecastForACityStep3(int localId) {
        client.retrieveForecastForCity(localId, new ForecastForACityResultsObserver() {
            @Override
            public void receiveForecastList(List<Weather> forecast) {
                for (Weather day : forecast) {
                    mWeatherList.addLast(day.toString());
                    int weatherListSize = mWeatherList.size();
                    // Notify the adapter, that the data has changed.
                    weatherRecyclerView.getAdapter().notifyItemInserted(weatherListSize);
                }
            }
            @Override
            public void onFailure(Throwable cause) {

                int weatherListSize = mWeatherList.size();
                mWeatherList.addLast("Failed to get forecast for 5 days");
                // Notify the adapter, that the data has changed.
                weatherRecyclerView.getAdapter().notifyItemInserted(weatherListSize);
            }
        });

    }

}
