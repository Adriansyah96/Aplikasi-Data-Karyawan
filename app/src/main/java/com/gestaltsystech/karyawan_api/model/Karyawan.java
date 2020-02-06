package com.gestaltsystech.karyawan_api.model;

public class Karyawan {
    private int id;
    private String nama;
    private String email;
    private int tanggal_lahir;
    private int telepon;
    private String alamat;
    private String jabatan;
    private String kelamin;
    private String password;
    private String konfirmasi_password;

    public Karyawan(String nama, String email, int tanggal_lahir, int telepon, String alamat, String jabatan, String kelamin, String password, String konfirmasi_password) {
        this.nama = nama;
        this.email = email;
        this.tanggal_lahir = tanggal_lahir;
        this.telepon = telepon;
        this.alamat = alamat;
        this.jabatan = jabatan;
        this.kelamin = kelamin;
        this.password = password;
        this.konfirmasi_password = konfirmasi_password;

    }


}
