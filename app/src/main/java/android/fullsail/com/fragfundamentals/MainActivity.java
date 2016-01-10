package android.fullsail.com.fragfundamentals;


import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity.TAG";
    private static final String FEED_URL = "http://api.openweathermap.org/data/2.5/weather?appid=2fdbb11865748f1affb21d0cd696c2dc&amp;units=imperial&amp;zip=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void containZip(String userTextString) {
        Log.i("TAG", userTextString);

        if(isOnline()) {
            Weather newObj = null;

            try {
                String safeURL = FEED_URL + URLEncoder.encode(userTextString, "UTF-8") + ",us";
                URL url = new URL(safeURL);

                AsynchronousTask asyncTask = new AsynchronousTask(displayTemp, this);
                asyncTask.execute(url);


            } catch (MalformedURLException | UnsupportedEncodingException e) {


                e.printStackTrace();
            }
            try{
                FileInputStream fis = openFileInput("File.txt");
                ObjectInput ois = new ObjectInputStream(fis);
                newObj = (Weather)ois.readObject();
                ois.close();
                Log.e(TAG, "Name: " + newObj.getCity());
            }catch (Exception e){

                Log.e(TAG, "First error");
            }

        }else{
            DisplayFragment accessOffline = (DisplayFragment)getFragmentManager().findFragmentByTag(DisplayFragment.TAG);
            Weather newObj = null;
            try{
                FileInputStream fis = openFileInput("File.txt");
                ObjectInput ois = new ObjectInputStream(fis);
                newObj = (Weather)ois.readObject();
                ois.close();
            }catch (Exception e){

                Log.e(TAG, "First error");
            }
            if(newObj != null){
                accessOffline = DisplayFragment.newInstance(newObj);
                Log.e(TAG, "Not Null");
            }else{
                accessOffline.setDetails(newObj.getmWeatherMain(), newObj.getCity(), newObj.getmHumidity(), newObj.getmWeatherDescription());
            }
        }

    }


    public class AsynchronousTask extends AsyncTask<URL, Integer, Weather> {

        public TextView displayTemp;
        private TextView city;
        private TextView description;
        private TextView humidity;

        ArrayList<Weather> weatherArrayList = new ArrayList<>();
        Adapter mAdapter;
        ListView zipCodeList;


        Activity mActivity;


        //CREATE ASYNC TASK

        String jsonString = "";

        public AsynchronousTask(TextView displayTemp, Activity activity) {

            this.mActivity = activity;
        }


        protected void onPreExecute() {
            //progressBar.setVisibility(View.VISIBLE);

        }


        @Override
        protected Weather doInBackground(URL... _url) {

            for (URL queryURL : _url) {
                try {
                    URLConnection conn = queryURL.openConnection();
                    jsonString = IOUtils.toString(conn.getInputStream());
                    break;
                } catch (IOException e) {

                }
            }
            JSONObject apiData;
            Weather store = new Weather();


            try {
                apiData = new JSONObject(jsonString);
            } catch (Exception e) {

                apiData = null;
            }


            try {
                if (apiData != null) {
                    store.setmCity(apiData.getString("name"));
                    Log.i("TAG", "City" + apiData.get("name"));

                    store.setmWeatherMain(apiData.getJSONObject("main").getString("temp"));
                    Log.i("TAG", "Temp" + apiData.getJSONObject("main").getString("temp"));

                    store.setmHumidity(apiData.getJSONObject("main").getString("humidity"));
                    Log.i("TAG", "Humidity" + apiData.getJSONObject("main").getString("humidity"));

                    store.setmWeatherDescription(apiData.getJSONArray("weather").getJSONObject(0).getString("description"));
                    Log.i("TAG", "Description" + apiData.getJSONArray("weather").getJSONObject(0).getString("description"));


                }


            } catch (Exception e) {

                apiData = null;
            }





            return store;
        }


        protected void onPostExecute(Weather _weatherInfo) {

            DisplayFragment displayFrag = (DisplayFragment)getFragmentManager().findFragmentByTag(DisplayFragment.TAG);

            if(displayFrag == null){
                displayFrag = DisplayFragment.newInstance(_weatherInfo);
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_two, displayFrag, DisplayFragment.TAG)
                        .commit();

            }else{

                displayFrag.setDetails(_weatherInfo.getmWeatherMain(), _weatherInfo.getCity(), _weatherInfo.getmHumidity(), _weatherInfo.getmWeatherDescription());
            }


        }






    }
    //CONNECTION
    protected boolean isOnline() {

        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = manager.getActiveNetworkInfo();
        if (network != null && network.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }

    }



}

