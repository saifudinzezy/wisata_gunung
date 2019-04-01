package com.example.cyber_net.sig.model.response;

import com.example.cyber_net.sig.model.response.item.DataItem;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ResponseView implements Serializable{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("response")
	private String response;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
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
			"ResponseView{" + 
			"pesan = '" + pesan + '\'' + 
			",data = '" + data + '\'' + 
			",response = '" + response + '\'' + 
			"}";
		}
}