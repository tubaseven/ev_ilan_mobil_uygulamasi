package com.example.activities;

import java.io.InputStream;
import java.net.URL;

import org.json.JSONException;







import com.example.Data.DATA;
import com.example.classes.Ev_Tipi;
import com.example.yazlab2_4.R;
import com.example.yazlab2_4.R.id;
import com.example.yazlab2_4.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Ayrintilar extends Activity{

	
	
	Ev_Tipi ev;

	ProgressBar progress;
	ImageView image;
	
	int resim_sirasi=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		// bu method ile ekran görüntüsü olan activity_main.xml dosyasý kod ile baðlantý kuruyor.
		setContentView(R.layout.ayrintilar);
		
		// DATA sýnýfýnda static olarak saklanan context'e atanýyor:
		DATA.aktifContext=this;
		
		// ekranda gösterilecek olan yükleniyor diyalog elemaný alýnýyor:
		progress=(ProgressBar)findViewById(R.id.progressBar1);
        progress.setVisibility(View.INVISIBLE);
        
		image=(ImageView)findViewById(R.id.imageView1);
		
		
		
		TextView tv_il=(TextView)findViewById(R.id.TextView_il);
		TextView tv_alan=(TextView)findViewById(R.id.TextView_alan);
		TextView tv_oda=(TextView)findViewById(R.id.TextView_oda);
		TextView tv_bina=(TextView)findViewById(R.id.TextView_bina);
		TextView tv_kat=(TextView)findViewById(R.id.TextViewkat);
		TextView tv_fiyat=(TextView)findViewById(R.id.TextViewfiyat);
		TextView tv_emlaktip=(TextView)findViewById(R.id.textView_emlaktip);
		TextView tv_aciklama=(TextView)findViewById(R.id.textView_aciklama);
		
		
		
		ev=DATA.ev_listesi[DATA.selected_item_index];

		tv_il.setText(tv_il.getText().toString()+ev.evIL);
		tv_alan.setText(tv_alan.getText().toString()+ev.evAlan);
		tv_oda.setText(tv_oda.getText().toString()+ev.evOdaSayisi);
		tv_bina.setText(tv_bina.getText().toString()+ev.evBinaYasi);
		tv_kat.setText(tv_kat.getText().toString()+ev.evBulKat);
		tv_fiyat.setText(tv_fiyat.getText().toString()+ev.evFiyat);
		tv_emlaktip.setText(tv_emlaktip.getText().toString()+ev.evEmlakTip);
		tv_aciklama.setText(tv_aciklama.getText().toString()+ev.evAciklama);
		
		

		
		Button button_geri=(Button)findViewById(R.id.button1);
		Button button_ileri=(Button)findViewById(R.id.button2);
		
		// ileri butonu ile bir sonraki resim gösteriliyor:
		button_geri.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if((resim_sirasi-1) == -1)
					resim_sirasi=ev.bitmaps.length-1;
				else 
					resim_sirasi=resim_sirasi-1;
				
				image.setImageBitmap(ev.bitmaps[resim_sirasi]);
			}
		});
		
		// geri butonu ile bir önceki resim gösteriliyor:
		button_ileri.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if((resim_sirasi+1) == ev.bitmaps.length)
					resim_sirasi=0;
				else 
					resim_sirasi=resim_sirasi+1;
				
				image.setImageBitmap(ev.bitmaps[resim_sirasi]);
			}
		});
		
		new BackgroundTask().execute((Void) null);
	}
		
	// bu arka plan iþlemi ile bir eve ait olan resimler internetten indirilip
	// eve atanýyor. artýk gösterilmeye hazýrlar.
	public class BackgroundTask extends AsyncTask<Void, Void, Void> {

	      @Override
	      protected void onPreExecute() {
	         super.onPreExecute();
	         progress.setVisibility(View.VISIBLE);
	      }

	      @Override
	      protected Void doInBackground(Void... params) {
	    	  try{
		    	  for (int i = 0; i < ev.resimler.length; i++) {
		    		
		    		  
					ev.bitmaps[i]=BitmapFactory.decodeStream((InputStream)new URL(ev.resimler[i]).getContent());
		    	  }
	    	  }catch(Exception e){
	    		 // Toast.makeText(Ayrintilar.this, "resimler yuklenemedi", Toast.LENGTH_LONG).show();
	    	  }
	         return null;
	      }

	      @Override
	      protected void onPostExecute(Void result) {
	         super.onPostExecute(result);
	         
	         progress.setVisibility(View.INVISIBLE);
	         
	         if(ev.bitmaps.length>0)
	        	 if(ev.bitmaps[0]!=null)
	        		 //	ilk resim ekranda gösteriliyor:
	        		 image.setImageBitmap(ev.bitmaps[0]);
	      }
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
