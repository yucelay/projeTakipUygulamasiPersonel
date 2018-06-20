package com.example.yucel.projetakipuygulamasipersonel;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class tamamlananlarActivity extends AppCompatActivity {
    private tamamlananlarAdaptor tamamlananlarAdaptor1;
    private List<yapilacaklarDb> tamamlananlar = new ArrayList<>();
    private ListView tamamlananlarListView;
    private FirebaseDatabase db;
    private int gelenProjeId ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tamamlananlar);
        tamamlananlarListView = findViewById(R.id.tamamlananlarListView);
        db = FirebaseDatabase.getInstance();

        Bundle tamamlananlarBundle = getIntent().getExtras();
        gelenProjeId = tamamlananlarBundle.getInt("projeID");

        tamamlananlarAdaptor1 = new tamamlananlarAdaptor(tamamlananlarActivity.this,tamamlananlar);
        tamamlananlarListView.setAdapter(tamamlananlarAdaptor1);

tumGorevlerMethod();


    }

    public void tumGorevlerMethod(){
        final DatabaseReference yapilacaklariGetir=db.getReference("yapilacak");
        yapilacaklariGetir.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tamamlananlar.clear();
                for (DataSnapshot gelenler:dataSnapshot.getChildren()){
                    int projeID = gelenler.getValue(yapilacaklarDb.class).getProjeId();
                    String yapilacakAdi = gelenler.getValue(yapilacaklarDb.class).getYapilacakAdi();
                    int onay = gelenler.getValue(yapilacaklarDb.class).getOnay();
                    String yapilacakKey = gelenler.getValue(yapilacaklarDb.class).getYapilacakKey();
                    if(gelenProjeId == projeID){
                        tamamlananlar.add(new yapilacaklarDb(yapilacakAdi,onay,gelenProjeId,yapilacakKey));
                    }
                }
                tamamlananlarAdaptor1.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }



}
