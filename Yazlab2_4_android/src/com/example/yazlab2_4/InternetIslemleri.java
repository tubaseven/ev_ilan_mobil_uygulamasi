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
		// internetten veri �ekmek i�in arkaplan i�lemi �al��t�r�l�yor:
		new BackgroundTask().execute((Void) null);
	}
	  
	  
	// Arka plan i�lerini yapacak olan "inner-class" tipinde s�n�f
	public class BackgroundTask extends AsyncTask<Void, Void, Void> {
 
		boolean flag=true;
		
	      @Override
	      protected void onPreExecute() {
	         super.onPreExecute();
	      }

	      // Arka planda �al��acak olan kod:
	      @Override
	      protected Void doInBackground(Void... params) {
	    	  
			try {
				String md5=GuncellemeKontrol();
				
				//ilk veri al�m�n�
				if (DATA.veri_ozeti==null) {
					DATA.veri_ozeti=md5;
					Verileri_Cek();
					Thread.sleep(2000);
				}
				//veri de�i�mi� mi kontrol
				else if(!md5.equals(DATA.veri_ozeti)){
					DATA.ev_listesi=null;
					
					DATA.veri_ozeti=md5;
					Verileri_Cek();
					
				}
				//internetteki veriyle telfondaki ayn�
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
	         
	         //internetten ev listesi al�nm�� ise
	         if(flag==true)
	         {
	        	 //al�nan veriden olu�turulan ev listesinde eleman varsa
		         if(DATA.ev_listesi.length > 0)
		         {
		        	 // LISTELE SAYFASI A�ILIYOR:
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
	
	
	
	// guncellemeleri kontrol etmek i�in servisteki veri ozeti al�n�yor
	public String GuncellemeKontrol(){
		
		String md5=getStringFromUrl(hash_url);
		
		return md5;
	}
	
	
	//internetten veri al�n�yor ve al�nan verilerden ev listesi olusturuluyor
	public int Verileri_Cek() throws JSONException{
	
		// HATA olu�mas� durumunda uygulaman�n kapanmamas� i�in try-catch kullan�lm��t�r.
		try{
		
				//belirtilen urlddeki web i�eri�i al�n�yor
			  String islem_sonucu= getStringFromUrl(api_url);
			 
			  //al�nan web i�eriginden json nesneleri olusturuluyor
			  JSONObject jobject = new JSONObject(islem_sonucu);
			  JSONArray jarray =jobject.getJSONArray("ev_listesi");
			  
			  Ev_Tipi[] evListesi = new Ev_Tipi[jarray.length()];
			  
			  //her bir ev i�in bir kere donecek olan dongu
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
				  
				  // eve ait resimlerin listesi al�n�yor
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
			  
			  //olusturulan ev listesi her yerden erisilebilmesi i�in 
			  //DATA isimli s�n�fta static olarak saklan�yor
			  DATA.ev_listesi = evListesi;
			  
			  return 1;
	  
		}catch(Exception ex){
			return 0;
		}
	}
	  
	
//____________________________________________________________________________________________	
	
	
	// Bu method internetten al�nm��t�r
	// bu method parametredeki URL'ye HTTP GET iste�i yap�yor
	// gelen HTTP cevab� metottan geri donduruyor

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
	    // JSON'� string olarak d�nd�r�yoruz.
	    return internetverisi;
	  }
}
