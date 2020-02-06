package com.gestaltsystech.karyawan_api.api;

import com.gestaltsystech.karyawan_api.model.DataItem;
import com.gestaltsystech.karyawan_api.model.KaryawanResponse;
import com.gestaltsystech.karyawan_api.model.POSTModel;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface APIService {

    @GET("login.php")
    Call<POSTModel> login_staff(@Query("email") String Semail, @Query("password") String Spassword);

    @GET("tambah.php")
    Call<POSTModel> tambah_staff(@Query("nama") String Snama, @Query("email") String Semail,
            @Query("tanggal_lahir") String Stanggal_lahir, @Query("telepon") String Stelepon,
                                 @Query("alamat") String Salamat, @Query("jabatan") String Sjabatan,
                                 @Query("kelamin") String Skelamin,@Query("password") String Spassword,
                                 @Query("konfirmasi_password") String Skonfirmasi_password);

    @GET("tampilsemua.php")
    Call<KaryawanResponse> getList();

    @GET("tampil.php")
    Call<DataItem> getDetail(@Query("id")String Sid);

    @GET("edit.php")
    Call<POSTModel> updatedata(@Query("id") String Sid, @Query("nama") String Snama, @Query("email") String Semail,
                               @Query("tanggal_lahir") String Stanggal_lahir, @Query("telepon") String Stelepon,
                               @Query("alamat") String Salamat, @Query("jabatan") String Sjabatan,
                               @Query("kelamin") String Skelamin);

    @GET("hapus.php")
    Call<POSTModel> getHapus(@Query("id")String Sid);

    @GET("cekakses.php")
    Call<POSTModel> cekakses(@Query("id") String Sid,@Query("email") String Semail, @Query("password") String Spassword);

    @GET("maps.php")
    Call<POSTModel> getMaps(@Query("id")String Sid);

    @Headers("Accept: application/json")
    @Multipart
    @POST("uploadimage.php")
    Call<POSTModel> updateAvatar(
            @Part
                    MultipartBody.Part avatar,
            @Part("id") String Sid);

}
