package com.example.yucel.projetakipuygulamasipersonel;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class tamamlananlarAdaptor extends BaseAdapter {
    private FirebaseDatabase db;
    private LayoutInflater layoutInflater;
    private List<yapilacaklarDb> list;
    private Activity activity;
    int onay = 0, yeniOnay = 0;
    private int toplamYapilacakSayisi = 0, yapilacakSayisiOnayli = 0;

    public tamamlananlarAdaptor(Activity activity, List<yapilacaklarDb> mList) {
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        list = mList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        db = FirebaseDatabase.getInstance();
        View satirView;
        satirView = layoutInflater.inflate(R.layout.tamamlananlar_satir, null);
        final TextView yapilacakBasligiTv = satirView.findViewById(R.id.yapilacakBaslikTextView);
        Button tamamlananlarBilgisiButon = satirView.findViewById(R.id.tamamlanmaBilgisiButon);
        Button tamamlanmaIptalButon = satirView.findViewById(R.id.tamamlanmaIptalButon);
        ImageView yapilacakImageView = satirView.findViewById(R.id.yapilacakImageView);


        final yapilacaklarDb yapilacaklar = list.get(position);
        yapilacakBasligiTv.setText(yapilacaklar.getYapilacakAdi());
        final String gelenKey = yapilacaklar.getYapilacakKey();
        onay = yapilacaklar.getOnay();
        final int projeId = yapilacaklar.getProjeId();
        toplamYapilacakSayisi++;

        if (onay == 1) {
            yapilacakImageView.setImageResource(R.drawable.ok);
            tamamlananlarBilgisiButon.setText("Onaylandı");
            tamamlananlarBilgisiButon.setEnabled(false);
            tamamlanmaIptalButon.setEnabled(true);
            yapilacakSayisiOnayli++;
            tamamlananlarBilgisiButon.setBackgroundColor(Color.WHITE);

        } else {
            yapilacakImageView.setImageResource(R.drawable.not_ok);
            tamamlananlarBilgisiButon.setText("Onayla");
            tamamlananlarBilgisiButon.setEnabled(true);
            tamamlanmaIptalButon.setEnabled(false);
            tamamlanmaIptalButon.setBackgroundColor(Color.WHITE);
        }

        System.out.println(toplamYapilacakSayisi + " -- " + yapilacakSayisiOnayli);
        tamamlananlarBilgisi(projeId);



        tamamlananlarBilgisiButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference ref = db.getReference("yapilacak");
                ref.child(gelenKey).setValue(new yapilacaklarDb(yapilacakBasligiTv.getText().toString(), 1, projeId, gelenKey));
                tamamlananlarBilgisi(projeId);
            }
        });


        tamamlanmaIptalButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference ref = db.getReference("yapilacak");
                ref.child(gelenKey).setValue(new yapilacaklarDb(yapilacakBasligiTv.getText().toString(), 0, projeId, gelenKey));
                tamamlananlarBilgisi(projeId);
            }
        });


        return satirView;
    }

    public void tamamlananlarBilgisi(int projeId){
        DatabaseReference refToplamYapilacakSayisi = db.getReference("tamamlananlar").child(String.valueOf(projeId)).child("toplamYapilacakSayisi");
        DatabaseReference refToplamOnayliYapilacakSayisi = db.getReference("tamamlananlar").child(String.valueOf(projeId)).child("onayliYapilacakSayisi");
        DatabaseReference refProjeId = db.getReference("tamamlananlar").child(String.valueOf(projeId)).child("projeId");
        refToplamYapilacakSayisi.setValue(toplamYapilacakSayisi);
        refToplamOnayliYapilacakSayisi.setValue(yapilacakSayisiOnayli);
        refProjeId.setValue(projeId);
    }

}
