using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

using System.Data.SQLite;
using System.IO;
using System.Security.Cryptography;
using System.Text;
using Yazlab2_4.Models;

namespace Yazlab2_4.Code
{
    // BU SINIF SADECE DIGER SINIFLARA HIZMET VERIR. YANI METHODLARINI DIGERLERININ KULLANIMLARINA ACAR.
    public class KOD_SINIFI
    {

        // VERITABANINA BAGLANTI KURMAK ICIN GEREKEN BAGLANTI NESNESI OLUSTURAN FONKSIYON
        public static SQLiteConnection BaglantiOlustur()
        {
            SQLiteConnection connection = null;

            string dbName = System.Web.Hosting.HostingEnvironment.MapPath("~/App_Data/dbEV.sqlite");

            if (!File.Exists(dbName))
            {
                SQLiteConnection.CreateFile(dbName); //Yoksa oluşturacaktır.
            }

            connection = new SQLiteConnection(@"Data Source=" + dbName + ";"); //Version3, indirdiğimiz SQLite'in  sürümünü temsil etmektedir.

            return connection;
        }

        // :::INTERNETTEN ALINMIŞTIR::::
        // MD5 ÖZETİ ALAN METHOD:
        public static string MD5Sifrele(string metin)
        {
            // MD5CryptoServiceProvider nesnenin yeni bir instance'sını oluşturalım.

            MD5CryptoServiceProvider md5 = new MD5CryptoServiceProvider();

            //Girilen veriyi bir byte dizisine dönüştürelim ve hash hesaplamasını yapalım.
            byte[] btr = Encoding.UTF8.GetBytes(metin);
            btr = md5.ComputeHash(btr);

            //byte'ları biriktirmek için yeni bir StringBuilder ve string oluşturalım.
            StringBuilder sb = new StringBuilder();


            //hash yapılmış her bir byte'ı dizi içinden alalım ve her birini hexadecimal string olarak formatlayalım.
            foreach (byte ba in btr)
            {
                sb.Append(ba.ToString("x2").ToLower());
            }

            //hexadecimal(onaltılık) stringi geri döndürelim.
            return sb.ToString();
        }


        // LİSTELE SAYFASININ TEMEL ELEMANI OLAN NESNE'NİN OLUŞTURULUP İSTEYENE GÖNDERİLDİĞİ METHOD
        public static Nesne Nesne_getir()
        {
            Nesne nesne = new Nesne();

            Ev_Tipi[] ev_listesi = Evleri_Bul();

            foreach (Ev_Tipi ev in ev_listesi)
            {
                ev.resimler = Resimleri_Bul(ev.evID);
            }

            nesne.ev_listesi = ev_listesi;

            return nesne;
        }


        // ORM KAVRAMI ILE ILGILI BIR KONU
        // VERITABAINDAKİ TABLODAN EV LISTESI OLUŞTURULUYOR
        public static Ev_Tipi[] Evleri_Bul()
        {
            List<Ev_Tipi> ev_listesi = new List<Ev_Tipi>();

            SQLiteConnection connection = KOD_SINIFI.BaglantiOlustur();
            connection.Open();
            SQLiteCommand command = connection.CreateCommand();
            command.CommandText = "select * from tblEV";
            command.ExecuteNonQuery();
            SQLiteDataReader data_reader = command.ExecuteReader();
            while (data_reader.Read())
            {
                Ev_Tipi ev = new Ev_Tipi();

                ev.evAciklama = data_reader["evAciklama"].ToString();
                ev.evAlan = Convert.ToInt32(data_reader["evAlan"]);
                ev.evBinaYasi = Convert.ToInt32(data_reader["evBinaYasi"]);
                ev.evBulKat = Convert.ToInt32(data_reader["evBulKat"]);
                ev.evEmlakTip = data_reader["evEmlakTip"].ToString();
                ev.evFiyat = Convert.ToInt32(data_reader["evFiyat"]);
                ev.evID = Convert.ToInt32(data_reader["evID"]);
                ev.evIL = data_reader["evIL"].ToString();

                ev_listesi.Add(ev);
            }
            connection.Close();

            return ev_listesi.ToArray();
        }

        // ORM KAVRAMI ILE ILGILI BIR KONU
        // VERITABAINDAKİ TABLODAN HER BIR EV ICIN RESIM LISTESI OLUŞTURULUYOR
        public static Resim_Tipi[] Resimleri_Bul(int evID)
        {
            List<Resim_Tipi> resim_listesi = new List<Resim_Tipi>();


            SQLiteConnection connection = KOD_SINIFI.BaglantiOlustur();

            connection.Open();
            SQLiteCommand command = connection.CreateCommand();
            command.CommandText = "select * from tblRESIM where resimEvID=" + evID;
            command.ExecuteNonQuery();
            SQLiteDataReader data_reader = command.ExecuteReader();
            while (data_reader.Read())
            {
                Resim_Tipi resim = new Resim_Tipi();

                resim.resimEvID = Convert.ToInt32(data_reader["resimEvID"]);
                resim.resimID = Convert.ToInt32(data_reader["resimID"]);
                resim.resimYol = data_reader["resimYol"].ToString();

                resim_listesi.Add(resim);
            }
            connection.Close();

            return resim_listesi.ToArray();
        }



        // ORM KAVRAMI ILE ILGILI BIR KONU
        // VERITABAINDAKİ TABLODAN RESIM LISTESI OLUŞTURULUYOR
        public static Resim_Tipi[] Veritabanindaki_Butun_Resimleri_Bul()
        {
            List<Resim_Tipi> resim_listesi = new List<Resim_Tipi>();


            SQLiteConnection connection = KOD_SINIFI.BaglantiOlustur();

            connection.Open();
            SQLiteCommand command = connection.CreateCommand();
            command.CommandText = "select * from tblRESIM";
            command.ExecuteNonQuery();
            SQLiteDataReader data_reader = command.ExecuteReader();
            while (data_reader.Read())
            {
                Resim_Tipi resim = new Resim_Tipi();

                resim.resimEvID = Convert.ToInt32(data_reader["resimEvID"]);
                resim.resimID = Convert.ToInt32(data_reader["resimID"]);
                resim.resimYol = data_reader["resimYol"].ToString();

                resim_listesi.Add(resim);
            }
            connection.Close();

            return resim_listesi.ToArray();
        }



        // VERITABANINDA SORGU ÇALIŞTIRMAK İÇİN KULLANILAN METHOD
        public static int SorguCalistir(string sorgu)
        {
            try
            {
                SQLiteConnection connection = KOD_SINIFI.BaglantiOlustur();

                connection.Open();
                SQLiteCommand command = connection.CreateCommand();
                command.CommandText = sorgu;
                command.ExecuteNonQuery();

                return 1;
            }
            catch
            {
                return 0;
            }
        }
    }
}