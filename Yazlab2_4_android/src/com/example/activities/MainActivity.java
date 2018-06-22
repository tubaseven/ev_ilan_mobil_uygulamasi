package com.example.activities;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.Data.DATA;
import com.example.yazlab2_4.InternetIslemleri;
import com.example.yazlab2_4.R;
import com.example.yazlab2_4.R.id;
import com.example.yazlab2_4.R.layout;
import com.example.yazlab2_4.R.menu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {

	
	// OnCreate methodu ekran� olu�turan ve temel kodlar�n yaz�ld��� yerdir.
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// bu method ile ekran g�r�nt�s� olan activity_main.xml dosyas� kod ile ba�lant� kuruyor.
		setContentView(R.layout.activity_main);
		
		// DATA s�n�f�nda static olarak saklanan context'e atan�yor:
		DATA.aktifContext=this;
		
		// =new yap�larak constructer �a��r�l�yyor. 
		// b�ylece internetten veri alma i�lemleri ger�ekle�tiriliyor.
		InternetIslemleri internet_islemi = new InternetIslemleri();
	}

	// otomatik kod: a��klamaya gerek yok.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// otomatik kod: a��klamaya gerek yok.
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	
	
	
	  
	  
	  
	  
}
