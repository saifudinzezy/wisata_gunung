package com.example.cyber_net.sig.model.response.item;

import com.google.gson.annotations.SerializedName;

public class UserItem{

	@SerializedName("nama")
	private String nama;

	@SerializedName("id_admin")
	private String idAdmin;

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setIdAdmin(String idAdmin){
		this.idAdmin = idAdmin;
	}

	public String getIdAdmin(){
		return idAdmin;
	}

	@Override
 	public String toString(){
		return 
			"UserItem{" + 
			"nama = '" + nama + '\'' + 
			",id_admin = '" + idAdmin + '\'' + 
			"}";
		}
}