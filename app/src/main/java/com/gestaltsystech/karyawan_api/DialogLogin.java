package com.gestaltsystech.karyawan_api;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gestaltsystech.karyawan_api.api.APIConfig;
import com.gestaltsystech.karyawan_api.api.APIService;
import com.gestaltsystech.karyawan_api.model.POSTModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DialogLogin extends AppCompatActivity {

    private EditText dialog_email, dialog_password;
    private Button btn_dialog_login;
    private APIService apiservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_login);

        apiservice = APIConfig.getClient().create(APIService.class);

        String Skeyemail = getIntent().getStringExtra("keyemail");
        String Skeypassword = getIntent().getStringExtra("keypassword");

        dialog_email = findViewById(R.id.dialog_email);
        dialog_password = findViewById(R.id.dialog_password);
        btn_dialog_login = findViewById(R.id.dialog_btn_login);

        btn_dialog_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }
    private void login() {
        final String Semail = dialog_email.getText().toString().trim();
        final String Spassword = dialog_password.getText().toString().trim();

        if (Semail.isEmpty()||Spassword.isEmpty()){
            Toast.makeText(this,"Email Atau Password Tidak Terisi",Toast.LENGTH_SHORT).show();
            return;
        }
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sedang Masuk....");
        progressDialog.show();


        final String Skey = getIntent().getStringExtra("key");
        final String Skeyemail = getIntent().getStringExtra("keyemail");
        final String Skeypassword = getIntent().getStringExtra("keypassword");



//        if (dialog_email.equals(Skeyemail) && dialog_password.equals(Skeypassword)){
//            Intent intent = new Intent(DialogLogin.this, Edit.class);
//            startActivity(intent);
//
//            Toast.makeText(this,"Berhasil Masuk",Toast.LENGTH_SHORT).show();
//        }else{
//            Toast.makeText(this,"Gagal Masuk",Toast.LENGTH_SHORT).show();
//        }





        Call<POSTModel> call = apiservice.cekakses(Skey,Semail,Spassword);
        call.enqueue(new Callback<POSTModel>() {
            @Override
            public void onResponse(Call<POSTModel> call, Response<POSTModel> response) {
                progressDialog.dismiss();


                if (response.body().getMessage().equals("Akses Diterima")){
//                  startActivity(new Intent(getApplicationContext(), Edit.class));

                    Intent intent = new Intent(DialogLogin.this, Edit.class);
                    intent.putExtra("key",Skey);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<POSTModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }


        });
//
    }
}
