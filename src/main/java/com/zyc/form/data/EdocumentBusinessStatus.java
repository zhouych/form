package com.zyc.form.data;

public enum EdocumentBusinessStatus {
	PENDING("pending", "待提交"),
	PROCESS_REJECT_TOME("process_reject_tome", "驳回（直送至我）"),
	PROCESS_REJECT_STEPBY("process_reject_stepby", "驳回（逐级审批）"),
	PROCESS_APPROVING("process_approving", "审批中"),
	PROCESS_COMPLETED("process_completed", "已完成");
	
	private String value;
	private String text;

	private EdocumentBusinessStatus(String value, String text) {
		this.value = value;
		this.text = text;
	}

	public String getValue() {
		return value;
	}

	public String getText() {
		return text;
	}
}
