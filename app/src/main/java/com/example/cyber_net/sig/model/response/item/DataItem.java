package com.example.cyber_net.sig.model.response.item;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DataItem implements Serializable{

	@SerializedName("image")
	private String image;

	@SerializedName("id_wisata")
	private String idWisata;

	@SerializedName("nama")
	private String nama;

	@SerializedName("id_view")
	private String idView;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setIdWisata(String idWisata){
		this.idWisata = idWisata;
	}

	public String getIdWisata(){
		return idWisata;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setIdView(String idView){
		this.idView = idView;
	}

	public String getIdView(){
		return idView;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"image = '" + image + '\'' + 
			",id_wisata = '" + idWisata + '\'' + 
			",nama = '" + nama + '\'' + 
			",id_view = '" + idView + '\'' + 
			"}";
		}
}