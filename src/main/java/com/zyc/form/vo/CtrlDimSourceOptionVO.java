package com.zyc.form.vo;

import org.springframework.beans.BeanUtils;

import com.zyc.baselibs.entities.EntityCopyable;
import com.zyc.form.client.vo.DimensionVO;
import com.zyc.form.entities.CtrlDimSource;

public class CtrlDimSourceOptionVO extends CtrlDimSource implements EntityCopyable<CtrlDimSource> {
	
	private boolean enabled = false;

	public CtrlDimSourceOptionVO() {
	}
	
	public CtrlDimSourceOptionVO(CtrlDimSource ctrlDimSource) {
		BeanUtils.copyProperties(ctrlDimSource, this);
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public static CtrlDimSourceOptionVO newInstance(DimensionVO dimVo) {
		CtrlDimSourceOptionVO vo = new CtrlDimSourceOptionVO();
		BeanUtils.copyProperties(dimVo, vo);
		vo.init();
		vo.createIdWhenNot();
		return vo;
	}

	@Override
	public CtrlDimSource copyEntity() {
		CtrlDimSource source = new CtrlDimSource();
		BeanUtils.copyProperties(this, source);
		return source;
	}
}
