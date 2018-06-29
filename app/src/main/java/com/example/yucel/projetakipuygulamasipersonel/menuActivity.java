package com.example.yucel.projetakipuygulamasipersonel;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class menuActivity extends AppCompatActivity {

    private Button yapilacaklarButon, yeniGorev, gorevlerButon, tamamlananlarButon, testButon;
    private TextView menuProjeBilgileriTv;
    private FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        db = FirebaseDatabase.getInstance();
        yapilacaklarButon = findViewById(R.id.yapilacaklarButon);
        yeniGorev = findViewById(R.id.yeniGorevButon);
        menuProjeBilgileriTv = findViewById(R.id.menuProjeBilgileriTv);
        gorevlerButon = findViewById(R.id.gorevlerButon);
        tamamlananlarButon = findViewById(R.id.tamamlananlarButon);
        testButon = findViewById(R.id.testButon);

        final Bundle menuProjeId = getIntent().getExtras();
        final int gelenProjeID = menuProjeId.getInt("projeID");

        menuProjeBilgileriTv.setText(String.valueOf(gelenProjeID));


        DatabaseReference refProjeAdi = db.getReference("projeler").child(String.valueOf(gelenProjeID)).child("projeAdi");
        refProjeAdi.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                menuProjeBilgileriTv.setText("Proje Id   : #"+gelenProjeID + "\n" + "Proje Adı : " + dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        yapilacaklarButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent yapilacaklarSayfasi = new Intent(menuActivity.this, yapilacaklarActivity.class);
                yapilacaklarSayfasi.putExtra("projeID", gelenProjeID);
                startActivity(yapilacaklarSayfasi);
            }
        });


        yeniGorev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent yeniGorevSayfasi = new Intent(menuActivity.this, yeniGorevActivity.class);
                yeniGorevSayfasi.putExtra("projeID", gelenProjeID);
                startActivity(yeniGorevSayfasi);

            }
        });


        gorevlerButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gorevlerSayfasi = new Intent(menuActivity.this, gorevlerActivity.class);
                gorevlerSayfasi.putExtra("projeID", gelenProjeID);
                startActivity(gorevlerSayfasi);
            }
        });

        tamamlananlarButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tamamlananlarSayfasi = new Intent(menuActivity.this, tamamlananlarActivity.class);
                tamamlananlarSayfasi.putExtra("projeID", gelenProjeID);
                startActivity(tamamlananlarSayfasi);
            }
        });

        testButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent testSayfasi = new Intent(menuActivity.this, testActivity.class);
                testSayfasi.putExtra("projeID", gelenProjeID);
                startActivity(testSayfasi);
            }
        });

    }
}
