package com.jankozlowski.satellitesensor;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="kanal")
public class Kanal implements Serializable {

	@Id
	@GenericGenerator(name="keygenerator" , strategy="increment")
	@GeneratedValue(generator="keygenerator")
	@Column(name = "id_kanal", unique = true, nullable = false)
	private int idKanal;
	private String zakresFal;
	private double centralnaDlugosFal;
	private double rozdzielcosc;
	
	@ManyToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinColumn(name = "id_sensor", nullable = false)
	private Sensor sensor;
	
	
	
	
	
	public int getId() {
		return idKanal;
	}
	public void setId(int id) {
		this.idKanal = id;
	}
	public String getZakresFal() {
		return zakresFal;
	}
	public void setZakresFal(String zakresFal) {
		this.zakresFal = zakresFal;
	}

	public double getRozdzielcosc() {
		return rozdzielcosc;
	}
	public void setRozdzielcosc(double rozdzielcosc) {
		this.rozdzielcosc = rozdzielcosc;
	}
	public double getCentralnaDlugosFal() {
		return centralnaDlugosFal;
	}
	public void setCentralnaDlugosFal(double centralnaDlugosFal) {
		this.centralnaDlugosFal = centralnaDlugosFal;
	}
	public int getIdKanal() {
		return idKanal;
	}
	public void setIdKanal(int idKanal) {
		this.idKanal = idKanal;
	}
	public Sensor getSensor() {
		return sensor;
	}
	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}
	
}
