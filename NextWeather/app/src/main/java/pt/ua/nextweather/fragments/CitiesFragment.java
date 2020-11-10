package pt.ua.nextweather.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

import pt.ua.nextweather.CityItemAdapter;
import pt.ua.nextweather.R;
import pt.ua.nextweather.datamodel.City;


public class CitiesFragment extends Fragment {
    private HashMap<String, City> cities;

    Context context;
    private RecyclerView mRecyclerView;
    private CityItemAdapter mAdapter;

    public CitiesFragment() { /*Required empty public constructor*/}

    public static CitiesFragment newInstance(HashMap<String,City> cities) {
        CitiesFragment f = new CitiesFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable("cities",cities);
        f.setArguments(arguments);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        context = container.getContext();
        Bundle b = this.getArguments();
        View view = inflater.inflate(R.layout.fragment_cities, container, false);

        if(b.getSerializable("cities") != null) {
            cities = (HashMap<String, City>) b.getSerializable("cities");

            for(String city: cities.keySet())
                //Log.i("z",city);

            mRecyclerView = (RecyclerView) view.findViewById(R.id.citiesrecycler);
            mAdapter = new CityItemAdapter(context, cities);                // Create an adapter and supply the data to be displayed.
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));   // Give the RecyclerView a default layout manager.
            mRecyclerView.setAdapter(mAdapter);                                 // Connect the adapter with the RecyclerView.
        }

        return view;
    }


}