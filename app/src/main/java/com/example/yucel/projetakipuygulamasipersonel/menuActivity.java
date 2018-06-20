package com.example.yucel.projetakipuygulamasipersonel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class menuActivity extends AppCompatActivity {

    private Button yapilacaklarButon,yeniGorev,gorevlerButon,tamamlananlarButon;
    private TextView menuProjeBilgileriTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        yapilacaklarButon=findViewById(R.id.yapilacaklarButon);
        yeniGorev=findViewById(R.id.yeniGorevButon);
        menuProjeBilgileriTv = findViewById(R.id.menuProjeBilgileriTv);
        gorevlerButon = findViewById(R.id.gorevlerButon);
        tamamlananlarButon = findViewById(R.id.tamamlananlarButon);

        final Bundle menuProjeId = getIntent().getExtras();
        final int gelenProjeID = menuProjeId.getInt("projeID");

        menuProjeBilgileriTv.setText(String.valueOf(gelenProjeID));

        yapilacaklarButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent yapilacaklarSayfasi=new Intent(menuActivity.this, yapilacaklarActivity.class);
                yapilacaklarSayfasi.putExtra("projeID",gelenProjeID);
                startActivity(yapilacaklarSayfasi);
            }
        });


        yeniGorev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent yeniGorevSayfasi = new Intent(menuActivity.this,yeniGorevActivity.class);
                yeniGorevSayfasi.putExtra("projeID",gelenProjeID);
                startActivity(yeniGorevSayfasi);

            }
        });


        gorevlerButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gorevlerSayfasi = new Intent(menuActivity.this,gorevlerActivity.class);
                gorevlerSayfasi.putExtra("projeID",gelenProjeID);
                startActivity(gorevlerSayfasi);
            }
        });

        tamamlananlarButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tamamlananlarSayfasi = new Intent(menuActivity.this,tamamlananlarActivity.class);
                tamamlananlarSayfasi.putExtra("projeID",gelenProjeID);
                startActivity(tamamlananlarSayfasi);
            }
        });

    }
}
