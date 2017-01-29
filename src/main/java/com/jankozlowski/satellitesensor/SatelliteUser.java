package com.jankozlowski.satellitesensor;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="satelliteuser")
public class SatelliteUser implements Serializable{
	
	@Id
	@GenericGenerator(name="keygenerator" , strategy="increment")
	@GeneratedValue(generator="keygenerator")
	@Column(name = "userid", unique = true, nullable = false)
	private int userid;
	private String nazwa;
	private String password;
	public String getNazwa() {
		return nazwa;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}


	
}
