package com.example.tempature_app;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import cz.msebera.android.httpclient.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class Weather {

    public static final String request = "https://api.openweathermap.org/data/2.5/weather?id=2996944&units=metric&appid=aca2124f0d11437c7406f8490e604e42";

    private MutableLiveData<String> weather = new MutableLiveData<>();

    private MutableLiveData<Integer> temp = new MutableLiveData<>();

    public LiveData<String> getWeather(){
        return this.weather;
    }

    public LiveData<Integer> getTemp(){
        return this.temp;
    }

    public void callAPI() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(request, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.i("code", String.valueOf(statusCode));
                try {
                    convertResponse(responseBody);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }


    private void convertResponse(byte[] responseBody) throws JSONException {
        Log.i("info","convert");
        JSONObject responseJson = new JSONObject(new String(responseBody));
        this.weather.postValue(responseJson.getJSONArray("weather").getJSONObject(0).getString("main"));
        this.temp.postValue(responseJson.getJSONObject("main").getInt("temp"));
    }
}
