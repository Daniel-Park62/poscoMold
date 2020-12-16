package mold.posco.model;

import java.sql.Timestamp;

public class ChartData {
	private Timestamp tm;
	private int seq ;  // sensorno
	private int place;
	private double temp;
	private String desc;
	
	public ChartData(Timestamp tm, int seq, int place, double temp, String desc) {
		super();
		this.tm = tm;
		this.seq = seq;
		this.place = place;
		this.temp = temp;
		this.desc = desc;
	}
	
	public Timestamp getTm() {
		return tm;
	}
	public int getSeq() {
		return seq;
	}
	public int getPlace() {
		return place;
	}

	public double getTemp() {
		return temp;
	}
	public String getDesc() {
		return desc != null ? desc : "" ;
	}

}
