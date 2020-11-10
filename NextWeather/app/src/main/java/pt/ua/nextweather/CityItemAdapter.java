package pt.ua.nextweather;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.TreeSet;

import pt.ua.nextweather.datamodel.City;
import pt.ua.nextweather.ui.CityWeather;

public class CityItemAdapter extends RecyclerView.Adapter<CityItemAdapter.ForecastViewHolder>  {
    private final HashMap<String, City> cities;
    private LayoutInflater mInflater;

    public CityItemAdapter(Context context, HashMap<String,City> cities) {
        mInflater = LayoutInflater.from(context);
        this.cities = cities;
    }

    class ForecastViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView cityItemView;
        final CityItemAdapter mAdapter;

        public ForecastViewHolder(View itemView, CityItemAdapter adapter) {
            super(itemView);
            cityItemView= itemView.findViewById(R.id.city_name);
            cityItemView.setOnClickListener(this);
            this.mAdapter = adapter;
        }

        @Override
        public void onClick(View view) {
            TextView cityview = (TextView) view;
            Context context=view.getContext();

            City city = cities.get(cityview.getText());    // Use that to access the affected item in mWordList.

            Intent intent = new Intent(context, CityWeather.class);
            intent.putExtra("cityname", city.getLocal());
            intent.putExtra("citycode",city.getGlobalIdLocal());


            context.startActivity(intent);
        }
    }


    @NonNull
    @Override
    public CityItemAdapter.ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.city_holder,
                parent, false);
        return new ForecastViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull CityItemAdapter.ForecastViewHolder holder, int position) {
        TreeSet<String> keys = new TreeSet<>(cities.keySet());
        String mCurrent = keys.toArray()[position].toString();
        holder.cityItemView.setText(mCurrent);
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }


}
