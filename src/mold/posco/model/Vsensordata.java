package mold.posco.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the vsensordata database table.
 * 
 */
@Entity
@NamedQuery(name="Vsensordata.findAll", query="SELECT v FROM Vsensordata v")
public class Vsensordata  {

	@Id
	private String id;

	private int act;

	private double humi;

	@Column(name="humi_adc")
	private short humiAdc;

	private int loc;

	private int low;

	private String mold;

	private int outb;

	private int place;
	
	private int seq;

	private double temp;

	private Timestamp tm;

	public Vsensordata() {
	}

	public String getId() {
		return this.id;
	}

	public int getAct() {
		return this.act;
	}

	public void setAct(int act) {
		this.act = act;
	}

	public double getHumi() {
		return this.humi;
	}

	public void setHumi(double humi) {
		this.humi = humi;
	}

	public short getHumiAdc() {
		return this.humiAdc;
	}

	public void setHumiAdc(short humiAdc) {
		this.humiAdc = humiAdc;
	}

	public int getLoc() {
		return this.loc;
	}

	public void setLoc(int loc) {
		this.loc = loc;
	}

	public int getLow() {
		return this.low;
	}

	public void setLow(int low) {
		this.low = low;
	}

	public String getMold() {
		return this.mold;
	}

	public void setMold(String mold) {
		this.mold = mold;
	}

	public int getOutb() {
		return this.outb;
	}

	public void setOutb(int outb) {
		this.outb = outb;
	}

	public int getPlace() {
		return this.place;
	}

	public void setPlace(int place) {
		this.place = place;
	}

	public int getSeq() {
		return this.seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public double getTemp() {
		return this.temp;
	}

	public void setTemp(double temp) {
		this.temp = temp;
	}

	public Timestamp getTm() {
		return this.tm;
	}

	public void setTm(Timestamp tm) {
		this.tm = tm;
	}
	public String getDispNm() {
		return "S" + String.format("%02d", this.seq);
	}

}