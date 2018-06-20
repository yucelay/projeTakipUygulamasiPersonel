package com.example.yucel.projetakipuygulamasipersonel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class yeniGorevActivity extends AppCompatActivity {
    private Button yeniGorevEkleButon;
    private FirebaseDatabase db;
    private EditText gorevBasligiEditText,gorevAciklamasiEditText;
    private int gelenProjeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yeni_gorev);
        yeniGorevEkleButon = findViewById(R.id.yeniGorevEkleButon);
        gorevBasligiEditText = findViewById(R.id.gorevBasligiEditText);
        gorevAciklamasiEditText = findViewById(R.id.gorevAciklamasiEditText);
        db = FirebaseDatabase.getInstance();

        Bundle yeniGorevBundle = getIntent().getExtras();
        gelenProjeId = yeniGorevBundle.getInt("projeID");
        System.out.println(gelenProjeId);

        yeniGorevEkleButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yeniGorevEkleMethod();
            }
        });
    }

    public void yeniGorevEkleMethod(){
        Date simdikiZaman = new Date();
        System.out.println(simdikiZaman.toString());
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String tarih=(df.format(simdikiZaman));

        DatabaseReference ref = db.getReference("gorevler");
        String key = ref.push().getKey();
        DatabaseReference refKey = db.getReference("gorevler/"+key);
        refKey.setValue(new gorevlerDb(gelenProjeId,tarih,gorevBasligiEditText.getText().toString(),gorevAciklamasiEditText.getText().toString()));
    }
}
