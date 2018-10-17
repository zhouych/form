package com.zyc.form.vo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.zyc.baselibs.aopv.Verifiable;
import com.zyc.form.client.vo.DimensionVO;
import com.zyc.form.entities.CtrlDimSource;
import com.zyc.form.entities.FormDomain;

@Verifiable
public class FormDomainVO extends FormDomain {
	
	private static final long serialVersionUID = -7241150664326724541L;
	
	private List<CtrlDimSource> ctrlDimSources;
	
	private List<CtrlDimUIOptionVO> ctrlDimUIOptionVos;

	public FormDomainVO() {
		
	}
	
	public FormDomainVO(FormDomain formDomain) {
		BeanUtils.copyProperties(formDomain, this);
	}
	
	public List<CtrlDimSource> getCtrlDimSources() {
		return ctrlDimSources;
	}

	public void setCtrlDimSources(List<CtrlDimSource> ctrlDimSources) {
		this.ctrlDimSources = ctrlDimSources;
	}
	
	public static FormDomainVO newInstance() {
		FormDomainVO vo = new FormDomainVO();
		vo.init();
		vo.generateId();
		return vo;
	}

	public List<CtrlDimUIOptionVO> getCtrlDimUIOptionVos() {
		return ctrlDimUIOptionVos;
	}

	public void setCtrlDimUIOptionVos(List<DimensionVO> dimvos) {
		if(this.ctrlDimUIOptionVos == null) {
			this.ctrlDimUIOptionVos = new ArrayList<CtrlDimUIOptionVO>();
		} else {
			this.ctrlDimUIOptionVos.clear();
		}
		
		CtrlDimUIOptionVO option; 
		for (DimensionVO dimvo : dimvos) {
			option = null;
			if(this.getCtrlDimSources() != null && !this.getCtrlDimSources().isEmpty()) {
				for (CtrlDimSource cds : this.getCtrlDimSources()) {
					if(cds.getDimid().equals(dimvo.getDimid())) {
						option = new CtrlDimUIOptionVO(cds);
						option.setEnabled(true);
						break;
					}
				}	
			}
			
			if(option == null) {
				option = new CtrlDimUIOptionVO();
				BeanUtils.copyProperties(dimvo, option);
			}
			
			this.ctrlDimUIOptionVos.add(option);
		}
	}
}
