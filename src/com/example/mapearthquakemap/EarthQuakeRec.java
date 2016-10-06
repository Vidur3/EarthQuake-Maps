package com.example.mapearthquakemap;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class EarthQuakeRec {

	private double lat,lng,magnitude;
	
	protected EarthQuakeRec(double lat,double lng,double magnitude)
	{
		super();
		this.lat=lat;
		this.lng = lng;
		this.magnitude=magnitude;
	}
	
	public double getLat()
	{
		return lat;
	}
	public double getLng()
	{
		return lng;
	}
	public double getMagnitude()
	{
		return magnitude;
	}
}
