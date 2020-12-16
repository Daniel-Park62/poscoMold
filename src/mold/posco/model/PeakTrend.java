package mold.posco.model;

import java.sql.Timestamp;

public class PeakTrend {

	private Timestamp tm;
	private int val;
	
	public PeakTrend(Timestamp tm,  int val) {
		this.tm = tm;
		this.val = val;
	}
	
	public Timestamp getTm() {
		return tm;
	}
	public int getVal() {
		return val;
	}

}
