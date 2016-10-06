package com.example.mapearthquakemap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class JSONResponseHandler implements ResponseHandler<List<EarthQuakeRec>> {


	@Override
	public List<EarthQuakeRec> handleResponse(HttpResponse response)
			throws ClientProtocolException, IOException {
		// TODO Auto-generated method stub
		List<EarthQuakeRec> result = new ArrayList<EarthQuakeRec>();
		String JSONresponse = new BasicResponseHandler().handleResponse(response);
		try
		{
		    JSONObject object = (JSONObject) new JSONTokener(JSONresponse).nextValue();
		    JSONArray earthquakes = object.getJSONArray("earthquakes");
		    
		    for(int i =0;i<earthquakes.length();i++)
		    {
		    	JSONObject tmp = (JSONObject)earthquakes.get(i);
		        result.add(new EarthQuakeRec(tmp.getDouble("lat"),tmp.getDouble("lng"), tmp.getDouble("magnitude")));   	
		    }
		}
		catch(JSONException e)
		{
			e.printStackTrace();
		}
		return result;
	}
}
