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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class WeatherActivity extends Activity {
	
	private TextView textView1;//城市
	private TextView textView2;//天气
	private TextView textView3;//最高温度
	private TextView textView4;//最低温度
	private TextView textView5;//发布时间
	private TextView textView6;//发布时间
	private ImageView imageView1;//天气
	private ImageView imageView2;//天气
	private ImageView imageView3;//天气
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
		textView6 = (TextView) findViewById(R.id.textView9);
		imageView1 = (ImageView) findViewById(R.id.imageView1);
		imageView2 = (ImageView) findViewById(R.id.imageView2);
		imageView3 = (ImageView) findViewById(R.id.imageView3);
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
            String img1 = weatherInfo.getString("img1").substring(1);
            String img2 = null;
            try {
            	img2 = weatherInfo.getString("img2").substring(1);
			} catch (Exception e) {
				img2 = "0.gif";
			}
            
            if (img1.equals(img2)) {
				imageView3.setImageResource(parseIcon(img1));
				imageView1.setVisibility(View.GONE);
				imageView2.setVisibility(View.GONE);
				textView6.setVisibility(View.GONE);
				imageView3.setVisibility(View.VISIBLE);
				
			}else {
				imageView1.setImageResource(parseIcon(img1)) ;
	            imageView2.setImageResource(parseIcon(img2)) ; 
	            imageView1.setVisibility(View.VISIBLE);
				imageView2.setVisibility(View.VISIBLE);
				textView6.setVisibility(View.VISIBLE);
				imageView3.setVisibility(View.GONE);
			}
            
                             
            
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
    
    
 // 工具方法，该方法负责把返回的天气图标字符串，转换为程序的图片资源ID。
 	private int parseIcon(String strIcon)
 	{
 		if (strIcon == null)
 			return -1;
 		if ("0.gif".equals(strIcon))
 			return R.drawable.a_0;
 		if ("1.gif".equals(strIcon))
 			return R.drawable.a_1;
 		if ("2.gif".equals(strIcon))
 			return R.drawable.a_2;
 		if ("3.gif".equals(strIcon))
 			return R.drawable.a_3;
 		if ("4.gif".equals(strIcon))
 			return R.drawable.a_4;
 		if ("5.gif".equals(strIcon))
 			return R.drawable.a_5;
 		if ("6.gif".equals(strIcon))
 			return R.drawable.a_6;
 		if ("7.gif".equals(strIcon))
 			return R.drawable.a_7;
 		if ("8.gif".equals(strIcon))
 			return R.drawable.a_8;
 		if ("9.gif".equals(strIcon))
 			return R.drawable.a_9;
 		if ("10.gif".equals(strIcon))
 			return R.drawable.a_10;
 		if ("11.gif".equals(strIcon))
 			return R.drawable.a_11;
 		if ("12.gif".equals(strIcon))
 			return R.drawable.a_12;
 		if ("13.gif".equals(strIcon))
 			return R.drawable.a_13;
 		if ("14.gif".equals(strIcon))
 			return R.drawable.a_14;
 		if ("15.gif".equals(strIcon))
 			return R.drawable.a_15;
 		if ("16.gif".equals(strIcon))
 			return R.drawable.a_16;
 		if ("17.gif".equals(strIcon))
 			return R.drawable.a_17;
 		if ("18.gif".equals(strIcon))
 			return R.drawable.a_18;
 		if ("19.gif".equals(strIcon))
 			return R.drawable.a_19;
 		if ("20.gif".equals(strIcon))
 			return R.drawable.a_20;
 		if ("21.gif".equals(strIcon))
 			return R.drawable.a_21;
 		if ("22.gif".equals(strIcon))
 			return R.drawable.a_22;
 		if ("23.gif".equals(strIcon))
 			return R.drawable.a_23;
 		if ("24.gif".equals(strIcon))
 			return R.drawable.a_24;
 		if ("25.gif".equals(strIcon))
 			return R.drawable.a_25;
 		if ("26.gif".equals(strIcon))
 			return R.drawable.a_26;
 		if ("27.gif".equals(strIcon))
 			return R.drawable.a_27;
 		if ("28.gif".equals(strIcon))
 			return R.drawable.a_28;
 		if ("29.gif".equals(strIcon))
 			return R.drawable.a_29;
 		if ("30.gif".equals(strIcon))
 			return R.drawable.a_30;
 		if ("31.gif".equals(strIcon))
 			return R.drawable.a_31;
 		return 0;
 	}
    
    @Override
    public void onBackPressed() {
//    	super.onBackPressed();
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
