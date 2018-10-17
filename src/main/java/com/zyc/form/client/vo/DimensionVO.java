package com.zyc.form.client.vo;

public class DimensionVO implements java.io.Serializable {
	
	private static final long serialVersionUID = -9061047037289295275L;
	
	private String dimid;
	private String dimname;
	private String dimcode;
	
	public DimensionVO() {
	}
	
	public DimensionVO(String dimid, String dimname, String dimcode) {
		this.dimid = dimid;
		this.dimname = dimname;
		this.dimcode = dimcode;
	}
	
	public String getDimid() {
		return dimid;
	}
	public void setDimid(String dimid) {
		this.dimid = dimid;
	}
	public String getDimname() {
		return dimname;
	}
	public void setDimname(String dimname) {
		this.dimname = dimname;
	}
	public String getDimcode() {
		return dimcode;
	}
	public void setDimcode(String dimcode) {
		this.dimcode = dimcode;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
