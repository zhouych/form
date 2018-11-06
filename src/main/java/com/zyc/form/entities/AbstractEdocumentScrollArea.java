package com.zyc.form.entities;

import java.sql.JDBCType;

import com.zyc.baselibs.annotation.DatabaseColumn;
import com.zyc.baselibs.annotation.FieldRule;
import com.zyc.baselibs.annotation.Mainfield;
import com.zyc.baselibs.annotation.Subfield;
import com.zyc.baselibs.commons.StringUtils;
import com.zyc.baselibs.entities.Businessable;
import com.zyc.baselibs.entities.DescriptionBaseEntity;
import com.zyc.baselibs.entities.Labelable;

public abstract class AbstractEdocumentScrollArea<T extends AbstractEdocumentScrollArea<?>> extends DescriptionBaseEntity implements java.io.Serializable, Labelable, Businessable<T> {

	private static final long serialVersionUID = 258499219665746057L;

	@FieldRule(required = true, externalUneditable = true)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	private String edocumentid;

	@FieldRule(required = true, externalUneditable = true)
	@DatabaseColumn(jdbcType = JDBCType.INTEGER)
	private Integer index;

	@FieldRule(required = true, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	private String title;

	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	@Mainfield
	private String item01;
	
	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	@Subfield
	private String item01label;

	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	@Mainfield
	private String item02;
	
	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	@Subfield
	private String item02label;

	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	@Mainfield
	private String item03;
	
	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	@Subfield
	private String item03label;

	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	@Mainfield
	private String item04;
	
	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	@Subfield
	private String item04label;

	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	@Mainfield
	private String item05;
	
	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	@Subfield
	private String item05label;

	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	@Mainfield
	private String item06;
	
	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	@Subfield
	private String item06label;

	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	@Mainfield
	private String item07;
	
	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	@Subfield
	private String item07label;

	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	@Mainfield
	private String item08;
	
	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	@Subfield
	private String item08label;

	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	@Mainfield
	private String item09;
	
	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	@Subfield
	private String item09label;

	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	@Mainfield
	private String item10;
	
	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	@Subfield
	private String item10label;

	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	@Mainfield
	private String item11;
	
	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	@Subfield
	private String item11label;

	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	@Mainfield
	private String item12;
	
	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	@Subfield
	private String item12label;

	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	@Mainfield
	private String item13;
	
	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	@Subfield
	private String item13label;

	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	@Mainfield
	private String item14;
	
	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	@Subfield
	private String item14label;

	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	@Mainfield
	private String item15;
	
	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	@Subfield
	private String item15label;

	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	@Mainfield
	private String item16;
	
	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	@Subfield
	private String item16label;

	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	@Mainfield
	private String item17;
	
	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	@Subfield
	private String item17label;

	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	@Mainfield
	private String item18;
	
	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	@Subfield
	private String item18label;

	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	@Mainfield
	private String item19;
	
	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	@Subfield
	private String item19label;

	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 36)
	@Mainfield
	private String item20;
	
	@FieldRule(required = false, externalUneditable = false)
	@DatabaseColumn(jdbcType = JDBCType.VARCHAR, jdbcTypeVarcharLength = 128)
	@Subfield
	private String item20label;
	
	public String getEdocumentid() {
		return edocumentid;
	}

	public void setEdocumentid(String edocumentid) {
		this.edocumentid = edocumentid;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getItem01() {
		return item01;
	}

	public void setItem01(String item01) {
		this.item01 = item01;
	}

	public String getItem01label() {
		return item01label;
	}

	public void setItem01label(String item01label) {
		this.item01label = item01label;
	}

	public String getItem02() {
		return item02;
	}

	public void setItem02(String item02) {
		this.item02 = item02;
	}

	public String getItem02label() {
		return item02label;
	}

	public void setItem02label(String item02label) {
		this.item02label = item02label;
	}

	public String getItem03() {
		return item03;
	}

	public void setItem03(String item03) {
		this.item03 = item03;
	}

	public String getItem03label() {
		return item03label;
	}

	public void setItem03label(String item03label) {
		this.item03label = item03label;
	}

	public String getItem04() {
		return item04;
	}

	public void setItem04(String item04) {
		this.item04 = item04;
	}

	public String getItem04label() {
		return item04label;
	}

	public void setItem04label(String item04label) {
		this.item04label = item04label;
	}

	public String getItem05() {
		return item05;
	}

	public void setItem05(String item05) {
		this.item05 = item05;
	}

	public String getItem05label() {
		return item05label;
	}

	public void setItem05label(String item05label) {
		this.item05label = item05label;
	}

	public String getItem06() {
		return item06;
	}

	public void setItem06(String item06) {
		this.item06 = item06;
	}

	public String getItem06label() {
		return item06label;
	}

	public void setItem06label(String item06label) {
		this.item06label = item06label;
	}

	public String getItem07() {
		return item07;
	}

	public void setItem07(String item07) {
		this.item07 = item07;
	}

	public String getItem07label() {
		return item07label;
	}

	public void setItem07label(String item07label) {
		this.item07label = item07label;
	}

	public String getItem08() {
		return item08;
	}

	public void setItem08(String item08) {
		this.item08 = item08;
	}

	public String getItem08label() {
		return item08label;
	}

	public void setItem08label(String item08label) {
		this.item08label = item08label;
	}

	public String getItem09() {
		return item09;
	}

	public void setItem09(String item09) {
		this.item09 = item09;
	}

	public String getItem09label() {
		return item09label;
	}

	public void setItem09label(String item09label) {
		this.item09label = item09label;
	}

	public String getItem10() {
		return item10;
	}

	public void setItem10(String item10) {
		this.item10 = item10;
	}

	public String getItem10label() {
		return item10label;
	}

	public void setItem10label(String item10label) {
		this.item10label = item10label;
	}

	public String getItem11() {
		return item11;
	}

	public void setItem11(String item11) {
		this.item11 = item11;
	}

	public String getItem11label() {
		return item11label;
	}

	public void setItem11label(String item11label) {
		this.item11label = item11label;
	}

	public String getItem12() {
		return item12;
	}

	public void setItem12(String item12) {
		this.item12 = item12;
	}

	public String getItem12label() {
		return item12label;
	}

	public void setItem12label(String item12label) {
		this.item12label = item12label;
	}

	public String getItem13() {
		return item13;
	}

	public void setItem13(String item13) {
		this.item13 = item13;
	}

	public String getItem13label() {
		return item13label;
	}

	public void setItem13label(String item13label) {
		this.item13label = item13label;
	}

	public String getItem14() {
		return item14;
	}

	public void setItem14(String item14) {
		this.item14 = item14;
	}

	public String getItem14label() {
		return item14label;
	}

	public void setItem14label(String item14label) {
		this.item14label = item14label;
	}

	public String getItem15() {
		return item15;
	}

	public void setItem15(String item15) {
		this.item15 = item15;
	}

	public String getItem15label() {
		return item15label;
	}

	public void setItem15label(String item15label) {
		this.item15label = item15label;
	}

	public String getItem16() {
		return item16;
	}

	public void setItem16(String item16) {
		this.item16 = item16;
	}

	public String getItem16label() {
		return item16label;
	}

	public void setItem16label(String item16label) {
		this.item16label = item16label;
	}

	public String getItem17() {
		return item17;
	}

	public void setItem17(String item17) {
		this.item17 = item17;
	}

	public String getItem17label() {
		return item17label;
	}

	public void setItem17label(String item17label) {
		this.item17label = item17label;
	}

	public String getItem18() {
		return item18;
	}

	public void setItem18(String item18) {
		this.item18 = item18;
	}

	public String getItem18label() {
		return item18label;
	}

	public void setItem18label(String item18label) {
		this.item18label = item18label;
	}

	public String getItem19() {
		return item19;
	}

	public void setItem19(String item19) {
		this.item19 = item19;
	}

	public String getItem19label() {
		return item19label;
	}

	public void setItem19label(String item19label) {
		this.item19label = item19label;
	}

	public String getItem20() {
		return item20;
	}

	public void setItem20(String item20) {
		this.item20 = item20;
	}

	public String getItem20label() {
		return item20label;
	}

	public void setItem20label(String item20label) {
		this.item20label = item20label;
	}

	@Override
	public boolean businessEquals(T obj) {
		if(obj == null) {
			return false;
		}
		return StringUtils.equals(this.getId(), obj.getId()) 
				&& StringUtils.equals(this.getEdocumentid(), this.getEdocumentid());
	}

	@Override
	public String label() {
		return this.getTitle();
	}
}
