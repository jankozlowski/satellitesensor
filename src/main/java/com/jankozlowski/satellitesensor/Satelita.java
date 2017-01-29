package com.jankozlowski.satellitesensor;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;



@Entity
@Table(name="satelita")
public class Satelita implements Serializable {

	@Id
	@Column(name = "norad_id", unique = true, nullable = false)
	private int noradId;
	private String nazwa;
	private String rodzaj;
	private String uzytkownicy;
	//private String uwagi;
	private String dataStartu;
	private String pojazdStartowy;
	private String cel;
	private String kraj;
	private int masa;
	private String tle1;
	private String tle2;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="norad_id")
	private Orbita orbita;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "satelita_sensor",  joinColumns = { 
			@JoinColumn(name = "norad_id", nullable = false, updatable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "id_sensor", 
					nullable = false, updatable = false) })
	private Set<Sensor> sensory = new HashSet<Sensor>(0);
	
	public String getNazwa() {
		return nazwa;
	}
	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}
	public String getRodzaj() {
		return rodzaj;
	}
	public void setRodzaj(String rodzaj) {
		this.rodzaj = rodzaj;
	}
	public String getUzytkownicy() {
		return uzytkownicy;
	}
	public void setUzytkownicy(String u¿ytkownicy) {
		this.uzytkownicy = u¿ytkownicy;
	}
	//public String getUwagi() {
//		return uwagi;
//	}
//	public void setUwagi(String uwagi) {
//		this.uwagi = uwagi;
//	}
	public String getDataStartu() {
		return dataStartu;
	}
	public void setDataStartu(String dataStartu) {
		this.dataStartu = dataStartu;
	}
	public String getPojazdStartowy() {
		return pojazdStartowy;
	}
	public void setPojazdStartowy(String pojazdStartowy) {
		this.pojazdStartowy = pojazdStartowy;
	}
	public int getMasa() {
		return masa;
	}
	public void setMasa(int masa) {
		this.masa = masa;
	}
	public int getNoradId() {
		return noradId;
	}
	public void setNoradId(int noradId) {
		this.noradId = noradId;
	}

	public String getTle1() {
		return tle1;
	}
	public void setTle1(String tle1) {
		this.tle1 = tle1;
	}
	public String getTle2() {
		return tle2;
	}
	public void setTle2(String tle2) {
		this.tle2 = tle2;
	}
	public Orbita getOrbita() {
		return orbita;
	}
	public void setOrbita(Orbita orbita) {
		this.orbita = orbita;
	}
	public String getCel() {
		return cel;
	}
	public void setCel(String cel) {
		this.cel = cel;
	}
	public Set<Sensor> getSensory() {
		return sensory;
	}
	public void setSensory(Set<Sensor> sensory) {
		this.sensory = sensory;
	}
	public String getKraj() {
		return kraj;
	}
	public void setKraj(String kraj) {
		this.kraj = kraj;
	}
	
}
