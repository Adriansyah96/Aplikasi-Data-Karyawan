package com.gestaltsystech.karyawan_api;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;

import com.gestaltsystech.karyawan_api.api.APIConfig;
import com.gestaltsystech.karyawan_api.api.APIService;
import com.gestaltsystech.karyawan_api.model.POSTModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {
    private EditText nama, email,ttl, telepon,alamat,password, konfirmas_password;
    private Spinner spinerjabatan;
    private RadioGroup radioGroupGender;
    AppCompatRadioButton radio_gender;
    private Button register;
    private APIService apiservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        apiservice = APIConfig.getClient().create(APIService.class);

        nama = findViewById(R.id.input_nama);
        email = findViewById(R.id.input_email);
        ttl = findViewById(R.id.input_ttl);
        telepon = findViewById(R.id.input_telepon);
        alamat = findViewById(R.id.input_alamat);
        password = findViewById(R.id.input_password);
        konfirmas_password = findViewById(R.id.input_password_confirmation);
        radioGroupGender = findViewById(R.id.radio_gender);
        spinerjabatan = findViewById(R.id.spiner);
        register = findViewById(R.id.btn_register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    private void signUp() {

        int selectedId = radioGroupGender.getCheckedRadioButtonId();
        radio_gender = findViewById(selectedId);

        String Snama = nama.getText().toString().trim();
        String Semail = email.getText().toString().trim();
        String Sttl = ttl.getText().toString().trim();
        String Stelepon = telepon.getText().toString().trim();
        String Salamat = alamat.getText().toString().trim();
        String Sspiner = spinerjabatan.getSelectedItem().toString();
        String Sgender = radio_gender.getText().toString().trim();
        String Spassword = password.getText().toString().trim();
        String Skonfirmasi = konfirmas_password.getText().toString().trim();

            if (Snama.isEmpty() || Semail.isEmpty() || Sttl.isEmpty() || Stelepon.isEmpty() || Salamat.isEmpty()||
                    Sspiner.isEmpty() || Sgender.isEmpty() || Spassword.isEmpty() || Skonfirmasi.isEmpty()) {
                Toast.makeText(this, "Data Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                return;


            }else if (!Spassword.equals(Skonfirmasi)) {
                Toast.makeText(Register.this,"Konfirmasi Password Tidak Sesuai",Toast.LENGTH_SHORT).show();
            }else {

            Call<POSTModel> call = apiservice.tambah_staff(Snama,Semail,Sttl,Stelepon,Salamat,Sspiner,Sgender,
                    Spassword,Skonfirmasi);
            call.enqueue(new Callback<POSTModel>() {
                @Override
                public void onResponse(Call<POSTModel> call, Response<POSTModel> response) {
                    Intent intent = new Intent(Register.this,Login.class);
                    startActivity(intent);

                    Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<POSTModel> call, Throwable t) {

                    Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                }


            });

                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Mohon Tunggu....");
                progressDialog.show();
                finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Register.this, Login.class);
        startActivity(intent);
    }
}
