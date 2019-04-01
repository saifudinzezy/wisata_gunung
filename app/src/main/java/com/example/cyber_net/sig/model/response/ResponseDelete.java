package com.example.cyber_net.sig.model.response;

import com.google.gson.annotations.SerializedName;

public class ResponseDelete{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("kode")
	private int kode;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setKode(int kode){
		this.kode = kode;
	}

	public int getKode(){
		return kode;
	}

	@Override
 	public String toString(){
		return 
			"ResponseDelete{" + 
			"pesan = '" + pesan + '\'' + 
			",kode = '" + kode + '\'' + 
			"}";
		}
}