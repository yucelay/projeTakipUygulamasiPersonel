package com.example.yucel.projetakipuygulamasipersonel;

public class gorevlerDb {
    int projeId;
    String gorev_tarihi,gorev_basligi,gorev_aciklamasi,gorev_key;

    public gorevlerDb() {
    }

    public gorevlerDb(int projeId, String gorev_tarihi, String gorev_basligi, String gorev_aciklamasi,String gorev_key) {
        this.projeId = projeId;
        this.gorev_tarihi = gorev_tarihi;
        this.gorev_basligi = gorev_basligi;
        this.gorev_aciklamasi = gorev_aciklamasi;
        this.gorev_key = gorev_key;
    }


    public int getProjeId() {
        return projeId;
    }

    public void setProjeId(int projeId) {
        this.projeId = projeId;
    }

    public String getGorev_tarihi() {
        return gorev_tarihi;
    }

    public void setGorev_tarihi(String gorev_tarihi) {
        this.gorev_tarihi = gorev_tarihi;
    }

    public String getGorev_basligi() {
        return gorev_basligi;
    }

    public void setGorev_basligi(String gorev_basligi) {
        this.gorev_basligi = gorev_basligi;
    }

    public String getGorev_aciklamasi() {
        return gorev_aciklamasi;
    }

    public void setGorev_aciklamasi(String gorev_aciklamasi) {
        this.gorev_aciklamasi = gorev_aciklamasi;
    }

    public String getGorev_key() {
        return gorev_key;
    }

    public void setGorev_key(String gorev_key) {
        this.gorev_key = gorev_key;
    }
}
