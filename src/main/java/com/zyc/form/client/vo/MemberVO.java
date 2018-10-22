package com.zyc.form.client.vo;

import java.io.Serializable;

public class MemberVO implements Serializable {

	private static final long serialVersionUID = -4934044482488794506L;

	private String id;
	private String parentid;
	private String membername;
	private String membercode;
	private boolean haschildren;

	public MemberVO() {
	}
	
	public MemberVO(String id, String parentid, String membername, String membercode, boolean haschildren) {
		this.id = id;
		this.parentid = parentid;
		this.membername = membername;
		this.membercode = membercode;
		this.haschildren = haschildren;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public String getMembername() {
		return membername;
	}
	public void setMembername(String membername) {
		this.membername = membername;
	}
	public String getMembercode() {
		return membercode;
	}
	public void setMembercode(String membercode) {
		this.membercode = membercode;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public boolean isHaschildren() {
		return haschildren;
	}

	public void setHaschildren(boolean haschildren) {
		this.haschildren = haschildren;
	}
}
