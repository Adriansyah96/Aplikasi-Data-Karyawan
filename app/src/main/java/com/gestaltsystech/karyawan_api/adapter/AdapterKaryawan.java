package com.gestaltsystech.karyawan_api.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.gestaltsystech.karyawan_api.DetailKaryawan;
import com.gestaltsystech.karyawan_api.R;
import com.gestaltsystech.karyawan_api.model.DataItem;

import java.util.List;

public class AdapterKaryawan extends RecyclerView.Adapter<AdapterKaryawan.KaryawanViewHolder> {
    private List<DataItem> datalist;
    private Context context;

    public AdapterKaryawan(List<DataItem> datalist, Context context) {
        this.datalist = datalist;
        this.context = context;
    }

    @Override
    public AdapterKaryawan.KaryawanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_karyawan,parent,false);
        KaryawanViewHolder vh = new KaryawanViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterKaryawan.KaryawanViewHolder holder, final int position) {
        final String Snama = datalist.get(position).getNama();
        final String Salamat = datalist.get(position).getAlamat();
        final String Sjabatan = datalist.get(position).getJabatan();
        final String Semail = datalist.get(position).getEmail();

        final String namaid = datalist.get(position).getId();
        final String keyemail = datalist.get(position).getEmail();
        final String keypassword = datalist.get(position).getPassword();

        holder.nama.setText(Snama);
        holder.alamat.setText(Salamat);
        holder.jabatan.setText(Sjabatan);
        holder.email.setText(Semail);

        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), DetailKaryawan.class);
                intent.putExtra("key", namaid);
                intent.putExtra("keyemail", keyemail);
                intent.putExtra("keypassword", keypassword);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return  datalist.size();
    }

    public class KaryawanViewHolder extends RecyclerView.ViewHolder {
        private TextView nama, alamat, jabatan, email;
        private CardView cardview;
        public KaryawanViewHolder(@NonNull View itemView) {
            super(itemView);

            nama= itemView.findViewById(R.id.txt_nama_karyawan);
            alamat= itemView.findViewById(R.id.txt_alamat_karyawan);
            jabatan= itemView.findViewById(R.id.txt_jabatan_karyawan);
            email= itemView.findViewById(R.id.txt_email_karyawan);
            cardview = itemView.findViewById(R.id.card_karyawan);
        }
    }
}
