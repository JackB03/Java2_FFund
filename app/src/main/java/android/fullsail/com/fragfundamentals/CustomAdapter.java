package android.fullsail.com.fragfundamentals;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by JackBonner on 1/10/16.
 */

public class CustomAdapter extends BaseAdapter {

    private static final long ID_CONSTANT = 0x010101010L;
    private ArrayList<Weather> mWeather;
    private Context mContext;

    public CustomAdapter(WeatherListFragment asynchronousTask, ArrayList<Weather> weatherArrayList) {


    }


    @Override
    public int getCount() {
        return mWeather.size();
    }

    @Override
    public Object getItem(int position) {
        return mWeather.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ID_CONSTANT + position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.weather_list, parent, false);
        }

        Weather weather = (Weather)getItem(position);
        TextView zipText = (TextView)convertView.findViewById(R.id.zipCodeList);
        zipText.setText(weather.getCity());

        return convertView;
    }

    public void insert(EditText zipCode) {

    }
}

