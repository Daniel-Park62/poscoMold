package mold.posco.model;

import java.sql.Timestamp;

import javax.persistence.*;

import org.eclipse.persistence.annotations.ReadOnly;

/**
 * Entity implementation class for Entity: MoteInfo
 *
 */
@ReadOnly
@Entity
@Table(name="moteinfo")
public class MoteInfo {
   
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;

	private int act;

	private double humi;

	@Column(name="humi_adc")
	private short humiAdc;

	private double humi2;

	private int place;
	private String mold = "" ;
	private short loc = 0 ;

	@Column(updatable=false, insertable=false)
	private int seq;

	private double temp;

	private double temp2;

	private Timestamp tm;

	@ManyToOne
	@JoinColumn(name = "seq") 
	private MoteStatus motestatus ;
	
	public MoteStatus getMoteStatus() {
		return motestatus ;
	}
	
	public MoteInfo() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
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

	public double getHumi2() {
		return this.humi2;
	}

	public void setHumi2(double humi2) {
		this.humi2 = humi2;
	}

	public int getPlace() {
		return this.place;
	}

	public void setPlace(int place) {
		this.place = place;
	}

	public String getMold() {
		return mold;
	}

	public void setMold(String mold) {
		this.mold = mold;
	}

	public short getLoc() {
		return loc;
	}

	public void setLoc(short loc) {
		this.loc = loc;
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

	public double getTemp2() {
		return this.temp2;
	}

	public void setTemp2(double temp2) {
		this.temp2 = temp2;
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
