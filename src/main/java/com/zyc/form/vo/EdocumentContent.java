package com.zyc.form.vo;

import java.util.List;

import com.zyc.form.entities.Edocument;
import com.zyc.form.entities.EdocumentDetail;
import com.zyc.form.entities.EdocumentReversal;
import com.zyc.form.entities.EdocumentPayment;
import com.zyc.form.entities.EdocumentRepayment;
import com.zyc.form.entities.EdocumentBackup01;
import com.zyc.form.entities.EdocumentBackup02;
import com.zyc.form.entities.EdocumentBackup03;
import com.zyc.form.entities.EdocumentBackup04;

public class EdocumentContent implements java.io.Serializable {

	private static final long serialVersionUID = -1910915510020670648L;

	private Edocument main;
	
	private List<EdocumentDetail> details;
	
	private List<EdocumentReversal> reversals;
	
	private List<EdocumentPayment> payments;
				 
	private List<EdocumentRepayment> repayments;
	
	private List<EdocumentBackup01> backup01s;
	
	private List<EdocumentBackup02> backup02s;
	
	private List<EdocumentBackup03> backup03s;
	
	private List<EdocumentBackup04> backup04s;

	public Edocument getMain() {
		return main;
	}

	public void setMain(Edocument main) {
		this.main = main;
	}

	public List<EdocumentDetail> getDetails() {
		return details;
	}

	public void setDetails(List<EdocumentDetail> details) {
		this.details = details;
	}

	public List<EdocumentReversal> getReversals() {
		return reversals;
	}

	public void setReversals(List<EdocumentReversal> reversals) {
		this.reversals = reversals;
	}

	public List<EdocumentPayment> getPayments() {
		return payments;
	}

	public void setPayments(List<EdocumentPayment> payments) {
		this.payments = payments;
	}

	public List<EdocumentRepayment> getRepayments() {
		return repayments;
	}

	public void setRepayments(List<EdocumentRepayment> repayments) {
		this.repayments = repayments;
	}

	public List<EdocumentBackup01> getBackup01s() {
		return backup01s;
	}

	public void setBackup01s(List<EdocumentBackup01> backup01s) {
		this.backup01s = backup01s;
	}

	public List<EdocumentBackup02> getBackup02s() {
		return backup02s;
	}

	public void setBackup02s(List<EdocumentBackup02> backup02s) {
		this.backup02s = backup02s;
	}

	public List<EdocumentBackup03> getBackup03s() {
		return backup03s;
	}

	public void setBackup03s(List<EdocumentBackup03> backup03s) {
		this.backup03s = backup03s;
	}

	public List<EdocumentBackup04> getBackup04s() {
		return backup04s;
	}

	public void setBackup04s(List<EdocumentBackup04> backup04s) {
		this.backup04s = backup04s;
	}
	
}
