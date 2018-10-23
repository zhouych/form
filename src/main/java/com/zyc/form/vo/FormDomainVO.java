package com.zyc.form.vo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.zyc.baselibs.aopv.Verifiable;
import com.zyc.baselibs.commons.CollectionUtils;
import com.zyc.form.client.vo.DimensionVO;
import com.zyc.form.entities.CtrlDimSource;
import com.zyc.form.entities.FormDomain;

@Verifiable
public class FormDomainVO extends FormDomain {
	
	private static final long serialVersionUID = -7241150664326724541L;
	
	private List<CtrlDimSourceOptionVO> ctrlDimSources;

	public FormDomainVO() {
		this.ctrlDimSources = new ArrayList<CtrlDimSourceOptionVO>();
	}
	
	public FormDomainVO(FormDomain formDomain) {
		BeanUtils.copyProperties(formDomain, this);
		this.ctrlDimSources = new ArrayList<CtrlDimSourceOptionVO>();
	}
	
	public static FormDomainVO newInstance() {
		FormDomainVO vo = new FormDomainVO();
		vo.init();
		vo.createIdWhenNot();
		return vo;
	}

	public List<CtrlDimSourceOptionVO> getCtrlDimSources() {
		return ctrlDimSources;
	}
	
	public void addCtrlDimSource(CtrlDimSource source, boolean enabled) {
		CtrlDimSourceOptionVO vo = null;
		for (CtrlDimSourceOptionVO item : this.getCtrlDimSources()) {
			if(item.businessEquals(source)) {
				vo = item;
				break;
			}
		}
		
		//没有重复则添加，如果重复则覆盖。
		if(vo == null) {
			vo = new CtrlDimSourceOptionVO(source);
			vo.setEnabled(enabled);
			this.getCtrlDimSources().add(vo);
		} else {
			BeanUtils.copyProperties(source, vo);
			vo.setEnabled(enabled);
		}
	}
	
	public void addCtrlDimSources(List<CtrlDimSource> sources, boolean enabled) {
		if(CollectionUtils.hasElement(sources)) {
			for (CtrlDimSource source : sources) {
				this.addCtrlDimSource(source, enabled);
			}
		}
	}

	public void addCtrlDimSourceOption(DimensionVO dimension) {
		CtrlDimSourceOptionVO vo = null;
		for (CtrlDimSourceOptionVO item : this.getCtrlDimSources()) {
			if(item.getDimid().equals(dimension.getDimid()) || item.getDimcode().equals(dimension.getDimcode())) {
				vo = item;
				break;
			}
		}

		//没有重复则添加，如果重复则覆盖。
		if(vo == null) {
			vo = CtrlDimSourceOptionVO.newInstance(dimension);
			this.getCtrlDimSources().add(vo);
		} else {
			BeanUtils.copyProperties(dimension, vo);
		}
	}
	
	public void addCtrlDimSourceOptions(List<DimensionVO> dimensions) {
		if(CollectionUtils.hasElement(dimensions)) {
			for (DimensionVO dimension : dimensions) {
				this.addCtrlDimSourceOption(dimension);
			}
		}
	}
}
