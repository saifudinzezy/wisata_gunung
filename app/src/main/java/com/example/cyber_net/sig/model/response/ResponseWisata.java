package com.example.cyber_net.sig.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.cyber_net.sig.model.response.item.WisataItem;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ResponseWisata implements Parcelable {

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("wisata")
	private List<WisataItem> wisata;

	@SerializedName("response")
	private String response;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setWisata(List<WisataItem> wisata){
		this.wisata = wisata;
	}

	public List<WisataItem> getWisata(){
		return wisata;
	}

	public void setResponse(String response){
		this.response = response;
	}

	public String getResponse(){
		return response;
	}

	@Override
 	public String toString(){
		return 
			"ResponseWisata{" + 
			"pesan = '" + pesan + '\'' + 
			",wisata = '" + wisata + '\'' + 
			",response = '" + response + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.pesan);
		dest.writeList(this.wisata);
		dest.writeString(this.response);
	}

	public ResponseWisata() {
	}

	protected ResponseWisata(Parcel in) {
		this.pesan = in.readString();
		this.wisata = new ArrayList<WisataItem>();
		in.readList(this.wisata, WisataItem.class.getClassLoader());
		this.response = in.readString();
	}

	public static final Parcelable.Creator<ResponseWisata> CREATOR = new Parcelable.Creator<ResponseWisata>() {
		@Override
		public ResponseWisata createFromParcel(Parcel source) {
			return new ResponseWisata(source);
		}

		@Override
		public ResponseWisata[] newArray(int size) {
			return new ResponseWisata[size];
		}
	};
}