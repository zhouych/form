package com.zyc.form.entities;

import java.math.BigDecimal;
import java.sql.JDBCType;
import java.util.Date;

import org.apache.commons.codec.binary.StringUtils;

import com.zyc.baselibs.annotation.DatabaseColumn;
import com.zyc.baselibs.annotation.DatabaseTable;
import com.zyc.baselibs.annotation.EnumMapping;
import com.zyc.baselibs.annotation.FieldRule;
import com.zyc.baselibs.annotation.Mainfield;
import com.zyc.baselibs.annotation.Subfield;
import com.zyc.baselibs.entities.Businessable;
import com.zyc.baselibs.entities.DescriptionBaseEntity;
import com.zyc.baselibs.entities.Labelable;
import com.zyc.form.data.EdocumentBusinessStatus;

/**
 * 电子文档
 * @author zhouyancheng
 *
 */
@DatabaseTable(name = "edocuments")
public class Edocument extends DescriptionBaseEntity implements java.io.Serializable, Labelable, Businessable<Edocument> {
	
	private static final long serialVersionUID = 5016561878123160022L;

	@FieldRule(required = true, externalUneditable = true)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	@Mainfield(subfields = { "formidlabel" })
	private String formid;

	@FieldRule(required = true, externalUneditable = true)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	@Subfield(mainfield = "formid")
	private String formidlabel;
	
	@FieldRule(required = true, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	private String title;

	@FieldRule(required = true, externalUneditable = true)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 64)
	private String edocumentnumber;
	
	@FieldRule(required = true, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.TIMESTAMP)
	private Date occurdate;

	@FieldRule(required = true, externalUneditable = true)
	@EnumMapping(enumClazz = EdocumentBusinessStatus.class)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 64)
	private String businessstatus;

	@FieldRule(required = true, externalUneditable = true)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	@Mainfield(subfields = { "creatorlabel" })
	private String creator;
	
	@FieldRule(required = true, externalUneditable = true)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	@Subfield(mainfield = "creator")
	private String creatorlabel;

	@FieldRule(required = true, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	@Mainfield(subfields = { "creatordeptlabel" })
	private String creatordept;
	
	@FieldRule(required = true, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	@Subfield(mainfield = "creatordept")
	private String creatordeptlabel;

	@FieldRule(required = true, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	@Mainfield(subfields = { "creatorcompanylabel" })
	private String creatorcompany;
	
	@FieldRule(required = true, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	@Subfield(mainfield = "creatorcompany")
	private String creatorcompanylabel;

	@FieldRule(required = true, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	@Mainfield(subfields = { "applicantlabel" })
	private String applicant;
	
	@FieldRule(required = true, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	@Subfield(mainfield = "applicant")
	private String applicantlabel;

	@FieldRule(required = true, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	@Mainfield(subfields = { "applicantdeptlabel" })
	private String applicantdept;
	
	@FieldRule(required = true, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	@Subfield(mainfield = "applicantdept")
	private String applicantdeptlabel;

	@FieldRule(required = true, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	@Mainfield(subfields = { "applicantcompanylabel" })
	private String applicantcompany;
	
	@FieldRule(required = true, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	@Subfield(mainfield = "applicantcompany")
	private String applicantcompanylabel;

	@FieldRule(required = true, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	@Mainfield(subfields = { "originalcurrencylabel" })
	private String originalcurrency;
	
	@FieldRule(required = true, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	@Subfield(mainfield = "originalcurrency")
	private String originalcurrencylabel;
	
	@FieldRule(required = true, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	@Mainfield(subfields = { "naturalcurrencylabel" })
	private String naturalcurrency;
	
	@FieldRule(required = true, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	@Subfield(mainfield = "naturalcurrency")
	private String naturalcurrencylabel;

	@FieldRule(required = true, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.DECIMAL)
	private BigDecimal exchangerate;

	@FieldRule(required = true, externalUneditable = true)
	@DatabaseColumn(jdbcType = JDBCType.DOUBLE)
	private Double originalmoneytotal;

	@FieldRule(required = true, externalUneditable = true)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 64)
	private String originalmoneytotalcapital;

	@FieldRule(required = true, externalUneditable = true)
	@DatabaseColumn(jdbcType = JDBCType.DOUBLE)
	private Double naturalmoneytotal;

	@FieldRule(required = true, externalUneditable = true)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 64)
	private String naturalmoneytotalcapital;
	
	public String getFormid() {
		return formid;
	}

	public void setFormid(String formid) {
		this.formid = formid;
	}

	public String getFormidlabel() {
		return formidlabel;
	}

	public void setFormidlabel(String formidlabel) {
		this.formidlabel = formidlabel;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEdocumentnumber() {
		return edocumentnumber;
	}

	public void setEdocumentnumber(String edocumentnumber) {
		this.edocumentnumber = edocumentnumber;
	}

	public Date getOccurdate() {
		return occurdate;
	}

	public void setOccurdate(Date occurdate) {
		this.occurdate = occurdate;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreatorlabel() {
		return creatorlabel;
	}

	public void setCreatorlabel(String creatorlabel) {
		this.creatorlabel = creatorlabel;
	}

	public String getCreatordept() {
		return creatordept;
	}

	public void setCreatordept(String creatordept) {
		this.creatordept = creatordept;
	}

	public String getCreatordeptlabel() {
		return creatordeptlabel;
	}

	public void setCreatordeptlabel(String creatordeptlabel) {
		this.creatordeptlabel = creatordeptlabel;
	}

	public String getCreatorcompany() {
		return creatorcompany;
	}

	public void setCreatorcompany(String creatorcompany) {
		this.creatorcompany = creatorcompany;
	}

	public String getCreatorcompanylabel() {
		return creatorcompanylabel;
	}

	public void setCreatorcompanylabel(String creatorcompanylabel) {
		this.creatorcompanylabel = creatorcompanylabel;
	}

	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}

	public String getApplicantlabel() {
		return applicantlabel;
	}

	public void setApplicantlabel(String applicantlabel) {
		this.applicantlabel = applicantlabel;
	}

	public String getApplicantdept() {
		return applicantdept;
	}

	public void setApplicantdept(String applicantdept) {
		this.applicantdept = applicantdept;
	}

	public String getApplicantdeptlabel() {
		return applicantdeptlabel;
	}

	public void setApplicantdeptlabel(String applicantdeptlabel) {
		this.applicantdeptlabel = applicantdeptlabel;
	}

	public String getApplicantcompany() {
		return applicantcompany;
	}

	public void setApplicantcompany(String applicantcompany) {
		this.applicantcompany = applicantcompany;
	}

	public String getApplicantcompanylabel() {
		return applicantcompanylabel;
	}

	public void setApplicantcompanylabel(String applicantcompanylabel) {
		this.applicantcompanylabel = applicantcompanylabel;
	}

	public String getOriginalcurrency() {
		return originalcurrency;
	}

	public void setOriginalcurrency(String originalcurrency) {
		this.originalcurrency = originalcurrency;
	}

	public String getOriginalcurrencylabel() {
		return originalcurrencylabel;
	}

	public void setOriginalcurrencylabel(String originalcurrencylabel) {
		this.originalcurrencylabel = originalcurrencylabel;
	}

	public String getNaturalcurrency() {
		return naturalcurrency;
	}

	public void setNaturalcurrency(String naturalcurrency) {
		this.naturalcurrency = naturalcurrency;
	}

	public String getNaturalcurrencylabel() {
		return naturalcurrencylabel;
	}

	public void setNaturalcurrencylabel(String naturalcurrencylabel) {
		this.naturalcurrencylabel = naturalcurrencylabel;
	}

	public BigDecimal getExchangerate() {
		return exchangerate;
	}

	public void setExchangerate(BigDecimal exchangerate) {
		this.exchangerate = exchangerate;
	}

	public Double getOriginalmoneytotal() {
		return originalmoneytotal;
	}

	public void setOriginalmoneytotal(Double originalmoneytotal) {
		this.originalmoneytotal = originalmoneytotal;
	}

	public String getOriginalmoneytotalcapital() {
		return originalmoneytotalcapital;
	}

	public void setOriginalmoneytotalcapital(String originalmoneytotalcapital) {
		this.originalmoneytotalcapital = originalmoneytotalcapital;
	}

	public Double getNaturalmoneytotal() {
		return naturalmoneytotal;
	}

	public void setNaturalmoneytotal(Double naturalmoneytotal) {
		this.naturalmoneytotal = naturalmoneytotal;
	}

	public String getNaturalmoneytotalcapital() {
		return naturalmoneytotalcapital;
	}

	public void setNaturalmoneytotalcapital(String naturalmoneytotalcapital) {
		this.naturalmoneytotalcapital = naturalmoneytotalcapital;
	}

	@Override
	public boolean businessEquals(Edocument obj) {
		if(obj == null) {
			return false;
		}
		return StringUtils.equals(this.getId(), obj.getId());
	}

	@Override
	public String label() {
		return this.getTitle();
	}
}
