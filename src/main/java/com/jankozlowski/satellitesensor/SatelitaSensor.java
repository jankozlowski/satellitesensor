package com.jankozlowski.satellitesensor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="satelita_sensor")
public class SatelitaSensor {

	private int noradId;
	private int idSensor;
	
	public int getNoradId() {
		return noradId;
	}
	public void setNoradId(int noradId) {
		this.noradId = noradId;
	}
	public int getIdSensor() {
		return idSensor;
	}
	public void setIdSensor(int idSensor) {
		this.idSensor = idSensor;
	}
	
}
