package com.example.yucel.projetakipuygulamasipersonel;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class testActivity extends AppCompatActivity {
    private FirebaseDatabase db;
    private Button butonTestActivity;
    private TextView projeBilgisiTestSayfasi,projeTestDurum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        db = FirebaseDatabase.getInstance();
        butonTestActivity = findViewById(R.id.butonTestActivity);
        projeBilgisiTestSayfasi = findViewById(R.id.projeBilgisiTestSayfasi);
        projeTestDurum = findViewById(R.id.projeTestDurum);
        Bundle testBundle = getIntent().getExtras();
        final int projeID = testBundle.getInt("projeID");
        System.out.println(projeID);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width / 1.2), (int) (height / 3));


        DatabaseReference refProjeAdi = db.getReference("projeler").child(String.valueOf(projeID)).child("projeAdi");
        refProjeAdi.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                projeBilgisiTestSayfasi.setText(dataSnapshot.getValue().toString() + " Projesini Test Et");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        projeTesti(String.valueOf(projeID));
        butonTestActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = String.valueOf(projeID);
                DatabaseReference refOnay = db.getReference("testedilmisprojeler/" + key + "/onayDurumu");
                DatabaseReference refProjeId = db.getReference("testedilmisprojeler/" + key + "/projeID");
                refOnay.setValue(1);
                refProjeId.setValue(projeID);
            }
        });

    }


    public void projeTesti(String projeID) {
        DatabaseReference refTest = db.getReference("testedilmisprojeler").child(projeID).child("projeID");
        refTest.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue() == null) {
                    System.out.println("veri yok");
                    projeTestDurum.setText("Test Edilmedi");
                }else{
                    System.out.println("veri bulundu");
                    butonTestActivity.setEnabled(false);
                    projeTestDurum.setTextColor(Color.GREEN);
                    projeTestDurum.setText("Test Edildi");
                    butonTestActivity.setText("");
                    butonTestActivity.setBackgroundResource(R.drawable.onayla_icon);
                    //butonTestActivity.setVisibility(View.INVISIBLE);
                }
                System.out.println("123 " + dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
