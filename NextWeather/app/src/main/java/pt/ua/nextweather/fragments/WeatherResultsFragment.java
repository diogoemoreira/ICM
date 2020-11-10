package pt.ua.nextweather.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pt.ua.nextweather.R;
import pt.ua.nextweather.datamodel.Weather;
import pt.ua.nextweather.network.ForecastForACityResultsObserver;
import pt.ua.nextweather.network.IpmaWeatherClient;


public class WeatherResultsFragment extends Fragment {
    IpmaWeatherClient client = new IpmaWeatherClient();


    ArrayList<HashMap<String,String>> forecastResults=new ArrayList<>();
    String feedback="";
    TextView tv;
    int current_day=0;

    public WeatherResultsFragment() {
        // Required empty public constructor
    }


    public static WeatherResultsFragment newInstance(int city_code) {
        WeatherResultsFragment f = new WeatherResultsFragment();
        Bundle arguments = new Bundle();
        arguments.putInt("citycode", city_code);
        f.setArguments(arguments);
        return f;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_weather_result, container, false);
        tv = rootView.findViewById(R.id.tv1);

        Button prev = (Button) rootView.findViewById(R.id.previous);
        prev.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if(current_day>0)
                    ShowDay(--current_day);
            }
        });

        Button next = (Button) rootView.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if(current_day<4)
                    ShowDay(++current_day);
            }
        });


        if (getArguments().containsKey("citycode")) {
            tv.setText(String.valueOf(this.getArguments().getInt("citycode")));
            getForecast(this.getArguments().getInt("citycode"));
        }
        return rootView;
    }


    private void getForecast(int localId) {
        client.retrieveForecastForCity(localId, new ForecastForACityResultsObserver() {
            @Override
            public void receiveForecastList(List<Weather> forecast) {
                for (Weather day : forecast) {
                    HashMap<String,String> dayMap =new HashMap<String,String>();
                    feedback += day.toString();
                    feedback += "\n";
                    dayMap.put("Date",day.getForecastDate());
                    dayMap.put("Precipitation",String.valueOf(day.getPrecipitaProb()));
                    dayMap.put("MinTemp",String.valueOf(day.getTMin()));
                    dayMap.put("MaxTemp",String.valueOf(day.getTMax()));
                    dayMap.put("WindDirection",String.valueOf(day.getPredWindDir()));
                    dayMap.put("WindSpeed",String.valueOf(day.getClassWindSpeed()));
                    forecastResults.add(dayMap);
                }
                ShowDay(0);
                //Log.i("ForecastResults:",feedback);
            }
            @Override
            public void onFailure(Throwable cause) {
                feedback+= "Failed to get forecast for 5 days";
            }
        });

    }

    private void ShowDay(int index){
        System.out.println(forecastResults.toString());
        HashMap<String,String> dayMap =new HashMap<String,String>();
        dayMap=forecastResults.get(index);

        TextView generic=getView().findViewById(R.id.tv0);
        generic.setText("Date:" + dayMap.get("Date"));

        generic=getView().findViewById(R.id.tv1);
        generic.setText("Precipition: " + dayMap.get("Precipitation"));

        generic=getView().findViewById(R.id.tv2);
        generic.setText("Min Temp: " + dayMap.get("MinTemp"));

        generic=getView().findViewById(R.id.tv3);
        generic.setText("Max Temp: " + dayMap.get("MaxTemp"));

        generic=getView().findViewById(R.id.tv4);
        generic.setText("Wind Direction: " + dayMap.get("windDirection"));

        generic=getView().findViewById(R.id.tv5);
        generic.setText("Wind Speed: " + dayMap.get("WindSpeed"));

    }

    public void OnClickNext(View view){
        if(current_day<4)
            ShowDay(++current_day);
    }
    public void OnClickPrev(View view){
        if(current_day>0)
            ShowDay(--current_day);
    }
}