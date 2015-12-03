package com.google.weathertest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Spinner provincesSpinner;
	private Spinner citiesSpinner;
	private Spinner countriesSpinner;
	private ArrayAdapter<String> adapter1;
	private ArrayAdapter<String> adapter2;
	private ArrayAdapter<String> adapter3;
	private List<String> list1;
	private List<String> list2;
	private List<String> list3;
	private List<String> codes1;
	private List<String> codes2;
	private List<String> codes3;
	private StringBuilder response;
	private String weatherCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		provincesSpinner = (Spinner) findViewById(R.id.spinner1);
		citiesSpinner = (Spinner) findViewById(R.id.spinner2);
		countriesSpinner = (Spinner) findViewById(R.id.spinner3);
		list1 = new ArrayList<String>();
		list2 = new ArrayList<String>();
		list3 = new ArrayList<String>();
		codes1 = new ArrayList<String>();
		codes2 = new ArrayList<String>();
		codes3 = new ArrayList<String>();

		getWeatherInfo(null);
				
	}
	
	public void selectListener(Spinner spinner,final List<String> codes){
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent,
					View view, int position, long id) {
				String code = null;
				if (codes!=null) {
					code = codes.get(position);
				}
				getWeatherInfo(code);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
	}
	
	

	public void getWeatherInfo(final String code) {
		new Thread() {

			@Override
			public void run() {

				HttpURLConnection connection = null;
				try {
					String address;
					if (code == null) {
						address = "http://www.weather.com.cn/data/list3/city.xml ";
					} else {
						address = "http://www.weather.com.cn/data/list3/city"
								+ code + ".xml";
					}
					URL url = new URL(address);
					connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					InputStream inputStream = connection.getInputStream();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(inputStream));
					response = new StringBuilder();
					String line;
					while ((line = reader.readLine()) != null) {
						response.append(line);
					}
					runOnUiThread(new Runnable() {
						public void run() {
							if (!TextUtils.isEmpty(response)) {
								String[] allAreas = response.toString().split(
										",");
								response = null;
								if (allAreas != null && allAreas.length > 0) {
									if (code==null||code.length()==2) {
										list2.clear();
										codes2.clear();
									}
									if (code==null||code.length()==2||code.length()==4) {
										list3.clear();
										codes3.clear();
									}
									
									
									for (String p : allAreas) {
										String[] array = p.split("\\|");
										if (code == null) {
											list1.add(array[1]);
											codes1.add(array[0]);
										} else if (code.length() == 2) {
											list2.add(array[1]);	
											codes2.add(array[0]);
										} else if (code.length() == 4){
											list3.add(array[1]);	
											codes3.add(array[0]);
										} else {
											weatherCode = array[1];
										}										
									}
									if (code == null) {
										adapter1 = new ArrayAdapter<String>(
												MainActivity.this,
												android.R.layout.simple_spinner_item,
												list1);
										provincesSpinner
												.setAdapter(adapter1);
										selectListener(provincesSpinner,codes1);
									} else if (code.length() == 2) {
										adapter2 = new ArrayAdapter<String>(
												MainActivity.this,
												android.R.layout.simple_spinner_item,
												list2);
										citiesSpinner
												.setAdapter(adapter2);
										selectListener(citiesSpinner,codes2);
									} else if (code.length() == 4){
										adapter3 = new ArrayAdapter<String>(
												MainActivity.this,
												android.R.layout.simple_spinner_item,
												list3);
										countriesSpinner
												.setAdapter(adapter3);
										selectListener(countriesSpinner, codes3);
									}
								}
							}
						}
					});

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	public void getWeather(View view) {
		if (weatherCode!=null) {
			Intent intent = new Intent(MainActivity.this,WeatherActivity.class);
			intent.putExtra("get", weatherCode);
			startActivity(intent);
			finish();
		}else {
			Toast.makeText(this, "didn't get weather info", Toast.LENGTH_SHORT).show();
		}
	}

}
