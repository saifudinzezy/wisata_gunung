package com.example.cyber_net.sig.model.response.item;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class WisataItem implements Parcelable {

	@SerializedName("image")
	private String image;

	@SerializedName("id_wisata")
	private String idWisata;

	@SerializedName("lokasi")
	private String lokasi;

	@SerializedName("latitude")
	private String latitude;

	@SerializedName("deskripsi")
	private String deskripsi;

	@SerializedName("judul")
	private String judul;

	@SerializedName("longitude")
	private String longitude;

	@SerializedName("ketinggian")
	private String ketinggian;

	@SerializedName("fasilitas")
	private String fasilitas;

	@SerializedName("tipe_tanah")
	private String tipe_tanah;

	public String getKetinggian() {
		return ketinggian;
	}

	public void setKetinggian(String ketinggian) {
		this.ketinggian = ketinggian;
	}

	public String getFasilitas() {
		return fasilitas;
	}

	public void setFasilitas(String fasilitas) {
		this.fasilitas = fasilitas;
	}

	public String getTipe_tanah() {
		return tipe_tanah;
	}

	public void setTipe_tanah(String tipe_tanah) {
		this.tipe_tanah = tipe_tanah;
	}

	public String getTipe_gunung() {
		return tipe_gunung;
	}

	public void setTipe_gunung(String tipe_gunung) {
		this.tipe_gunung = tipe_gunung;
	}

	@SerializedName("tipe_gunung")
	private String tipe_gunung;

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

	public void setLokasi(String lokasi){
		this.lokasi = lokasi;
	}

	public String getLokasi(){
		return lokasi;
	}

	public void setLatitude(String latitude){
		this.latitude = latitude;
	}

	public String getLatitude(){
		return latitude;
	}

	public void setDeskripsi(String deskripsi){
		this.deskripsi = deskripsi;
	}

	public String getDeskripsi(){
		return deskripsi;
	}

	public void setJudul(String judul){
		this.judul = judul;
	}

	public String getJudul(){
		return judul;
	}

	public void setLongitude(String longitude){
		this.longitude = longitude;
	}

	public String getLongitude(){
		return longitude;
	}

	@Override
 	public String toString(){
		return 
			"WisataItem{" + 
			"image = '" + image + '\'' + 
			",id_wisata = '" + idWisata + '\'' + 
			",lokasi = '" + lokasi + '\'' + 
			",latitude = '" + latitude + '\'' + 
			",deskripsi = '" + deskripsi + '\'' + 
			",judul = '" + judul + '\'' + 
			",longitude = '" + longitude + '\'' + 
			"}";
		}

	public WisataItem() {
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.image);
		dest.writeString(this.idWisata);
		dest.writeString(this.lokasi);
		dest.writeString(this.latitude);
		dest.writeString(this.deskripsi);
		dest.writeString(this.judul);
		dest.writeString(this.longitude);
		dest.writeString(this.ketinggian);
		dest.writeString(this.fasilitas);
		dest.writeString(this.tipe_tanah);
		dest.writeString(this.tipe_gunung);
	}

	protected WisataItem(Parcel in) {
		this.image = in.readString();
		this.idWisata = in.readString();
		this.lokasi = in.readString();
		this.latitude = in.readString();
		this.deskripsi = in.readString();
		this.judul = in.readString();
		this.longitude = in.readString();
		this.ketinggian = in.readString();
		this.fasilitas = in.readString();
		this.tipe_tanah = in.readString();
		this.tipe_gunung = in.readString();
	}

	public static final Creator<WisataItem> CREATOR = new Creator<WisataItem>() {
		@Override
		public WisataItem createFromParcel(Parcel source) {
			return new WisataItem(source);
		}

		@Override
		public WisataItem[] newArray(int size) {
			return new WisataItem[size];
		}
	};
}