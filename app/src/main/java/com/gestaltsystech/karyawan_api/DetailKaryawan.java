package com.gestaltsystech.karyawan_api;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.gestaltsystech.karyawan_api.api.APIConfig;
import com.gestaltsystech.karyawan_api.api.APIService;
import com.gestaltsystech.karyawan_api.maps.MapsActivity;
import com.gestaltsystech.karyawan_api.model.DataItem;
import com.gestaltsystech.karyawan_api.model.POSTModel;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailKaryawan extends AppCompatActivity {

    private TextView id;
    private TextView nama;
    private TextView email;
    private TextView tanggal_lahir;
    private TextView telepon;
    private TextView alamat;
    private TextView jabatan;
    private TextView kelamin;
    private TextView password;
    private TextView koordinat;
    private ImageView avatar;
    private Button maps, edit, delete;

    private APIService apiservice;
    String Skey;

    String mediaPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_karyawan);

        apiservice = APIConfig.getClient().create(APIService.class);

        Skey = getIntent().getStringExtra("key");
        String Skeyemail = getIntent().getStringExtra("keyemail");
        String Skeypassword = getIntent().getStringExtra("keypassword");



        id = findViewById(R.id.txt_id_karyawan);
        nama = findViewById(R.id.txt_nama_karyawan);
        email = findViewById(R.id.txt_email_karyawan);
        tanggal_lahir = findViewById(R.id.txt_tanggal_lahir_karyawan);
        telepon = findViewById(R.id.txt_telepon_karyawan);
        alamat = findViewById(R.id.txt_alamat_karyawan);
        jabatan = findViewById(R.id.txt_jabatan_karyawan);
        kelamin = findViewById(R.id.txt_kelamin_karyawan);
        password = findViewById(R.id.txt_password_karyawan);
        koordinat = findViewById(R.id.txt_koordinat_karyawan);
        avatar =  findViewById(R.id.img_detail_karyawan);



        maps = findViewById(R.id.btn_maps);
        edit = findViewById(R.id.btn_edit);
        delete = findViewById(R.id.btn_delete);

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Memuat....");
        progressDialog.show();

        Call<DataItem> call = apiservice.getDetail(Skey);
        call.enqueue(new Callback<DataItem>() {
            @Override
            public void onResponse(Call<DataItem> call, Response<DataItem> response) {


                if (response.isSuccessful()) {
                    progressDialog.dismiss();

                    id.setText(response.body().getId());
                    nama.setText(response.body().getNama());
                    email.setText(response.body().getEmail());
                    tanggal_lahir.setText(response.body().getTanggalLahir());
                    telepon.setText(response.body().getTelepon());
                    alamat.setText(response.body().getAlamat());
                    jabatan.setText(response.body().getJabatan());
                    kelamin.setText(response.body().getKelamin());
                    password.setText(response.body().getPassword());
                    koordinat.setText(response.body().getKordinat());

                    if (response.body().getAvatar() == null){
                        avatar.setImageResource(R.drawable.karyawan);
                    } else {
                        Glide.with(getApplicationContext())
                                .load(response.body().getAvatar())
                                .into(avatar);

                        Toast.makeText(getApplicationContext(),"Dataload",Toast.LENGTH_SHORT).show();
                    }


                }
            }


            @Override
            public void onFailure(Call<DataItem> call, Throwable t) {

                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }


        });


        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maps();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });
    }

    private void chooseImage() {
        if (!isStoragePermissionGranted()) {
            return;
        }

        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 0);
    }


    // 3. ketika gambar dipilih
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0 && resultCode == RESULT_OK && data != null && data.getData() != null )
        {
            Uri selectedImage = data.getData();

            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            mediaPath = cursor.getString(columnIndex);

            avatar.setImageBitmap(BitmapFactory.decodeFile(mediaPath));

            cursor.close();

            updateAvatar();
        }
    }





    private void updateAvatar() {

        // String Sid = getIntent().getStringExtra("key");

        //defining a progress dialog to show while signing up
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating avatar...");
        progressDialog.show();

        //Create a file object using file path
        File file = new File(mediaPath);
        // Create a request body with file and image media type
        RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);
        // Create MultipartBody.Part using file request-body,file name and part name
        MultipartBody.Part avatar = MultipartBody.Part.createFormData("avatar", file.getName(), fileReqBody);

        //Defining retrofit api service
        //APIService service = APIConfig.getClient(this).create(APIService.class);

        //defining the call
        //Call<DataKaryawanResponse> call = apiservice.getavatar(Sid);

        Call<POSTModel> call = apiservice.updateAvatar(avatar, Skey);

        call.enqueue(new Callback<POSTModel>() {
            @Override
            public void onResponse(Call<POSTModel> call, Response<POSTModel> response) {
                progressDialog.dismiss();


                if (response.body().getMessage().equals("FILE BERHASIL DI UPLOAD")){
                    Toast.makeText(getApplicationContext(),"FILE BERHASIL DI UPLOAD",Toast.LENGTH_SHORT).show();
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


    // 1 . Buat Permission
    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.v(this.getPackageName(),"Permission is granted");
                return true;
            } else {
                Log.v(this.getPackageName(),"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(this.getPackageName(),"Permission is granted");
            return true;
        }
    }

    // 1 . Buat Permission
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Log.v(this.getClass().getName(),"Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
    }


    private void edit() {
        final String Skey = getIntent().getStringExtra("key");
        String Skeyemail = getIntent().getStringExtra("keyemail");
        String Skeypassword = getIntent().getStringExtra("keypassword");


        Intent intent = new Intent(DetailKaryawan.this, DialogLogin.class);
        intent.putExtra("key", Skey);

        intent.putExtra("keyemail", Skeyemail);
        intent.putExtra("keypassword", Skeypassword);
        startActivity(intent);
    }

    private void maps() {

        final String Skey = getIntent().getStringExtra("key");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Anda ingin membuka Maps ?")
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent(DetailKaryawan.this, MapsActivity.class);
                        intent.putExtra("key", Skey);
                        startActivity(intent);

//                        Toast.makeText(getApplicationContext(),"Coding Masih Abu Abu",Toast.LENGTH_SHORT).show();

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

    private void delete() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Memuat....");
        progressDialog.show();

        final String Skey = getIntent().getStringExtra("key");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Anda Ingin Menghapus Data ?")
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Call<POSTModel> call = apiservice.getHapus(Skey);
                        call.enqueue(new Callback<POSTModel>() {
                            @Override
                            public void onResponse(Call<POSTModel> call, Response<POSTModel> response) {


                                if (response.isSuccessful()) {
                                    progressDialog.dismiss();

                                    if (response.body().getMessage().equals("Data Terhapus")) {

                                    } else {
                                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }


                            @Override
                            public void onFailure(Call<POSTModel> call, Throwable t) {

                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            }


                        });


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
