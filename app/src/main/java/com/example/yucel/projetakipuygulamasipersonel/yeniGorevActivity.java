package com.example.yucel.projetakipuygulamasipersonel;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class yeniGorevActivity extends AppCompatActivity {
    private Button yeniGorevEkleButon, yeniGorevKapatButon;
    private FirebaseDatabase db;
    private EditText gorevBasligiEditText, gorevAciklamasiEditText;
    private TextView projeBilgisiTextView;
    private int gelenProjeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yeni_gorev);
        yeniGorevEkleButon = findViewById(R.id.yeniGorevEkleButon);
        gorevBasligiEditText = findViewById(R.id.gorevBasligiEditText);
        gorevAciklamasiEditText = findViewById(R.id.gorevAciklamasiEditText);
        yeniGorevKapatButon = findViewById(R.id.yeniGorevKapatButon);
        projeBilgisiTextView = findViewById(R.id.projeBilgisiTextView);
        db = FirebaseDatabase.getInstance();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width / 1.2), (int) (height / 1.6));

        Bundle yeniGorevBundle = getIntent().getExtras();
        gelenProjeId = yeniGorevBundle.getInt("projeID");
        System.out.println(gelenProjeId);

        //projeBilgisiTextView.setText(String.valueOf(gelenProjeId));
        DatabaseReference refProjeAdi = db.getReference("projeler").child(String.valueOf(gelenProjeId)).child("projeAdi");
        refProjeAdi.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                projeBilgisiTextView.setText(
                        "Proje Id  :#" + gelenProjeId + "\n" + "Proje Adı :" + dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        yeniGorevKapatButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        yeniGorevEkleButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(gorevAciklamasiEditText.getText().toString().trim().equals("") || gorevBasligiEditText.getText().toString().trim().equals(""))) {
                    yeniGorevEkleMethod();
                } else {
                    Toast.makeText(yeniGorevActivity.this, "Lütfen tüm alanları doldurunuz.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void yeniGorevEkleMethod() {
        Date simdikiZaman = new Date();
        System.out.println(simdikiZaman.toString());
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String tarih = (df.format(simdikiZaman));
        DatabaseReference ref = db.getReference("gorevler");
        String key = ref.push().getKey();
        DatabaseReference refKey = db.getReference("gorevler/" + key);
        refKey.setValue(new gorevlerDb(gelenProjeId, tarih, gorevBasligiEditText.getText().toString(), gorevAciklamasiEditText.getText().toString(), key));
        Toast.makeText(this, "Görev Başarıyla Eklendi.", Toast.LENGTH_SHORT).show();
        gorevAciklamasiEditText.setText("");
        gorevBasligiEditText.setText("");
    }
}
