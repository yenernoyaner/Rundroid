package com.syyazilim.runout;

import java.io.Serializable;

public class UserSession implements Serializable {
    public long id ;
	public String calories;
	public String speed;
	public String distance;
	public String tempo;
	public String time;
	
	public UserSession() {
		super();
	}
	
	
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getCalories() {
		return calories;
	}
	public void setCalories(String calories) {
		this.calories = calories;
	}
	public String getSpeed() {
		return speed;
	}
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getTempo() {
		return tempo;
	}
	public void setTempo(String tempo) {
		this.tempo = tempo;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	
}
