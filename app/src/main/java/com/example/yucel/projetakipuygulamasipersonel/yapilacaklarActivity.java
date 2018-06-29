package com.example.yucel.projetakipuygulamasipersonel;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class yapilacaklarActivity extends AppCompatActivity {
    private Button yapilacakKaydetButon;
    private EditText yapilacakEditText;
    private FirebaseDatabase db;
    private ListView yapilacakListView;
    private List<String> yapilacakArray = new ArrayList<>();
    private ArrayAdapter<String> yapilacaklarAdapter;
    private int gelenProjeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yapilacaklar);
        db = FirebaseDatabase.getInstance();
        yapilacakKaydetButon = findViewById(R.id.yapilacakKaydetButon);
        yapilacakEditText = findViewById(R.id.yapilacakEditText);
        yapilacakListView = findViewById(R.id.yapilacakListView);
        yapilacaklarAdapter = new ArrayAdapter<>(yapilacaklarActivity.this, R.layout.listview_simple_row,yapilacakArray);
        yapilacakListView.setAdapter(yapilacaklarAdapter);

        final Bundle yapilacaklarProjeID = getIntent().getExtras();
        gelenProjeId = yapilacaklarProjeID.getInt("projeID");

        yapilacakKaydetButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(yapilacakEditText.getText().toString().trim().equals("")){
                    Toast.makeText(yapilacaklarActivity.this,"Alanı boş geçmeyiniz",Toast.LENGTH_SHORT).show();
                }else{
                    yapilacakEkle(yapilacakEditText.getText().toString());
                    Toast.makeText(yapilacaklarActivity.this,"Kaydedildi",Toast.LENGTH_SHORT).show();
                }

            }
        });
yapilacaklariGetirMetodu();


    }
    public void yapilacakEkle(String yapilacakAdi){
       DatabaseReference yapilacakRef = db.getReference("yapilacak");
       String yapilacakKey = yapilacakRef.push().getKey();
       DatabaseReference yapilacakRefKey = db.getReference("yapilacak/"+ yapilacakKey);
       yapilacakRefKey.setValue(new yapilacaklarDb(yapilacakAdi,0,gelenProjeId,yapilacakKey));
       yapilacakEditText.setText("");
    }

    public void yapilacaklariGetirMetodu(){
        DatabaseReference yapilacaklariGetir=db.getReference("yapilacak");
        yapilacaklariGetir.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                yapilacakArray.clear();
                int sira=0;
                for (DataSnapshot gelenler:dataSnapshot.getChildren()){
                    int projeID = gelenler.getValue(yapilacaklarDb.class).getProjeId();
                    if(projeID==gelenProjeId){
                        sira++;
                        String personelAdiSoyadi = gelenler.getValue(yapilacaklarDb.class).getYapilacakAdi();
                        yapilacakArray.add(sira+ ". "+personelAdiSoyadi);
                        yapilacaklarAdapter.notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
