package com.example.yucel.projetakipuygulamasipersonel;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class gorevlerAdaptor extends BaseAdapter{
    private LayoutInflater layoutInflater;
    private FirebaseDatabase db;
    private List<gorevlerDb> list;
    private Activity activity;
    Context context;
    public gorevlerAdaptor(Activity activity, List<gorevlerDb> mList){
        layoutInflater=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        list=mList;
        this.activity=activity;
        context = this.activity;

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
        final View satirView;
        satirView=layoutInflater.inflate(R.layout.gorevler_satir, null);
        TextView tvGorevBasligi = satirView.findViewById(R.id.gorevBaslikTextView);
        TextView tvGorevAciklamasi = satirView.findViewById(R.id.gorevAciklamasiTextView);
        TextView tvGorevTarihi = satirView.findViewById(R.id.gorevTarihiTextView);
        Button gorevSilButon = satirView.findViewById(R.id.gorevSilButon);


        final gorevlerDb gorevler=list.get(position);
        tvGorevBasligi.setText(gorevler.getGorev_basligi());
        tvGorevAciklamasi.setText(gorevler.getGorev_aciklamasi().toString());
        tvGorevTarihi.setText(gorevler.getGorev_tarihi());
        final String gorevKey = gorevler.getGorev_key();


        db = FirebaseDatabase.getInstance();

        gorevSilButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("gorev sil");
                final DatabaseReference ref = db.getReference("gorevler").child(gorevKey);
                ref.removeValue();
                 Toast.makeText(context,"Görev Silindi",Toast.LENGTH_SHORT).show();
            }
        });


        return satirView;
    }

}
