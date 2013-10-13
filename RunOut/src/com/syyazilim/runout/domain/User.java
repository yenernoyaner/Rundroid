package com.syyazilim.runout.domain;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class User  implements Serializable{
	private long id;
	private String username;
	private String name;
	private String surname;
	private String weight;
	private String tall;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getTall() {
		return tall;
	}
	public void setTall(String tall) {
		this.tall = tall;
	}
	

}
