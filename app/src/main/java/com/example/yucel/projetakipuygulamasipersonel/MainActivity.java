package com.example.yucel.projetakipuygulamasipersonel;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private FirebaseDatabase db;
    private EditText projeIdEditText,sifreEditText;
    private Button girisYapButon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = FirebaseDatabase.getInstance();
        projeIdEditText = findViewById(R.id.projeIdEditText);
        sifreEditText = findViewById(R.id.sifreEditText);
        girisYapButon = findViewById(R.id.girisYapButon);

        girisYapButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                personelGetirMetod();

               // Intent intent = new Intent(MainActivity.this, menuActivity.class);
               // startActivity(intent);

            }
        });
    }


    public void personelGetirMetod() {

        if (projeIdEditText.getText().toString().trim().equals("") || sifreEditText.getText().toString().trim().equals("")) {
            Toast.makeText(MainActivity.this, "Lütfen Tüm Alanları Doldurunuz.", Toast.LENGTH_SHORT).show();
        } else {

            DatabaseReference yapilacaklariGetir = db.getReference("personel");
            yapilacaklariGetir.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot gelenler : dataSnapshot.getChildren()) {
                        int projeID = gelenler.getValue(userDb.class).getProjeID();
                        String sifre = gelenler.getValue(userDb.class).getP_sifre();

                        if (Integer.parseInt(projeIdEditText.getText().toString()) == projeID && sifreEditText.getText().toString().equals(sifre)) {
                            Toast.makeText(MainActivity.this, "Giriş Başarılı", Toast.LENGTH_SHORT).show();
                            Intent menuSayfasi = new Intent(MainActivity.this,menuActivity.class);
                            menuSayfasi.putExtra("projeID",projeID);
                            startActivity(menuSayfasi);
                            break;
                        } else {
                            //  Toast.makeText(MainActivity.this,"Kullanıcı Bulunamadı",Toast.LENGTH_SHORT).show();
                            //  System.out.println("kayit bulunamadi");
                        }
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
    }

}
