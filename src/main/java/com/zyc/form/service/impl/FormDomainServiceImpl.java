package com.zyc.form.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zyc.baselibs.annotation.FieldRule;
import com.zyc.baselibs.annotation.FieldRuleUtils;
import com.zyc.baselibs.aopv.ParamVerification;
import com.zyc.baselibs.commons.CollectionUtils;
import com.zyc.baselibs.commons.StringUtils;
import com.zyc.baselibs.entities.DataStatus;
import com.zyc.baselibs.ex.BussinessException;
import com.zyc.baselibs.ex.IllegalValueException;
import com.zyc.baselibs.service.AbstractSelectByPageService;
import com.zyc.baselibs.vo.DeleteMode;
import com.zyc.baselibs.vo.EntryBean;
import com.zyc.baselibs.vo.Pagination;
import com.zyc.form.dao.CtrlDimSourceMapper;
import com.zyc.form.dao.FormDomainMapper;
import com.zyc.form.entities.CtrlDimSource;
import com.zyc.form.entities.FormDomain;
import com.zyc.form.service.FormDomainService;
import com.zyc.form.vo.CtrlDimSourceOptionVO;
import com.zyc.form.vo.FormDomainQueryVO;
import com.zyc.form.vo.FormDomainVO;

@Service
public class FormDomainServiceImpl extends AbstractSelectByPageService implements FormDomainService {

	@Autowired
	private FormDomainMapper formDomainMapper;

	@Autowired
	private CtrlDimSourceMapper ctrlDimSourceMapper;
	
	public static final List<FormDomainVO> testData = new ArrayList<FormDomainVO>();
	
	static {
		FormDomainVO vo;
		FormDomain fd;
		CtrlDimSource cds;
		for (int i = 0; i < 10; i++) {
			fd = new FormDomain();
			fd.init();
			fd.setId(UUID.randomUUID().toString());
			fd.setDomaincode(StringUtils.randomAlphabets(10));
			fd.setDomainname(fd.getDomaincode());
			vo = new FormDomainVO(fd);
			
			cds = new CtrlDimSource();
			cds.init();
			cds.setId(UUID.randomUUID().toString());
			cds.setFormdomainid(fd.getId());
			cds.setDimid(UUID.randomUUID().toString());
			cds.setDimcode(StringUtils.randomAlphabets(10));
			cds.setDimname(cds.getDimcode());
			cds.setExpression("+@2(" + cds.getDimid() + ");");
			cds.setExpressiontext("包含@成员及后代（" + cds.getDimcode() + "）");
			
			vo.addCtrlDimSource(cds, true);
			
			testData.add(vo);
		}
	}

	@Override
	public List<EntryBean> allDomainEntryBeans() {
		List<EntryBean> entryBeans = null; 
		List<FormDomain> domains = this.formDomainMapper.select(new FormDomain().clean());
		if(CollectionUtils.hasElement(domains)) {
			entryBeans = new ArrayList<EntryBean>();
			for (FormDomain domain : domains) {
				entryBeans.add(new EntryBean(domain.getId(), domain.label()));
			}
		}
		return entryBeans;
	}
	
	@Override
	public List<FormDomainVO> selectAll() {
		List<FormDomain> domains = this.formDomainMapper.select(new FormDomain().clean());
		return this.fromEntities(domains);
	}

	@Override
	public List<FormDomainVO> selectByPage(FormDomainQueryVO condition, String keyword, Pagination pagination) {
		List<FormDomain> domains = this.selectByPage(this.formDomainMapper, condition.copyEntity(), keyword, pagination);
		return this.fromEntities(domains);
	}
	
	private List<FormDomainVO> fromEntities(List<FormDomain> domains) {
		List<FormDomainVO> result = null;
		if(CollectionUtils.hasElement(domains)) {
			result = new ArrayList<FormDomainVO>();
			CtrlDimSource cds = new CtrlDimSource().clean();
			FormDomainVO vo = null;
			for (FormDomain formDomain : domains) {
				vo = new FormDomainVO(formDomain);
				
				cds.setFormdomainid(formDomain.getId());
				vo.addCtrlDimSources(this.ctrlDimSourceMapper.select(cds), true);

				result.add(vo);
			}
		}
		return result;
	}
	
	@Override
	public FormDomainVO selectByFormDomainId(String formdomainid) {
		if(StringUtils.isBlank(formdomainid)) {
			return null;
		}
		
		FormDomain formDomain = this.formDomainMapper.load(formdomainid, FormDomain.class);
		if(formDomain == null) {
			return null;
		}
		
		FormDomainVO vo = new FormDomainVO(formDomain);
		
		CtrlDimSource cds = new CtrlDimSource().clean();
		cds.setFormdomainid(formdomainid);
		vo.addCtrlDimSources(this.ctrlDimSourceMapper.select(cds), true);
		
		return vo;
	}

	@Override
	public FormDomainVO selectByFormDomainCode(String domaincode) {
		if(StringUtils.isBlank(domaincode)) {
			return null;
		}
		
		FormDomain condition = new FormDomain().clean();
		condition.setDomaincode(domaincode);
		
		List<FormDomain> formDomains = this.formDomainMapper.select(condition);
		FormDomain formDomain = CollectionUtils.hasElement(formDomains) ? formDomains.get(0) : null;
		if(formDomain == null) {
			return null;
		}
		
		FormDomainVO vo = new FormDomainVO(formDomain);

		CtrlDimSource cds = new CtrlDimSource().clean();
		cds.setFormdomainid(formDomain.getId());
		vo.addCtrlDimSources(this.ctrlDimSourceMapper.select(cds), true);
		
		return vo;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@ParamVerification(rules = { FieldRule.class })
	public FormDomainVO create(FormDomainVO vo) throws Exception {
		FormDomain fd = vo.copyEntity();
		//依据id与code进行判重
		if(this.formDomainMapper.load(fd.getId(), FormDomain.class) != null || this.selectByFormDomainCode(fd.getDomaincode()) != null) {
			throw new BussinessException("This form domain already exists. (id=" + fd.getId() + ", domaincode=" + fd.getDomaincode() + ")");
		}
		
		fd.init();
		int result = this.formDomainMapper.insert(fd);
		FormDomain newfd =  result > 0 ? this.formDomainMapper.load(fd.getId(), FormDomain.class) : null;
		if(newfd == null) {
			throw new BussinessException("Created 'FormDomain' failed.");
		}
		
		FormDomainVO _new = new FormDomainVO(newfd);
		_new.addCtrlDimSources(this.createCtrlDimSource(vo.getCtrlDimSources(), newfd.getId()), true);
		
		return _new;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<CtrlDimSource> createCtrlDimSource(List<CtrlDimSourceOptionVO> ctrlDimSources, String formdomainid) throws Exception {
		List<CtrlDimSource> _new = null;
		
		//查询旧数据...
		CtrlDimSource condition = new CtrlDimSource();
		condition.clean().setFormdomainid(formdomainid);
		List<CtrlDimSource> olds = this.ctrlDimSourceMapper.select(condition);
		
		//如果存在待保存的数据，则开始保存这些数据...
		if(CollectionUtils.hasElement(ctrlDimSources)) {
			_new = new ArrayList<CtrlDimSource>();
			for (CtrlDimSourceOptionVO ctrlDimSource : ctrlDimSources) {
				if(!ctrlDimSource.isEnabled()) {
					continue; //没有启用的控制维度不需要处理。
				}

				CtrlDimSource old = null, newCtrlDimSource = null;
				if(CollectionUtils.hasElement(olds)) {
					for (CtrlDimSource item : olds) {
						if(item.businessEquals(ctrlDimSource)) {
							old = item; //尝试在旧数据中寻找与当前待保存数据一致的数据，找到了则记录下来。
							break;
						}
					}
				}
				
				if(old == null) {
					newCtrlDimSource = this.createCtrlDimSource(ctrlDimSource, formdomainid); //当前待保存数据没有被保存过，则创建。
				} else {
					BeanUtils.copyProperties(ctrlDimSource, old, FieldRuleUtils.uneditableFields(old));
					this.update(this.ctrlDimSourceMapper, old, ACTION_UPDATE); //当前待保存数据已经被保存过，则更新。
					newCtrlDimSource = old;
					olds.remove(old);
				}
				
				if(newCtrlDimSource == null) {
					throw new BussinessException("Created 'CtrlDimSource' failed."); //创建或更新失败，则抛异常，确保事务回滚。
				}
				_new.add(newCtrlDimSource);
			}
		}

		//没有被当前待保存的控制维度所匹配的旧数据，在此处开始予以物理删除...
		if(CollectionUtils.hasElement(olds)) {
			for (CtrlDimSource item : olds) {
				this.ctrlDimSourceMapper.delete(item);
			}
		}
		
		return _new;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@ParamVerification(rules = { FieldRule.class })
	public CtrlDimSource createCtrlDimSource(CtrlDimSourceOptionVO vo, String formdomainid) throws Exception {
		CtrlDimSource source = vo.copyEntity();
		//formdomainid为表单域主键ID，该ID为当前待添加数据的外键（表单域主键ID）的正确值
		if(StringUtils.isBlank(formdomainid) || !formdomainid.equals(vo.getFormdomainid())) {
			throw new IllegalValueException(String.format("The data 'formdomainid' does not match. (formdomainid: %s!=%s)", vo.getFormdomainid(), formdomainid));
		}
		
		//依据id与formdomainid进行判重
		if(this.ctrlDimSourceMapper.load(vo.getId(), CtrlDimSource.class) != null) {
			throw new BussinessException("This dimension source already exists. (id=" + vo.getId() + ", formdomainid=" + vo.getFormdomainid() + ")");
		}
		
		source.init();
		int result = this.ctrlDimSourceMapper.insert(source);
		return result > 0 ? this.ctrlDimSourceMapper.load(source.getId(), CtrlDimSource.class) : null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@ParamVerification(rules = { FieldRule.class })
	public FormDomainVO modify(FormDomainVO vo) throws Exception {
		FormDomain domain = vo.copyEntity();
		
		FormDomain old = this.formDomainMapper.load(domain.getId(), FormDomain.class);
		if(old == null || !domain.businessEquals(old)) {
			throw new BussinessException("This form domain does not exist or data does not matchs. (domain code=" + domain.getDomaincode() + ")");
		}

		BeanUtils.copyProperties(domain, old, FieldRuleUtils.uneditableFields(old));
		this.update(this.formDomainMapper, old, ACTION_UPDATE);
		
		FormDomainVO _new = new FormDomainVO(old);
		_new.addCtrlDimSources(this.createCtrlDimSource(vo.getCtrlDimSources(), _new.getId()), true);
		return _new;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean deleteOnLogic(String formdomainid) throws Exception {
		FormDomain domain = this.loadDeletableEntity(formdomainid, FormDomain.class, this.formDomainMapper, DeleteMode.LOGIC);
		domain.setDatastatus(DataStatus.DELETED.getValue());
		boolean success = this.update(this.formDomainMapper, domain, ACTION_DELETE) > 0;
		if(success && domain.getEnabledbudgetctrl()) {
			CtrlDimSource cds = new CtrlDimSource().clean();
			cds.setFormdomainid(domain.getId());
			List<CtrlDimSource> cdss = this.ctrlDimSourceMapper.select(cds);
			if(CollectionUtils.hasElement(cdss)) {
				for (CtrlDimSource source : cdss) {
					source.setDatastatus(DataStatus.DELETED.getValue());
					this.update(this.ctrlDimSourceMapper, source, ACTION_DELETE);
				}
			}
		}
		return success;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean deleteOnPhysical(String formdomainid) throws Exception {
		FormDomain domain = this.loadDeletableEntity(formdomainid, FormDomain.class, this.formDomainMapper, DeleteMode.PHYSICAL);
		boolean success = this.formDomainMapper.deleteById(domain.getId(), domain.getVersion(), FormDomain.class) > 0;
		if(success && domain.getEnabledbudgetctrl()) {
			CtrlDimSource cds = new CtrlDimSource().clean();
			cds.setFormdomainid(domain.getId());
			this.ctrlDimSourceMapper.delete(cds);
		}
		return success;
	}
}
