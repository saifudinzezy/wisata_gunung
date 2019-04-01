package com.example.cyber_net.sig.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.cyber_net.sig.model.response.item.SafetyItem;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ResponseSafety implements Parcelable {

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("safety")
	private List<SafetyItem> safety;

	@SerializedName("response")
	private String response;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setSafety(List<SafetyItem> safety){
		this.safety = safety;
	}

	public List<SafetyItem> getSafety(){
		return safety;
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
			"ResponseSafety{" + 
			"pesan = '" + pesan + '\'' + 
			",safety = '" + safety + '\'' + 
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
		dest.writeList(this.safety);
		dest.writeString(this.response);
	}

	public ResponseSafety() {
	}

	protected ResponseSafety(Parcel in) {
		this.pesan = in.readString();
		this.safety = new ArrayList<SafetyItem>();
		in.readList(this.safety, SafetyItem.class.getClassLoader());
		this.response = in.readString();
	}

	public static final Parcelable.Creator<ResponseSafety> CREATOR = new Parcelable.Creator<ResponseSafety>() {
		@Override
		public ResponseSafety createFromParcel(Parcel source) {
			return new ResponseSafety(source);
		}

		@Override
		public ResponseSafety[] newArray(int size) {
			return new ResponseSafety[size];
		}
	};
}