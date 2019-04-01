package com.example.cyber_net.sig.model.response.item;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PosPendakianItem implements Parcelable {

	@SerializedName("id_wisata")
	private String idWisata;

	@SerializedName("nama")
	private String nama;

	@SerializedName("latitude")
	private String latitude;

	@SerializedName("id_pos")
	private String idPos;

	@SerializedName("longitude")
	private String longitude;

	@SerializedName("pos")
	private String pos;

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
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

	public void setLatitude(String latitude){
		this.latitude = latitude;
	}

	public String getLatitude(){
		return latitude;
	}

	public void setIdPos(String idPos){
		this.idPos = idPos;
	}

	public String getIdPos(){
		return idPos;
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
			"PosPendakianItem{" + 
			"id_wisata = '" + idWisata + '\'' + 
			",nama = '" + nama + '\'' + 
			",latitude = '" + latitude + '\'' + 
			",id_pos = '" + idPos + '\'' + 
			",longitude = '" + longitude + '\'' + 
			"}";
		}

	public PosPendakianItem() {
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.idWisata);
		dest.writeString(this.nama);
		dest.writeString(this.latitude);
		dest.writeString(this.idPos);
		dest.writeString(this.longitude);
		dest.writeString(this.pos);
	}

	protected PosPendakianItem(Parcel in) {
		this.idWisata = in.readString();
		this.nama = in.readString();
		this.latitude = in.readString();
		this.idPos = in.readString();
		this.longitude = in.readString();
		this.pos = in.readString();
	}

	public static final Creator<PosPendakianItem> CREATOR = new Creator<PosPendakianItem>() {
		@Override
		public PosPendakianItem createFromParcel(Parcel source) {
			return new PosPendakianItem(source);
		}

		@Override
		public PosPendakianItem[] newArray(int size) {
			return new PosPendakianItem[size];
		}
	};
}