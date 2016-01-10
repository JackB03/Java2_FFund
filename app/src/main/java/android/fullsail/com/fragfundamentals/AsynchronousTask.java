package android.fullsail.com.fragfundamentals;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by JackBonner on 1/10/16.
 */

public class AsynchronousTask extends AsyncTask<URL, Integer, Weather> {

    private TextView displayTemp;
    private TextView city;
    private TextView description;
    private TextView humidity;

    ArrayList<Weather> weatherArrayList = new ArrayList<>();
    Adapter mAdapter;
    ListView zipCodeList;



    Activity mActivity;


    //CREATE ASYNC TASK

    String jsonString = "";

    public AsynchronousTask(Activity activity) {

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



        try{

        }catch (Exception e){

        }





        return store;
    }



    protected void onPostExecute(Weather _weatherInfo){



    }

}
