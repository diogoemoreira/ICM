package pt.ua.nextweather.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.TextView;

import pt.ua.nextweather.R;
import pt.ua.nextweather.fragments.WeatherResultsFragment;

public class CityWeather extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        String city_name  = getIntent().getStringExtra("cityname");
        int city_code= getIntent().getIntExtra("citycode",0);

        TextView title=findViewById(R.id.city_title);
        title.setText(city_name);

        //Generate Fragment and get Forecast
        getForecast(city_code);
    }



    private void getForecast(int city_code) {
        WeatherResultsFragment fragment = WeatherResultsFragment.newInstance(city_code);

        // Get  FragmentManager and start transaction.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();

        // Add the new fragment for the current broadcast request
        fragmentTransaction.add(R.id.fragment_container,
                fragment).addToBackStack(null).commit();
    }
}