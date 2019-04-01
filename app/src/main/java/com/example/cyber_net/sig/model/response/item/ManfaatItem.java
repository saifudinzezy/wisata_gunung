package com.example.cyber_net.sig.model.response.item;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ManfaatItem implements Parcelable {

	@SerializedName("id_manfaat")
	private String idManfaat;

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

	public void setIdManfaat(String idManfaat){
		this.idManfaat = idManfaat;
	}

	public String getIdManfaat(){
		return idManfaat;
	}

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

	@Override
 	public String toString(){
		return 
			"ManfaatItem{" + 
			"id_manfaat = '" + idManfaat + '\'' + 
			",image = '" + image + '\'' + 
			",nama = '" + nama + '\'' + 
			",id_admin = '" + idAdmin + '\'' + 
			",deskripsi = '" + deskripsi + '\'' + 
			",tanggal = '" + tanggal + '\'' + 
			",judul = '" + judul + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.idManfaat);
		dest.writeString(this.image);
		dest.writeString(this.nama);
		dest.writeString(this.idAdmin);
		dest.writeString(this.deskripsi);
		dest.writeString(this.tanggal);
		dest.writeString(this.judul);
	}

	public ManfaatItem() {
	}

	protected ManfaatItem(Parcel in) {
		this.idManfaat = in.readString();
		this.image = in.readString();
		this.nama = in.readString();
		this.idAdmin = in.readString();
		this.deskripsi = in.readString();
		this.tanggal = in.readString();
		this.judul = in.readString();
	}

	public static final Parcelable.Creator<ManfaatItem> CREATOR = new Parcelable.Creator<ManfaatItem>() {
		@Override
		public ManfaatItem createFromParcel(Parcel source) {
			return new ManfaatItem(source);
		}

		@Override
		public ManfaatItem[] newArray(int size) {
			return new ManfaatItem[size];
		}
	};
}