package com.example.yazlab2_4;

import com.example.Data.DATA;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {

	LayoutInflater inflanter;
	
	public CustomAdapter(Activity activity) {
		// TODO Auto-generated constructor stub
		inflanter=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return DATA.ev_listesi.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return DATA.ev_listesi[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		View satirView=inflanter.inflate(R.layout.satir_layout, null);
		
		TextView tv1=(TextView)satirView.findViewById(R.id.textView1);
		TextView tv2=(TextView)satirView.findViewById(R.id.textView2);

		tv1.setText("Fiyat :"+ DATA.ev_listesi[arg0].evFiyat);
		tv2.setText("Bina Yasi :"+ DATA.ev_listesi[arg0].evBinaYasi);
		
		
		
		return satirView;
	}

}
