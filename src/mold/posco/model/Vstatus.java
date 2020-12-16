package mold.posco.model;

import java.io.Serializable;
import javax.persistence.*;

import org.eclipse.persistence.annotations.ReadOnly;

/**
 * The persistent class for the vstatus database table.
 * 
 */
@ReadOnly
@Entity
@NamedQuery(name="Vstatus.find", query="SELECT v FROM Vstatus v")
public class Vstatus implements Serializable {
	private static final long serialVersionUID = 1L;

	private static Vstatus instance = new Vstatus();
	public static Vstatus getInstance() {
		return instance;
	}

	@Id
	private int id = 1;

	private int actcnt;

	private int inactcnt;

	private int lbcnt;

	private int obcnt;

	private int sactcnt;

	private int sinactcnt;

	private int slbcnt;

	private Vstatus() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getActcnt() {
		return this.actcnt;
	}

	public void setActcnt(int actcnt) {
		this.actcnt = actcnt;
	}

	public int getInactcnt() {
		return this.inactcnt;
	}

	public void setInactcnt(int inactcnt) {
		this.inactcnt = inactcnt;
	}

	public int getLbcnt() {
		return this.lbcnt;
	}

	public void setLbcnt(int lbcnt) {
		this.lbcnt = lbcnt;
	}

	public int getObcnt() {
		return this.obcnt;
	}

	public void setObcnt(int obcnt) {
		this.obcnt = obcnt;
	}

	public int getSactcnt() {
		return this.sactcnt;
	}

	public void setSactcnt(int sactcnt) {
		this.sactcnt = sactcnt;
	}

	public int getSinactcnt() {
		return this.sinactcnt;
	}

	public void setSinactcnt(int sinactcnt) {
		this.sinactcnt = sinactcnt;
	}

	public int getSlbcnt() {
		return this.slbcnt;
	}

	public void setSlbcnt(int slbcnt) {
		this.slbcnt = slbcnt;
	}

}