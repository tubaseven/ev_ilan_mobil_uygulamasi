using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Data.SQLite;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using Yazlab2_4.Code;
using Yazlab2_4.Models;

namespace Yazlab2_4.Controllers
{
    public class YazlabController : ApiController
    {

        // Bu METHOD veritabanının güncellenip güncellenmediğini anlamak için,
        // veritabanının çıktısını alıp onu MD5 özetleme(hashing) fonksiyonundan geçirir.
        // Elde ettiği sonucu json tipinde string olarak geri döner.
        [HttpGet]
        public string Kontrol()
        {
            Nesne nesne = KOD_SINIFI.Nesne_getir();
            string json_data = JsonConvert.SerializeObject(nesne);
            string md5_json_data = KOD_SINIFI.MD5Sifrele(json_data);
            return md5_json_data; 
        }



        // bu method ise veritabanındaki evlerin listesini oluşturup json tipinde ev listesi olarak döner.
        // nesne sınıfının tek elemanı ev listesidir.
        [HttpGet]
        public Nesne Listele()
        {
            // KOD sınıfında bulunan nesne getir methodu ile nesne alınıyor.
            // Bu nesnenin içerisinde ev listesi bulunmaktadır.
            Nesne nesne = KOD_SINIFI.Nesne_getir();
            return nesne;
        }

    }
}
