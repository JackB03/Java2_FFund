package android.fullsail.com.fragfundamentals;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JackBonner on 1/10/16.
 */

public class WeatherListFragment extends Fragment {



    EditText zipCode;

    Button searchButton;

    ListView zipCodeList;

    String userText;





    WeatherListFragment context = this;

    List<String> zipList = new ArrayList<String>();

    ArrayAdapter<String> zipAdapter;



    private OnButtonClickListener mListener;



    private static final String FEED_URL = "http://api.openweathermap.org/data/2.5/weather?appid=2fdbb11865748f1affb21d0cd696c2dc&amp;units=imperial&amp;zip=";





    public static final String TAG = "WeatherListFragment.TAG";



    public static WeatherListFragment newInstance(ArrayList<Weather> newArrayList){



        WeatherListFragment weatherFrag = new WeatherListFragment();

        return weatherFrag;

    }





    //CREATING PUBLIC INTERFACE

    public interface OnButtonClickListener{

        //public void displayText(String userTextString);

        public void containZip(String zipString);

    }


    @Override
    public void onAttach(Activity activity) {

        super.onAttach(activity);



        if(activity instanceof OnButtonClickListener){

            mListener = (OnButtonClickListener) activity;

        }else{

            throw new IllegalArgumentException("This doesn't work");

        }

    }



    @Override

    public View onCreateView(LayoutInflater _inflater, ViewGroup _container, Bundle _savedInstanceState){

        View view = _inflater.inflate(R.layout.weather_list, _container, false);





        return  view;







    }



    @Override

    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);



        View view = getView();



        assert view != null;

        zipCode = (EditText) view.findViewById(R.id.zipCode);

        zipCodeList = (ListView) view.findViewById(R.id.zipCodeList);

        zipCode.setText("");







        searchButton = (Button) view.findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {





                userText = zipCode.getText().toString();

                //mListener.displayText(userText);

                zipList.add(userText);

                zipAdapter.notifyDataSetChanged();

                zipCode.setText("");

                mListener.containZip(userText);





            }

        });





        zipCodeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }



        });





        zipAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, zipList);

        zipCodeList.setAdapter(zipAdapter);

    }



}


