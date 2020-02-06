package com.gestaltsystech.karyawan_api;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gestaltsystech.karyawan_api.api.APIConfig;
import com.gestaltsystech.karyawan_api.api.APIService;
import com.gestaltsystech.karyawan_api.model.POSTModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private EditText email,password;
    private Button btn_login;
    private TextView text_register;
    private APIService apiservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        apiservice = APIConfig.getClient().create(APIService.class);

        email = findViewById(R.id.input_email);
        password = findViewById(R.id.input_password);
        btn_login = findViewById(R.id.btn_login);
        text_register = findViewById(R.id.link_register);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();


            }
        });

        text_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_register();

            }
        });
    }

    private void text_register() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing In....");
        progressDialog.show();

        Intent intent = new Intent(Login.this, Register.class);
        startActivity(intent);



    }

    private void login() {
        final String Semail = email.getText().toString().trim();
        final String Spassword = password.getText().toString().trim();

        if (Semail.isEmpty()||Spassword.isEmpty()){
            Toast.makeText(this,"Email Atau Password Tidak Terisi",Toast.LENGTH_SHORT).show();
            return;
        }
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sedang Masuk....");
        progressDialog.show();


        Call<POSTModel> call = apiservice.login_staff(Semail,Spassword);
        call.enqueue(new Callback<POSTModel>() {
            @Override
            public void onResponse(Call<POSTModel> call, Response<POSTModel> response) {
                progressDialog.dismiss();


                if (response.body().getMessage().equals("Berhasil Login")){
                    startActivity(new Intent(getApplicationContext(), DataKaryawan.class));
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

}
}

