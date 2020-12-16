package mold.posco.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

import org.eclipse.swt.graphics.Point;

/**
 * Entity implementation class for Entity: MoteStatus
 *
 */
@Entity
@NamedQuery(name = "MoteStatus.sensorList", 
            query = "select a from MoteStatus a where a.gubun = 'S' and a.spare = 'N' ")
@NamedQuery(name = "MoteStatus.rList", query = "select a from MoteStatus a where a.seq < 13 and a.gubun != 'S' and a.spare = 'N' ")
@NamedQuery(name = "MoteStatus.rList2", query = "select a from MoteStatus a where a.seq >= 13 and a.gubun != 'S' and a.spare = 'N' ")
@NamedQuery(name = "MoteStatus.findById", query = "select a from MoteStatus a where a.seq = :seq  ")

public class MoteStatus implements Cloneable {

	@Id
	private int seq = 0;
	private short act = 0;
	private String gubun = "S"; // 센서/리피터구분 S, R

	private String spare = "N"; // 예비품 Y,N
	private String mac = "";
	@Column(name = "descript")
	private String desc = "";
	private float batt = 0;
	@Column(name = "batt_dt")
	private String battDt = "";
	private int lowcnt = 0;
	private int x = 100;
	private int y = 100;

	private short place = 0;

	private short stand = 0;

	private String mold = "" ;
	
	private short loc = 0 ;
	
	private int obcnt = 0;
	private int tval = 0;
	private int sval = 0;
	private int maxval = 0;
	private int minval = 0;

	public MoteStatus() {
		super();
	}

	public short getLoc() {
		return loc;
	}

	public void setLoc(short loc) {
		this.loc = loc;
	}

	public void setXy(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Point getXy() {
		return new Point(x, y);
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public short getAct() {
		return this.act;
	}

	public void setAct(short act) {
		this.act = act;
	}

	public String getDispNm() {
		if (this.gubun.equals("R"))
			return "R" + String.format("%02d", this.seq);
		else
			return "M" + String.format("%02d", this.seq);
	}

	public String getSensorNm() {
		return "S" + String.format("%02d", this.seq);
	}

	public String getGubun() {
		return gubun;
	}

	public void setGubun(String gubun) {
		this.gubun = gubun;
	}

	public String getSpare() {
		return spare;
	}

	public void setSpare(String spare) {
		this.spare = spare;
	}

	public String getMac() {
		return this.mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}


	public float getBatt() {
		return this.batt > (float) 3.6 ? (float) 3.6 : this.batt;
	}

	public float getBattP() {
//		return (float)(100  - (3.6 - getBatt()) / 0.001714 );
		return (float) (getBatt() * 100 / 3.6);
	}

	public void setBatt(float batt) {
		this.batt = batt;
	}

	public String getBattDt() {
		return battDt;
	}

	public void setBattDt(String battDt) {
		this.battDt = battDt.length() > 8 ? battDt.substring(0, 8) : battDt;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getLowcnt() {
		return lowcnt;
	}

	public void setLowcnt(int lowcnt) {
		this.lowcnt = lowcnt;
	}

	public short getPlace() {
		return place;
	}

	public void setPlace(short place) {
		this.place = place;
	}

	public short getStand() {
		return stand;
	}

	public void setStand(short stand) {
		this.stand = stand;
	}

	public String getMold() {
		return mold;
	}

	public void setMold(String mold) {
		this.mold = mold;
	}

	public int getObcnt() {
		return obcnt;
	}

	public void setObcnt(int obcnt) {
		this.obcnt = obcnt;
	}

	public int getTval() {
		return tval;
	}

	public void setTval(int tval) {
		this.tval = tval;
	}

	public int getSval() {
		return sval;
	}

	public void setSval(int sval) {
		this.sval = sval;
	}

	public int getMaxval() {
		return maxval;
	}

	public void setMaxval(int maxval) {
		this.maxval = maxval;
	}

	public int getMinval() {
		return minval;
	}

	public void setMinval(int minval) {
		this.minval = minval;
	}

	@Override
	public String toString() {
		return "MoteStatus [seq=" + seq + ", act=" + act + ", gubun=" + gubun + ", spare="
				+ spare + ", mac=" + mac + ", desc=" + desc + ", batt=" + batt + ", battDt=" + battDt + ", lowcnt="
				+ lowcnt + ", x=" + x + ", y=" + y + ", loc=" + loc + ", obcnt=" + obcnt + ", tval=" + tval + ", sval="
				+ sval + ", maxval=" + maxval + ", minval=" + minval + "]";
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException { 
		return super.clone();
	}


}
