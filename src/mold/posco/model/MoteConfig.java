package mold.posco.model;

import java.lang.String;
import java.sql.Timestamp;
import org.eclipse.swt.graphics.Point;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: MoteConfig
 *
 */
@Entity
@Table(name="moteConfig")
@NamedQuery(
		  name = "MoteConfig.findone",
		  query = "select a from MoteConfig a where a.id = 1 "
		)

public class MoteConfig  {
	
	private static MoteConfig instance = new MoteConfig();
	public static MoteConfig getInstance() {
		return instance;
	}
	   
	@Id
	private short id = 1;
	private String sysCode;
	private String adgb;
	private short measure;
	private double maxtemp;
	private double mintemp;
	private double maxhumi;
	private double minhumi;
	private int s1tx;
	private int s1ty;
	private int s1bx;
	private int s1by;
	private int s2tx;
	private int s2ty;
	private int s2bx;
	private int s2by;
	private int s3tx;
	private int s3ty;
	private int s3bx;
	private int s3by;
	private int s4tx;
	private int s4ty;
	private int s4bx;
	private int s4by;
	private Timestamp tm;

	private MoteConfig() {}   
	public short getId() {
		return this.id;
	}

	public void setId(short id) {
		this.id = id;
	}   
	public String getSysCode() {
		return this.sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}   
	public String getAdgb() {
		return adgb;
	}
	public void setAdgb(String adgb) {
		this.adgb = adgb;
	}
	public short getMeasure() {
		return this.measure;
	}

	public void setMeasure(short measure) {
		this.measure = measure;
	}   
	public double getMaxtemp() {
		return maxtemp;
	}
	public void setMaxtemp(double maxtemp) {
		this.maxtemp = maxtemp;
	}
	public double getMintemp() {
		return mintemp;
	}
	public void setMintemp(double mintemp) {
		this.mintemp = mintemp;
	}
	public double getMaxhumi() {
		return maxhumi;
	}
	public void setMaxhumi(double maxhumi) {
		this.maxhumi = maxhumi;
	}
	public double getMinhumi() {
		return minhumi;
	}
	public void setMinhumi(double minhumi) {
		this.minhumi = minhumi;
	}
	public int getS1tx() {
		return this.s1tx;
	}

	public void setS1tx(int s1tx) {
		this.s1tx = s1tx;
	}   
	public int getS1ty() {
		return this.s1ty;
	}

	public void setS1ty(int s1ty) {
		this.s1ty = s1ty;
	}
	public int getS1bx() {
		return s1bx;
	}
	public void setS1bx(int s1bx) {
		this.s1bx = s1bx;
	}
	public int getS1by() {
		return s1by;
	}
	public void setS1by(int s1by) {
		this.s1by = s1by;
	}
	public int getS2tx() {
		return s2tx;
	}
	public void setS2tx(int s2tx) {
		this.s2tx = s2tx;
	}
	public int getS2ty() {
		return s2ty;
	}
	public void setS2ty(int s2ty) {
		this.s2ty = s2ty;
	}
	public int getS2bx() {
		return s2bx;
	}
	public void setS2bx(int s2bx) {
		this.s2bx = s2bx;
	}
	public int getS2by() {
		return s2by;
	}
	public void setS2by(int s2by) {
		this.s2by = s2by;
	}
	public int getS3tx() {
		return s3tx;
	}
	public void setS3tx(int s3tx) {
		this.s3tx = s3tx;
	}
	public int getS3ty() {
		return s3ty;
	}
	public void setS3ty(int s3ty) {
		this.s3ty = s3ty;
	}
	public int getS3bx() {
		return s3bx;
	}
	public void setS3bx(int s3bx) {
		this.s3bx = s3bx;
	}
	public int getS3by() {
		return s3by;
	}
	public void setS3by(int s3by) {
		this.s3by = s3by;
	}
	public int getS4tx() {
		return s4tx;
	}
	public void setS4tx(int s4tx) {
		this.s4tx = s4tx;
	}
	public int getS4ty() {
		return s4ty;
	}
	public void setS4ty(int s4ty) {
		this.s4ty = s4ty;
	}
	public int getS4bx() {
		return s4bx;
	}
	public void setS4bx(int s4bx) {
		this.s4bx = s4bx;
	}
	public int getS4by() {
		return s4by;
	}
	public void setS4by(int s4by) {
		this.s4by = s4by;
	}
	public Timestamp getTm() {
		return tm;
	}
	public void setTm(Timestamp tm) {
		this.tm = tm;
	}

	public Point getXy(int ix) {
		int x = 100, y = 100;
		switch ( ix ) {
		case  0:
			x = getS1tx();	y = getS1ty();
			break ;
		case  1:
			x = getS1bx();	y = getS1by();
			break ;
		case  2:
			x = getS2tx();	y = getS2ty();
			break ;
		case  3:
			x = getS2bx();	y = getS2by();
			break ;
		case  4:
			x = getS3tx();	y = getS3ty();
			break ;
		case  5:
			x = getS3bx();	y = getS3by();
			break ;
		case  6:
			x = getS4tx();	y = getS4ty();
			break ;
		case  7:
			x = getS4bx();	y = getS4by();
			break ;
		}
		return new Point(x, y);
	}
	public void setXy(int ix, int x, int y) {
		switch ( ix ) {
		case  0:
			setS1tx(x);	setS1ty(y);
			break ;
		case  1:
			setS1bx(x);	setS1by(y);
			break ;
		case  2:
			setS2tx(x);	setS2ty(y);
			break ;
		case  3:
			setS2bx(x);	setS2by(y);
			break ;
		case  4:
			setS3tx(x);	setS3ty(y);
			break ;
		case  5:
			setS3bx(x);	setS3by(y);
			break ;
		case  6:
			setS4tx(x);	setS4ty(y);
			break ;
		case  7:
			setS4bx(x);	setS4by(y);
			break ;
		}
		
	}
}
