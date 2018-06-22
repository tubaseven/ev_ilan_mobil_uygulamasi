package com.example.yazlab2_4;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.Data.DATA;
import com.example.classes.Ev_Tipi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.Toast;

public class InternetIslemleri {
	
	

	String api_url="http://tselif.azurewebsites.net/api/Yazlab/Listele";
	String hash_url="http://tselif.azurewebsites.net/api/Yazlab/Kontrol";
	
	InputStream is = null;
	JSONObject jObj = null;
	  
	String internetverisi = "";
	  
	  
	// Constructer
	public InternetIslemleri(){
		// internetten veri çekmek için arkaplan iþlemi çalýþtýrýlýyor:
		new BackgroundTask().execute((Void) null);
	}
	  
	  
	// Arka plan iþlerini yapacak olan "inner-class" tipinde sýnýf
	public class BackgroundTask extends AsyncTask<Void, Void, Void> {
 
		boolean flag=true;
		
	      @Override
	      protected void onPreExecute() {
	         super.onPreExecute();
	      }

	      // Arka planda çalýþacak olan kod:
	      @Override
	      protected Void doInBackground(Void... params) {
	    	  
			try {
				String md5=GuncellemeKontrol();
				
				//ilk veri alýmýný
				if (DATA.veri_ozeti==null) {
					DATA.veri_ozeti=md5;
					Verileri_Cek();
					Thread.sleep(2000);
				}
				//veri deðiþmiþ mi kontrol
				else if(!md5.equals(DATA.veri_ozeti)){
					DATA.ev_listesi=null;
					
					DATA.veri_ozeti=md5;
					Verileri_Cek();
					
				}
				//internetteki veriyle telfondaki ayný
				else{
					flag=false;
				}
				
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	  
	         return null;
	      }

	      @Override
	      protected void onPostExecute(Void result) {
	         super.onPostExecute(result);
	         
	         //internetten ev listesi alýnmýþ ise
	         if(flag==true)
	         {
	        	 //alýnan veriden oluþturulan ev listesinde eleman varsa
		         if(DATA.ev_listesi.length > 0)
		         {
		        	 // LISTELE SAYFASI AÇILIYOR:
			         Intent intent = new Intent("android.intent.action.LISTELE");
			         DATA.aktifContext.startActivity(intent);
			         
		         }else{
		        	 //Toast.makeText(MainActivity.this,"Yuklenemedi", Toast.LENGTH_LONG).show();
		         }
	         }
	      }

	      @Override
	      protected void onProgressUpdate(Void... values) {
	         super.onProgressUpdate(values);
	      }


	   }
	
	
	
	// guncellemeleri kontrol etmek için servisteki veri ozeti alýnýyor
	public String GuncellemeKontrol(){
		
		String md5=getStringFromUrl(hash_url);
		
		return md5;
	}
	
	
	//internetten veri alýnýyor ve alýnan verilerden ev listesi olusturuluyor
	public int Verileri_Cek() throws JSONException{
	
		// HATA oluþmasý durumunda uygulamanýn kapanmamasý için try-catch kullanýlmýþtýr.
		try{
		
				//belirtilen urlddeki web içeriði alýnýyor
			  String islem_sonucu= getStringFromUrl(api_url);
			 
			  //alýnan web içeriginden json nesneleri olusturuluyor
			  JSONObject jobject = new JSONObject(islem_sonucu);
			  JSONArray jarray =jobject.getJSONArray("ev_listesi");
			  
			  Ev_Tipi[] evListesi = new Ev_Tipi[jarray.length()];
			  
			  //her bir ev için bir kere donecek olan dongu
			  for (int i = 0; i < jarray.length(); i++) {
				  JSONObject obje = jarray.getJSONObject(i);
				  
				  // internet verisinden ev nesnesi olusturuluyor
				  Ev_Tipi ev= new Ev_Tipi();
				  ev.evAciklama = obje.getString("evAciklama");
				  ev.evAlan 	= obje.getInt("evAlan");
				  ev.evBinaYasi = obje.getInt("evBinaYasi");
				  ev.evBulKat 	= obje.getInt("evBulKat");
				  ev.evEmlakTip = obje.getString("evEmlakTip");
				  ev.evFiyat 	= obje.getInt("evFiyat");
				  ev.evID 		= obje.getInt("evID");
				  ev.evIL 		= obje.getString("evIL");
				  ev.evOdaSayisi= obje.getInt("evOdaSayisi");
				  
				  // eve ait resimlerin listesi alýnýyor
				  JSONArray resimlistesi = obje.getJSONArray("resimler");
				  String[] resimler= new String[resimlistesi.length()];
				  for (int j = 0; j < resimlistesi.length(); j++) {
					 JSONObject resimobjesi = resimlistesi.getJSONObject(j);
					  resimler[j] = resimobjesi.getString("resimYol") ;
				}
				  ev.resimler= resimler;
				  
				  ev.bitmaps=new Bitmap[resimler.length];
			
				  //olusturulan ev, ev listesine ekleniyor
				  evListesi[i] = ev;
			  }
			  
			  //olusturulan ev listesi her yerden erisilebilmesi için 
			  //DATA isimli sýnýfta static olarak saklanýyor
			  DATA.ev_listesi = evListesi;
			  
			  return 1;
	  
		}catch(Exception ex){
			return 0;
		}
	}
	  
	
//____________________________________________________________________________________________	
	
	
	// Bu method internetten alýnmýþtýr
	// bu method parametredeki URL'ye HTTP GET isteði yapýyor
	// gelen HTTP cevabý metottan geri donduruyor

	  public String getStringFromUrl(String url) {	 	  
		//  Log.e("URL", url);
	    // Making HTTP request
	    try {
	      // defaultHttpClient
	      DefaultHttpClient httpClient = new DefaultHttpClient();
	     HttpGet HttpGet = new HttpGet(url);
	      HttpResponse httpResponse = httpClient.execute(HttpGet);
	      HttpEntity httpEntity = httpResponse.getEntity();
	      is = httpEntity.getContent();
	    } catch (Exception e) {
	    	// Log.e("JSON Parser", "UnsupportedEncodingException " + e.toString());
	      e.printStackTrace();
	    } 
	    try {
	      BufferedReader reader = new BufferedReader(new InputStreamReader(
	          is, "iso-8859-1"), 8);
	      StringBuilder sb = new StringBuilder();
	      String line = null;
	      while ((line = reader.readLine()) != null) {
	        sb.append(line + "\n");
	      }
	      is.close();
	      internetverisi = sb.toString();

	    } catch (Exception e) {
	      //Log.e("Buffer Error", "Error converting result " + e.toString());
	    }
	    // JSON'ý string olarak döndürüyoruz.
	    return internetverisi;
	  }
}
