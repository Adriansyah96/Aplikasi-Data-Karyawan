package com.gestaltsystech.karyawan_api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class KaryawanResponse{

	@SerializedName("data")
	private List<DataItem> data;

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
	}

	@Override
 	public String toString(){
		return 
			"KaryawanResponse{" + 
			"data = '" + data + '\'' + 
			"}";
		}
}