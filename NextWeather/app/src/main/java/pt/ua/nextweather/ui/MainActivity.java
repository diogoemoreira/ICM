package pt.ua.nextweather.ui;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.View;

import java.util.HashMap;

import pt.ua.nextweather.fragments.CitiesFragment;
import pt.ua.nextweather.R;
import pt.ua.nextweather.datamodel.City;
import pt.ua.nextweather.datamodel.WeatherType;
import pt.ua.nextweather.network.CityResultsObserver;
import pt.ua.nextweather.network.IpmaWeatherClient;

public class MainActivity extends AppCompatActivity {
    IpmaWeatherClient client = new IpmaWeatherClient();
    private HashMap<String, City> cities;
    private HashMap<Integer, WeatherType> weatherDescriptions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get cities from the API
        LoadCities();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadCities(); //on click loads all the cities from the API
            }
        });
    }


    public void LoadCities(){
        client.retrieveCitiesList(new CityResultsObserver() {
            @Override
            public void receiveCitiesList(HashMap<String, City> citiesCollection) {
                MainActivity.this.cities = citiesCollection;
                if(cities.size()!=0){
                    CitiesFragment fragment = CitiesFragment.newInstance(cities);
                    displayFragment(fragment);
                }
            }
            @Override
            public void onFailure(Throwable cause) {
                Log.i("LoadCities","failed to load Cities!");
            }
        });
    }


    public void displayFragment(Fragment fragment) {
        // Get  FragmentManager and start transaction.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();

        // Add the new fragment for the current broadcast request
        fragmentTransaction.add(R.id.fragment_container,
                fragment).addToBackStack(null).commit();
    }


}
