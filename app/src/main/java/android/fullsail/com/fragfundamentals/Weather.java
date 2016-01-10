package android.fullsail.com.fragfundamentals;

import java.io.Serializable;

/**
 * Created by JackBonner on 1/10/16.
 */

public class Weather implements Serializable {

    private static long serialVersionUID = 8736847634070552888L;

    private String mCity;
    private String mWeatherMain;
    private String mWeatherDescription;
    private String mHumidity;

    public Weather() {mCity = mWeatherMain  = mHumidity = mWeatherDescription = "";}

    public Weather(String _city, Integer _weatherMain, String _weatherDescription, String _humidity){
        mCity = _city;
        mWeatherMain = _weatherMain.toString();
        mWeatherDescription = _weatherDescription;
        mHumidity = _humidity.toString();


    }

    public void setmCity(String mCity) {
        this.mCity = mCity;
    }

    public void setmWeatherMain(String mWeatherMain) {
        this.mWeatherMain = mWeatherMain;
    }

    public void setmWeatherDescription(String mWeatherDescription) {
        this.mWeatherDescription = mWeatherDescription;
    }

    public void setmHumidity(String mHumidity) {
        this.mHumidity = mHumidity;
    }

    public String getCity(){
        return mCity;
    }

    public String getmWeatherMain(){
        return mWeatherMain;
    }

    public String getmWeatherDescription(){
        return  mWeatherDescription;
    }

    public String getmHumidity() {return mHumidity;}
}

