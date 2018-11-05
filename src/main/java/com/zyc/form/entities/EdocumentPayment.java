package com.zyc.form.entities;

import java.sql.JDBCType;

import com.zyc.baselibs.annotation.DatabaseColumn;
import com.zyc.baselibs.annotation.DatabaseTable;
import com.zyc.baselibs.annotation.FieldRule;

@DatabaseTable(name = "edocumentpayments")
public class EdocumentPayment extends AbstractEdocumentMoneyArea<EdocumentPayment> {

	private static final long serialVersionUID = 4688762433343500527L;

	@FieldRule(required = true, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	private String receiver;

	@FieldRule(required = true, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	private String receiverlabel;

	@FieldRule(required = true, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	private String receiverbank;

	@FieldRule(required = true, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	private String receiverbankbranch;

	@FieldRule(required = true, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 64)
	private String receiveraccount;

	@FieldRule(required = true, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	private String paymethod;

	@FieldRule(required = true, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	private String paymethodlabel;

	@FieldRule(required = true, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	private String payer;

	@FieldRule(required = true, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	private String payerlabel;

	@FieldRule(required = true, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	private String payerbank;

	@FieldRule(required = true, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	private String payerbankbranch;

	@FieldRule(required = true, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 64)
	private String payeraccount;

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getReceiverlabel() {
		return receiverlabel;
	}

	public void setReceiverlabel(String receiverlabel) {
		this.receiverlabel = receiverlabel;
	}

	public String getReceiverbank() {
		return receiverbank;
	}

	public void setReceiverbank(String receiverbank) {
		this.receiverbank = receiverbank;
	}

	public String getReceiverbankbranch() {
		return receiverbankbranch;
	}

	public void setReceiverbankbranch(String receiverbankbranch) {
		this.receiverbankbranch = receiverbankbranch;
	}

	public String getReceiveraccount() {
		return receiveraccount;
	}

	public void setReceiveraccount(String receiveraccount) {
		this.receiveraccount = receiveraccount;
	}

	public String getPaymethod() {
		return paymethod;
	}

	public void setPaymethod(String paymethod) {
		this.paymethod = paymethod;
	}

	public String getPaymethodlabel() {
		return paymethodlabel;
	}

	public void setPaymethodlabel(String paymethodlabel) {
		this.paymethodlabel = paymethodlabel;
	}

	public String getPayer() {
		return payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}

	public String getPayerlabel() {
		return payerlabel;
	}

	public void setPayerlabel(String payerlabel) {
		this.payerlabel = payerlabel;
	}

	public String getPayerbank() {
		return payerbank;
	}

	public void setPayerbank(String payerbank) {
		this.payerbank = payerbank;
	}

	public String getPayerbankbranch() {
		return payerbankbranch;
	}

	public void setPayerbankbranch(String payerbankbranch) {
		this.payerbankbranch = payerbankbranch;
	}

	public String getPayeraccount() {
		return payeraccount;
	}

	public void setPayeraccount(String payeraccount) {
		this.payeraccount = payeraccount;
	}
	
}
