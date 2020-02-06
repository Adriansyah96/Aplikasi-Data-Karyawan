package com.gestaltsystech.karyawan_api.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class DataItem implements Serializable {

	@SerializedName("kelamin")
	private String kelamin;

	@SerializedName("nama")
	private String nama;

	@SerializedName("jabatan")
	private String jabatan;

	@SerializedName("telepon")
	private String telepon;

	@SerializedName("id")
	private String id;

	@SerializedName("email")
	private String email;

	@SerializedName("tanggal_lahir")
	private String tanggalLahir;

	@SerializedName("alamat")
	private String alamat;

	@SerializedName("password")
	private String password;

	@SerializedName("konfirmasi_password")
	private String konfirmasiPassword;

	@SerializedName("kordinat")
	private String kordinat;

	@SerializedName("avatar")
	private String avatar;

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}



	public void setKelamin(String kelamin){
		this.kelamin = kelamin;
	}

	public String getKelamin(){
		return kelamin;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setJabatan(String jabatan){
		this.jabatan = jabatan;
	}

	public String getJabatan(){
		return jabatan;
	}

	public void setTelepon(String telepon){
		this.telepon = telepon;
	}

	public String getTelepon(){
		return telepon;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setTanggalLahir(String tanggalLahir){
		this.tanggalLahir = tanggalLahir;
	}

	public String getTanggalLahir(){
		return tanggalLahir;
	}

	public void setAlamat(String alamat){
		this.alamat = alamat;
	}

	public String getAlamat(){
		return alamat;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}


	public void setKoordinat(String koordinat){
		this.kordinat = koordinat;
	}

	public String getKordinat(){
		return kordinat;
	}



	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"kelamin = '" + kelamin + '\'' + 
			",nama = '" + nama + '\'' + 
			",jabatan = '" + jabatan + '\'' + 
			",telepon = '" + telepon + '\'' + 
			",id = '" + id + '\'' + 
			",email = '" + email + '\'' + 
			",tanggal_lahir = '" + tanggalLahir + '\'' + 
			",alamat = '" + alamat + '\'' +
					",password = '" + password + '\'' +
					",konfirmasi_password = '" + konfirmasiPassword + '\'' +
					",koordinat = '" + kordinat + '\'' +
					"}";
		}
}