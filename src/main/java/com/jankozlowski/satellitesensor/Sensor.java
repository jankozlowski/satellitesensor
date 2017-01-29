package com.jankozlowski.satellitesensor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="sensor")
public class Sensor implements Serializable {

	
	@Id
	@GenericGenerator(name="keygenerator" , strategy="increment")
	@GeneratedValue(generator="keygenerator")
	@Column(name = "id_sensor", unique = true, nullable = false)
	private long sensorID;
	private String nazwa;
	private String technikaSkanowania;
	private double szerokoscPasma;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy="sensor")
	private Set<Kanal> kanaly = new HashSet<Kanal>(0);
	
	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "sensory")
	private Set<Satelita> satelity = new HashSet<Satelita>(0);
	
	//boolean typ;
	
	
	public long getSensorID() {
		return sensorID;
	}
	public void setSensorID(long sensorID) {
		this.sensorID = sensorID;
	}
	public String getNazwa() {
		return nazwa;
	}
	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public double getSzerokoscPasma() {
		return szerokoscPasma;
	}
	public void setSzerokoscPasma(double szerokoscPasma) {
		this.szerokoscPasma = szerokoscPasma;
	}
	public String getTechnikaSkanowania() {
		return technikaSkanowania;
	}
	public void setTechnikaSkanowania(String technikaSkanowania) {
		this.technikaSkanowania = technikaSkanowania;
	}
	public Set<Kanal> getKanaly() {
		return kanaly;
	}
	public void setKanaly(Set<Kanal> kanaly) {
		this.kanaly = kanaly;
	}
	public Set<Satelita> getSatelity() {
		return satelity;
	}
	public void setSatelity(Set<Satelita> satelity) {
		this.satelity = satelity;
	}
	
	

	
	
}
