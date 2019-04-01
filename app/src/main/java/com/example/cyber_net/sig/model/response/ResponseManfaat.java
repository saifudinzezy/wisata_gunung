package com.example.cyber_net.sig.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.cyber_net.sig.model.response.item.ManfaatItem;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ResponseManfaat implements Parcelable {

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("manfaat")
	private List<ManfaatItem> manfaat;

	@SerializedName("response")
	private String response;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setManfaat(List<ManfaatItem> manfaat){
		this.manfaat = manfaat;
	}

	public List<ManfaatItem> getManfaat(){
		return manfaat;
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
			"ResponseManfaat{" + 
			"pesan = '" + pesan + '\'' + 
			",manfaat = '" + manfaat + '\'' + 
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
		dest.writeList(this.manfaat);
		dest.writeString(this.response);
	}

	public ResponseManfaat() {
	}

	protected ResponseManfaat(Parcel in) {
		this.pesan = in.readString();
		this.manfaat = new ArrayList<ManfaatItem>();
		in.readList(this.manfaat, ManfaatItem.class.getClassLoader());
		this.response = in.readString();
	}

	public static final Parcelable.Creator<ResponseManfaat> CREATOR = new Parcelable.Creator<ResponseManfaat>() {
		@Override
		public ResponseManfaat createFromParcel(Parcel source) {
			return new ResponseManfaat(source);
		}

		@Override
		public ResponseManfaat[] newArray(int size) {
			return new ResponseManfaat[size];
		}
	};
}