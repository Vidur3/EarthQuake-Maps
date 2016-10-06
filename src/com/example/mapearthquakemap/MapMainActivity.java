package com.example.mapearthquakemap;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MapMainActivity extends Activity {
	
	private static final double CAMERA_LNG = 87.0;
	private static final double CAMERA_LAT = 17.0;
	
	private GoogleMap mMap;
	
	private final static String UNAME = "aporter";
	private final static String URL =  "http://api.geonames.org/earthquakesJSON?north=44.1&south=-9.9&east=-22.4&west=55.2&username="
			+ UNAME;

private static final String TAG = "MapMainActvity";

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_main);
        new HttpGetTask().execute(URL);		
		
	}

	
	private class HttpGetTask extends AsyncTask<String, Void, List<EarthQuakeRec>>
	{
		AndroidHttpClient mClient = AndroidHttpClient.newInstance("");

		@Override
		protected List<EarthQuakeRec> doInBackground(String... params) {
			// TODO Auto-generated method stub
			HttpGet request = new HttpGet(params[0]);
			JSONResponseHandler handler = new JSONResponseHandler();
			
			try
			{
				return mClient.execute(request,handler);
			}
			catch(ClientProtocolException e)
			{
				Log.i(TAG,"ClientProtocolException");
			}
			catch(IOException e)
			{
				Log.i(TAG, "IOException");
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(List<EarthQuakeRec> result) {
			
			mMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
			
			if(null!=mMap)
			{
				for(EarthQuakeRec rec : result)
				{
					mMap.addMarker(new MarkerOptions()
					.position(new LatLng(rec.getLat(), rec.getLng()))
					.title(String.valueOf(rec.getMagnitude()))
					.icon(BitmapDescriptorFactory.defaultMarker(getMarkerColor(rec.getMagnitude()))));
							
				}
				mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(CAMERA_LAT, CAMERA_LNG)));
			}
			
			if(null!=mClient)
				mClient.close();
			
		}

		private float getMarkerColor(double magnitude)
		{
			if(magnitude<6.0)
			{
				magnitude=6.0;
			}
			else if(magnitude>9.0)
			{
				magnitude = 9.0;
			}
			
			return (float)(120*(magnitude-6));
		}
		
		
	}		
		
	}

