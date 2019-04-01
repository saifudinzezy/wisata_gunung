package com.example.cyber_net.sig.model.response.item;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class SafetyItem implements Parcelable {

	@SerializedName("image")
	private String image;

	@SerializedName("nama")
	private String nama;

	@SerializedName("id_admin")
	private String idAdmin;

	@SerializedName("deskripsi")
	private String deskripsi;

	@SerializedName("tanggal")
	private String tanggal;

	@SerializedName("judul")
	private String judul;

	@SerializedName("id_safety")
	private String idSafety;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

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

	public void setDeskripsi(String deskripsi){
		this.deskripsi = deskripsi;
	}

	public String getDeskripsi(){
		return deskripsi;
	}

	public void setTanggal(String tanggal){
		this.tanggal = tanggal;
	}

	public String getTanggal(){
		return tanggal;
	}

	public void setJudul(String judul){
		this.judul = judul;
	}

	public String getJudul(){
		return judul;
	}

	public void setIdSafety(String idSafety){
		this.idSafety = idSafety;
	}

	public String getIdSafety(){
		return idSafety;
	}

	@Override
 	public String toString(){
		return 
			"SafetyItem{" + 
			"image = '" + image + '\'' + 
			",nama = '" + nama + '\'' + 
			",id_admin = '" + idAdmin + '\'' + 
			",deskripsi = '" + deskripsi + '\'' + 
			",tanggal = '" + tanggal + '\'' + 
			",judul = '" + judul + '\'' + 
			",id_safety = '" + idSafety + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.image);
		dest.writeString(this.nama);
		dest.writeString(this.idAdmin);
		dest.writeString(this.deskripsi);
		dest.writeString(this.tanggal);
		dest.writeString(this.judul);
		dest.writeString(this.idSafety);
	}

	public SafetyItem() {
	}

	protected SafetyItem(Parcel in) {
		this.image = in.readString();
		this.nama = in.readString();
		this.idAdmin = in.readString();
		this.deskripsi = in.readString();
		this.tanggal = in.readString();
		this.judul = in.readString();
		this.idSafety = in.readString();
	}

	public static final Parcelable.Creator<SafetyItem> CREATOR = new Parcelable.Creator<SafetyItem>() {
		@Override
		public SafetyItem createFromParcel(Parcel source) {
			return new SafetyItem(source);
		}

		@Override
		public SafetyItem[] newArray(int size) {
			return new SafetyItem[size];
		}
	};
}