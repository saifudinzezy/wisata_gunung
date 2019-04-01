package com.example.cyber_net.sig.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.cyber_net.sig.model.response.item.PosPendakianItem;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ResponsePos implements Parcelable {

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("response")
	private String response;

	@SerializedName("pos_pendakian")
	private List<PosPendakianItem> posPendakian;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setResponse(String response){
		this.response = response;
	}

	public String getResponse(){
		return response;
	}

	public void setPosPendakian(List<PosPendakianItem> posPendakian){
		this.posPendakian = posPendakian;
	}

	public List<PosPendakianItem> getPosPendakian(){
		return posPendakian;
	}

	@Override
 	public String toString(){
		return 
			"ResponsePos{" + 
			"pesan = '" + pesan + '\'' + 
			",response = '" + response + '\'' + 
			",pos_pendakian = '" + posPendakian + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.pesan);
		dest.writeString(this.response);
		dest.writeList(this.posPendakian);
	}

	public ResponsePos() {
	}

	protected ResponsePos(Parcel in) {
		this.pesan = in.readString();
		this.response = in.readString();
		this.posPendakian = new ArrayList<PosPendakianItem>();
		in.readList(this.posPendakian, PosPendakianItem.class.getClassLoader());
	}

	public static final Parcelable.Creator<ResponsePos> CREATOR = new Parcelable.Creator<ResponsePos>() {
		@Override
		public ResponsePos createFromParcel(Parcel source) {
			return new ResponsePos(source);
		}

		@Override
		public ResponsePos[] newArray(int size) {
			return new ResponsePos[size];
		}
	};
}