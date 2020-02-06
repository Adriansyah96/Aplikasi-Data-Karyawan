package com.gestaltsystech.karyawan_api;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;

import com.gestaltsystech.karyawan_api.api.APIConfig;
import com.gestaltsystech.karyawan_api.api.APIService;
import com.gestaltsystech.karyawan_api.model.DataItem;
import com.gestaltsystech.karyawan_api.model.POSTModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Edit extends AppCompatActivity {

    private EditText nama, email,ttl, telepon,alamat,password, konfirmas_password;
    private Spinner spinerjabatan;
    private RadioGroup radioGroupGender;
    AppCompatRadioButton radio_gender;
    private Button btn_edit;
    private RadioButton lakilaki,perempuan;
    private APIService apiservice;
    String Skey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        apiservice = APIConfig.getClient().create(APIService.class);

        Skey = getIntent().getStringExtra("key");

        nama = findViewById(R.id.edit_nama);
        email = findViewById(R.id.edit_email);
        ttl = findViewById(R.id.edit_ttl);
        telepon = findViewById(R.id.edit_telepon);
        alamat = findViewById(R.id.edit_alamat);
        lakilaki=findViewById(R.id.radio_male);
        perempuan=findViewById(R.id.radio_female);


//        password = findViewById(R.id.edit_password);
//        konfirmas_password = findViewById(R.id.edit_password_confirmation);
        radioGroupGender = findViewById(R.id.edit_radio_gender);
        spinerjabatan = findViewById(R.id.edit_spiner);
        btn_edit = findViewById(R.id.btn_edit);



        Call<DataItem> call = apiservice.getDetail(Skey);
        call.enqueue(new Callback<DataItem>() {
            @Override
            public void onResponse(Call<DataItem> call, Response<DataItem> response) {


                if (response.isSuccessful()){

//                    int selectedId = radioGroupGender.getCheckedRadioButtonId();
//                    radio_gender = findViewById(selectedId);




                    nama.setText(response.body().getNama());
                    email.setText(response.body().getEmail());
                    ttl.setText(response.body().getTanggalLahir());
                    telepon.setText(response.body().getTelepon());
                    alamat.setText(response.body().getAlamat());


                    if(response.body().getJabatan().equals("Direktur"))
                        spinerjabatan.setSelection(0);
                    else if(response.body().getJabatan().equals("Manager"))
                        spinerjabatan.setSelection(1);
                    else if (response.body().getJabatan().equals("HRD"))
                        spinerjabatan.setSelection(2);
                    else if (response.body().getJabatan().equals("Staff"))
                        spinerjabatan.setSelection(3);
                    else if (response.body().getJabatan().equals("Kepala Bagian"))
                        spinerjabatan.setSelection(4);
                    else if (response.body().getJabatan().equals("Karyawan"))
                        spinerjabatan.setSelection(5);
                    else if (response.body().getJabatan().equals("Office Boy"))
                        spinerjabatan.setSelection(6);

                   if (response.body().getKelamin().equals("Laki-laki"))
                       lakilaki.setChecked(true);
                   else perempuan.setChecked(true);

//                    if (response.body().getKelamin().equals("Laki-laki"))
//                        radio_gender.setSelected(true);
//                    else if (response.body().getKelamin().equals("Perempuan"))
//                        radio_gender.setSelected(true);




//                    if(radioGroupGender.equals(R.id.radio_male))
//                        radioGroupGender.check(R.id.radio_male);
//                    else
//                        radioGroupGender.check(R.id.radio_female);





//                    password.setText(response.body().getPassword());
//                    konfirmas_password.setText(response.body().getKonfirmasiPassword());





                }
            }


            @Override
            public void onFailure(Call<DataItem> call, Throwable t) {

                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }


        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit();
            }
        });




    }

    private void edit() {

        int selectedId = radioGroupGender.getCheckedRadioButtonId();
        radio_gender = findViewById(selectedId);

        Skey = getIntent().getStringExtra("key");

        final String Snama = nama.getText().toString().trim();
        final String Semail = email.getText().toString().trim();
        final String Sttl = ttl.getText().toString().trim();
        String Stelepon = telepon.getText().toString().trim();
        final String Salamat = alamat.getText().toString().trim();
        final String Sspiner = spinerjabatan.getSelectedItem().toString();
        final String Sgender = radio_gender.getText().toString().trim();
//        String Spassword = password.getText().toString().trim();
//        String Skonfirmasi = konfirmas_password.getText().toString().trim();

        if (Snama.isEmpty() || Semail.isEmpty() || Sttl.isEmpty() || Stelepon.isEmpty() || Salamat.isEmpty()||
                Sspiner.isEmpty() || Sgender.isEmpty()) {
            Toast.makeText(this, "Data Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            return;


//        }else if (!Spassword.equals(Skonfirmasi)) {
//            Toast.makeText(Edit.this,"Konfirmasi Password Tidak Sesuai",Toast.LENGTH_SHORT).show();
        }else {

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Mohon Tunggu....");
            progressDialog.show();


            Call<POSTModel> call = apiservice.updatedata(Skey,Snama,Semail,Sttl,Stelepon,Salamat,Sspiner,Sgender
                    );
            call.enqueue(new Callback<POSTModel>() {
                @Override
                public void onResponse(Call<POSTModel> call, Response<POSTModel> response) {
                    Intent intent = new Intent(Edit.this,DataKaryawan.class);
                    startActivity(intent);

                    Log.e("cek",response.body().getMessage());
                    Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<POSTModel> call, Throwable t) {
                    Log.e("cek",Skey+" "+Snama+" "+Semail+" "+Sttl+" "+Salamat+" "+Sspiner+" "+Sgender+"");
                    Log.e("cek",t.getMessage());
                    Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                }


            });
            finish();


        }




    }
}
