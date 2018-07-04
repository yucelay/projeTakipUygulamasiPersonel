package com.example.yucel.projetakipuygulamasipersonel;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private FirebaseDatabase db;
    private EditText projeIdEditText, sifreEditText;
    private Button girisYapButon;
    private String android_id;
    private Boolean beniHatirla = false;
    private Boolean checkBoxDurum = false;
    private CheckBox checkBox;
    private String mesaj = "";
    private TextView loginMesaj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = FirebaseDatabase.getInstance();
        projeIdEditText = findViewById(R.id.projeIdEditText);
        sifreEditText = findViewById(R.id.sifreEditText);
        girisYapButon = findViewById(R.id.girisYapButon);
        loginMesaj = findViewById(R.id.loginMesaj);
        checkBox = findViewById(R.id.checkBox);
        android_id = Settings.Secure.getString(MainActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);
        final DatabaseReference beniHatirlaData = db.getReference("benihatirlapersonel");
        beniHatirlaData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("beni hatirla data :" + dataSnapshot.child(android_id).child("email").getValue());

                if (dataSnapshot.child(android_id).child("projeId").getValue() != null && dataSnapshot.child(android_id).child("sifre").getValue() != null) {
                    projeIdEditText.setText(dataSnapshot.child(android_id).child("projeId").getValue().toString());
                    sifreEditText.setText(dataSnapshot.child(android_id).child("sifre").getValue().toString());
                    checkBoxDurum = true;
                    checkBox.setChecked(checkBoxDurum);
                    beniHatirla = true;
                } else {
                    projeIdEditText.setText("");
                    sifreEditText.setText("");
                    checkBoxDurum = false;
                    checkBox.setChecked(checkBoxDurum);
                    beniHatirla = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        girisYapButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                personelGetirMetod();
            }
        });
    }

    public void personelGetirMetod() {
        loginMesaj.setText("Lütfen Bilgilerini Kontrol Ediniz.");
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
                            loginMesaj.setTextColor(Color.GREEN);
                            loginMesaj.setText("Giriş Başarılı");
                            if (beniHatirla == true) {
                                DatabaseReference beniHatirlaRef = db.getReference("benihatirlapersonel");
                                beniHatirlaRef.child(android_id).child("cihazId").setValue(android_id);
                                beniHatirlaRef.child(android_id).child("projeId").setValue(projeIdEditText.getText().toString());
                                beniHatirlaRef.child(android_id).child("sifre").setValue(sifreEditText.getText().toString());
                            } else {
                                DatabaseReference beniHatirlaRef = db.getReference("benihatirlapersonel");
                                beniHatirlaRef.child(android_id).removeValue();
                                System.out.println("onayli degil");
                            }
                            Intent menuSayfasi = new Intent(MainActivity.this, menuActivity.class);
                            menuSayfasi.putExtra("projeID", projeID);
                            startActivity(menuSayfasi);
                            break;
                        } else {
                        }
                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }

    }

    public void itemClicked(View v) {
        CheckBox checkBox = (CheckBox) v;
        if (checkBox.isChecked()) {
            beniHatirla = true;
        } else {
            beniHatirla = false;
        }
    }

    public void itemClickedSifre(View v) {
        loginMesaj.setText("");
    }

    public void itemClickedProjeId(View v) {
        loginMesaj.setText("");
    }


}
