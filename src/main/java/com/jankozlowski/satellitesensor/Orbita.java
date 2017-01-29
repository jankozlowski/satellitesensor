package com.jankozlowski.satellitesensor;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="orbita")
public class Orbita implements Serializable {

	@Id
	@Column(name = "norad_id", unique = true, nullable = false)
	private int noradId;
	private String klasaWysokosci;
	private String orbita;
	private double perygeum;
	private double apogeum;
	private double mimosrod;
	private double inklinacja;
	private double czasObiegu;
	private double SEM;
	private Date czasRewizyty;
	
	@OneToOne(mappedBy="orbita", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	private Satelita satelita;
	
	
	public String getOrbita() {
		return orbita;
	}
	public void setOrbita(String orbita) {
		this.orbita = orbita;
	}

	public void setApogeum(int apogeum) {
		this.apogeum = apogeum;
	}
	public double getMimosrod() {
		return mimosrod;
	}
	public void setMimosrod(double mimosrod) {
		this.mimosrod = mimosrod;
	}
	public double getInklinacja() {
		return inklinacja;
	}
	public void setInklinacja(double inklinacja) {
		this.inklinacja = inklinacja;
	}
	public double getCzasObiegu() {
		return czasObiegu;
	}
	public void setCzasObiegu(double czasObiegu) {
		this.czasObiegu = czasObiegu;
	}
	public Date getCzarRewizyty() {
		return czasRewizyty;
	}
	public void setCzarRewizyty(Date czarRewizyty) {
		this.czasRewizyty = czarRewizyty;
	}
	public int getNoradId() {
		return noradId;
	}
	public void setNoradId(int noradId) {
		this.noradId = noradId;
	}
	public String getKlasaWysokosci() {
		return klasaWysokosci;
	}
	public void setKlasaWysokosci(String klasaWysokosci) {
		this.klasaWysokosci = klasaWysokosci;
	}
	public double getPerygeum() {
		return perygeum;
	}
	public void setPerygeum(double perygeum) {
		this.perygeum = perygeum;
	}
	public double getApogeum() {
		return apogeum;
	}
	public void setApogeum(double apogeum) {
		this.apogeum = apogeum;
	}
	public Date getCzasRewizyty() {
		return czasRewizyty;
	}
	public void setCzasRewizyty(Date czasRewizyty) {
		this.czasRewizyty = czasRewizyty;
	}
	public Satelita getSatelita() {
		return satelita;
	}
	public void setSatelita(Satelita satelita) {
		this.satelita = satelita;
	}
	public double getSEM() {
		return SEM;
	}
	public void setSEM(double sEM) {
		SEM = sEM;
	}
	
	
}
