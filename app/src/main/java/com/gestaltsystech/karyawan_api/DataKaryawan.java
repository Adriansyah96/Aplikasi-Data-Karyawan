package com.gestaltsystech.karyawan_api;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gestaltsystech.karyawan_api.adapter.AdapterKaryawan;
import com.gestaltsystech.karyawan_api.api.APIConfig;
import com.gestaltsystech.karyawan_api.api.APIService;
import com.gestaltsystech.karyawan_api.model.DataItem;
import com.gestaltsystech.karyawan_api.model.KaryawanResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataKaryawan extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<DataItem> listItem;
    private RecyclerView.LayoutManager layoutManager;
    private AdapterKaryawan adapter;
    private APIService apiservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_karyawan);

        apiservice = APIConfig.getClient().create(APIService.class);

        recyclerView = findViewById(R.id.recycleview_karyawan);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Memuat....");
        progressDialog.show();


        Call<KaryawanResponse> call = apiservice.getList();
        call.enqueue(new Callback<KaryawanResponse>() {
            @Override
            public void onResponse(Call<KaryawanResponse> call, Response<KaryawanResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    listItem = response.body().getData();

                    adapter = new AdapterKaryawan(listItem, DataKaryawan.this);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(adapter);


                }
            }


            @Override
            public void onFailure(Call<KaryawanResponse> call, Throwable t) {

                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }


        });



    }
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Anda ingin keluar?, Anda akan kembali kehalaman loggin")
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {
            Intent intent = new Intent(DataKaryawan.this, Login.class);
            startActivity(intent);
            finish();
        }
    })
            .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {
            dialog.cancel();
        }
    });
    AlertDialog alert = builder.create();
        alert.show();
}

}



