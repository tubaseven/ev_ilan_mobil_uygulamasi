package com.example.yazlab2_4;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

public class Timer {
	
	 Handler handle = null;
	 Runnable runnable = null;
	 int zaman;
	 int sure=10;
	 
	 public Timer(final Context context){
	 
			 zaman = sure;
			  handle = new Handler();
			  runnable = new Runnable() 
			  {
				   @Override
				   public void run() 
				   {
					    if (zaman != 0) {
					 
					     zaman--;
					    } 
					    else 
					    {
							 handle.removeCallbacks(runnable);
							 zaman=sure;
							  
							 //ýnternet kontrol ediliyor
							 new InternetIslemleri();
					      
					    }
					    handle.postDelayed(runnable, 1000);
				   }
			  };
			  runnable.run();
	 }
}