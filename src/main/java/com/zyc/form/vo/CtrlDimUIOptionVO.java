package com.zyc.form.vo;

import org.springframework.beans.BeanUtils;

import com.zyc.form.entities.CtrlDimSource;

public class CtrlDimUIOptionVO extends CtrlDimSource {
	
	private boolean enabled = false;

	public CtrlDimUIOptionVO() {
	}
	
	public CtrlDimUIOptionVO(CtrlDimSource ctrlDimSource) {
		BeanUtils.copyProperties(ctrlDimSource, this);
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
