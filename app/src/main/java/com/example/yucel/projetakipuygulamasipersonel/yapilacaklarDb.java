package com.example.yucel.projetakipuygulamasipersonel;

public class yapilacaklarDb {
    String yapilacakAdi,yapilacakKey;
    int onay,projeId;


    public yapilacaklarDb() {
    }



    public yapilacaklarDb(String yapilacakAdi, int onay, int projeId,String yapilacakKey){
        this.yapilacakAdi = yapilacakAdi;
        this.onay = onay;
        this.projeId = projeId;
        this.yapilacakKey = yapilacakKey;
    }

    public String getYapilacakAdi() {
        return yapilacakAdi;
    }

    public void setYapilacakAdi(String yapilacakAdi) {
        this.yapilacakAdi = yapilacakAdi;
    }

    public int getOnay(){
        return onay;
    }

    public void setOnay(int onay){
        this.onay = onay ;
    }

    public int getProjeId() {
        return projeId;
    }

    public void setProjeId(int projeId) {
        this.projeId = projeId;
    }

    public String getYapilacakKey() {
        return yapilacakKey;
    }

    public void setYapilacakKey(String yapilacakKey) {
        this.yapilacakKey = yapilacakKey;
    }
}
