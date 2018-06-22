package com.example.activities;

import com.example.Data.DATA;
import com.example.yazlab2_4.CustomAdapter;
import com.example.yazlab2_4.R;
import com.example.yazlab2_4.Timer;
import com.example.yazlab2_4.R.id;
import com.example.yazlab2_4.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class Listeleme extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.listele);
		
		// listelemede kullan�lan eleman'a i�erik s�n�f� atan�yor:
		ListView listview=(ListView)findViewById(R.id.listView1);
		CustomAdapter custom=new CustomAdapter(this);
		listview.setAdapter(custom);
		
	    // Timer nesnesi olu�turularak her 10sn de bir internterreki veri kontrol edilmesi sa�lan�yor:
		Timer guncel=new Timer(this);
		
		// ekranda g�sterilen listview'den sat�r se�ilmesi durumunda �al��acak olan kod:
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				DATA.selected_item_index=position;
				
				// se�ilen sat�rdaki evin �zelliklerini g�stermek i�in ayr�nt�lar activity'si a��l�yor:
				startActivity(new Intent("android.intent.action.AYRINTILAR"));
				
			}
			
		
		});
	}

	
	
	String msg = "Android : ";
	/** Called when the activity is about to become visible. */
	   @Override
	   protected void onStart() {
	      super.onStart();
	      Log.d(msg, "The onStart() event");
	   }

	   /** Called when the activity has become visible. */
	   @Override
	   protected void onResume() {
	      super.onResume();
	      Log.d(msg, "The onResume() event");
	   }

	   /** Called when another activity is taking focus. */
	   @Override
	   protected void onPause() {
	      super.onPause();
	      Log.d(msg, "The onPause() event");
	   }

	   /** Called when the activity is no longer visible. */
	   @Override
	   protected void onStop() {
	      super.onStop();
	      Log.d(msg, "The onStop() event");
	   }
	
}
