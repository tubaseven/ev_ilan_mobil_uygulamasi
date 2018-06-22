using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using Yazlab2_4.Code;
using Yazlab2_4.Models;

namespace Yazlab2_4.Controllers
{
    public class SorguController : Controller
    {
        //
        // GET: /Sorgu/

        // EKRANDAKİ TABLOLARIN LİSTELENMESİNİ SAĞLAYAN SAYFA
        public ActionResult Index()
        {
            Nesne verilerim = KOD_SINIFI.Nesne_getir();
            ViewData["ev_listesi"] = verilerim.ev_listesi;

            Resim_Tipi[] veritabanindaki_resimler = KOD_SINIFI.Veritabanindaki_Butun_Resimleri_Bul();
            ViewData["resim_listesi"] = veritabanindaki_resimler;
            return View();
        }


        // RESIM EKLENMESİ İÇİN ÇAĞIRILAN SAYFA
        [HttpPost]
        public ActionResult Resim_Ekle(FormCollection form_collection)
        {
            try
            {
                int resim_ev_id = Convert.ToInt32(form_collection["resimEvID"]);
                string resim_yol = form_collection["resimYol"];

                string sorgu = "INSERT INTO tblRESIM (resimID,resimYol,resimEvID) VALUES (null,'" + resim_yol + "'," + resim_ev_id + ")";

                KOD_SINIFI.SorguCalistir(sorgu);

                return RedirectToAction("Index");
            }
            catch
            {
                return RedirectToAction("Index");
            }
        }


        // RESIM SİLİNMESİ İÇİN ÇAĞIRILAN SAYFA
        [HttpPost]
        public ActionResult Resim_Sil(FormCollection form_collection)
        {
            try
            {



                int resim_id = Convert.ToInt32(form_collection["resimID"]);

                string sorgu = "DELETE from tblRESIM where resimID=" + resim_id;

                KOD_SINIFI.SorguCalistir(sorgu);

                return RedirectToAction("Index");
            }
            catch
            {
                return RedirectToAction("Index");
            }
        }


        // EV EKLENMESİ İÇİN ÇAĞIRILAN SAYFA
        [HttpPost]
        public ActionResult Ev_Ekle(FormCollection form_collection)
        {
            try
            {


                string ev_il = form_collection["il"];
                string ev_emlaktip = form_collection["emlaktip"];
                string ev_alan = form_collection["alan"];
                string ev_binayasi = form_collection["binayasi"];
                string ev_bulkat = form_collection["bulkat"];
                string ev_odasayisi = form_collection["odasayisi"];
                string ev_fiyat = form_collection["fiyat"];
                string ev_aciklama = form_collection["aciklama"];

                string sorgu = "INSERT INTO tblEV ( evID,evIL,evEmlakTip,evAlan,evOdaSayisi,evBinaYasi,evBulKat,evFiyat,evAciklama) VALUES (null,'" + ev_il + "','" + ev_emlaktip + "'," + ev_alan + "," + ev_odasayisi + "," + ev_binayasi + "," + ev_bulkat + ", " + ev_fiyat + ",'" + ev_aciklama + "')";

                KOD_SINIFI.SorguCalistir(sorgu);

                return RedirectToAction("Index");

            }
            catch
            {
                return RedirectToAction("Index");
            }
        }

        // RESIM SİLİNMESİ İÇİN ÇAĞIRILAN SAYFA
        [HttpPost]
        public ActionResult Ev_Sil(FormCollection form_collection)
        {
            try
            {

                int ev_id = Convert.ToInt32(form_collection["evID"]);

                string sorgu = "DELETE from tblEV where evID=" + ev_id;

                KOD_SINIFI.SorguCalistir(sorgu);

                return RedirectToAction("Index");

            }
            catch
            {
                return RedirectToAction("Index");
            }
        }
    }
}
