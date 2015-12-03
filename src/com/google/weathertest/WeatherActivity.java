package com.google.weathertest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class WeatherActivity extends Activity {
	
	private TextView textView1;//城市
	private TextView textView2;//天气
	private TextView textView3;//最高温度
	private TextView textView4;//最低温度
	private TextView textView5;//发布时间
	private int i = 0;
	private long date = 0;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather);
		textView1 = (TextView) findViewById(R.id.textView1);
		textView2 = (TextView) findViewById(R.id.textView2);
		textView3 = (TextView) findViewById(R.id.textView6);
		textView4 = (TextView) findViewById(R.id.textView7);
		textView5 = (TextView) findViewById(R.id.textView8);
		getWeatherInfo(getIntent().getStringExtra("get"));
	}
	
	/** 
     * 解析服务器返回的JSON数据，并将解析出的数据存储到本地。 
     */ 
	
	public void getWeatherInfo(final String code) {
		new Thread() {

			@Override
			public void run() {

				HttpURLConnection connection = null;
				try {
					String address = "http://www.weather.com.cn/data/cityinfo/"+code+".html";
					
					URL url = new URL(address);
					connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					InputStream inputStream = connection.getInputStream();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(inputStream));
					final StringBuilder response = new StringBuilder();
					String line;
					while ((line = reader.readLine()) != null) {
						response.append(line);
					}
					runOnUiThread(new Runnable() {
						public void run() {
							handleWeatherResponse(response.toString());
						}
					});
					
				}catch(Exception e){
					
				}
			}
		}.start();
	}
	
    public void handleWeatherResponse(String response){ 

        try { 
            JSONObject jsonObject = new JSONObject(response); 

            JSONObject weatherInfo = jsonObject.getJSONObject("weatherinfo"); 
            String cityName = weatherInfo.getString("city"); 
            String temp1 = weatherInfo.getString("temp1"); 
            String temp2 = weatherInfo.getString("temp2"); 
            String weatherDesp = weatherInfo.getString("weather"); 
            String publishTime = weatherInfo.getString("ptime"); 
            if (cityName!=null) {
            	textView1.setText(cityName);
            	textView2.setText(weatherDesp);
            	textView3.setText(temp1);
            	textView4.setText(temp2);
            	textView5.setText(publishTime);
			}
            
        }catch (JSONException e) { 

            e.printStackTrace(); 
        }
        
    } 
    
    @Override
    public void onBackPressed() {
    	
    	if(i==0){
    		date = System.currentTimeMillis();
    		Toast.makeText(this, "再按一次退出程序~", Toast.LENGTH_SHORT).show();
    		i++;
    	}else {
    		if(System.currentTimeMillis()-date>5000) {
				Toast.makeText(this, "再按一次退出程序~", Toast.LENGTH_SHORT).show();
				i=1;
				date = System.currentTimeMillis();
			}else {
				finish();
			}
			
		}
    }
	
}
