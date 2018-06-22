using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Yazlab2_4.Models
{
    public class Ev_Tipi
    {
        public int evID { get; set; }
        public string evIL { get; set; }
        public string evEmlakTip { get; set; }
        public int evAlan { get; set; }
        public int evOdaSayisi { get; set; }
        public int evBinaYasi { get; set; }
        public int evBulKat { get; set; }
        public int evFiyat { get; set; }
        public string evAciklama { get; set; }

        public Resim_Tipi[] resimler { get; set; }
    }
}