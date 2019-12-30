package com.tcmis.client.report.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: BaseFieldViewBean <br>
 * @version: 1.0, Mar 9, 2009 <br>
 *****************************************************************************/


public class BaseFieldViewBean extends BaseDataBean {

	private String companyId;
	private String reportType;
	private BigDecimal baseFieldId;
	private BigDecimal rounding;
	private String name;
	private String description;
	private BigDecimal displayOrder;
	private BigDecimal displayLength;
	private String columnSpec;
	private String aggregate;
	private String compatibility;
	private String fromClause;
	private String whereClause;
	private String hyperlink;
    private String reqdAggregateField;
    private String dailySum;
    private String baseFieldCategory;
    private String chemicalId;
    private String chemicalIdRequired;


    //constructor
	public BaseFieldViewBean() {
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public void setBaseFieldId(BigDecimal baseFieldId) {
		this.baseFieldId = baseFieldId;
	}
	public void setRounding(BigDecimal rounding) {
		this.rounding = rounding;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setDisplayOrder(BigDecimal displayOrder) {
		this.displayOrder = displayOrder;
	}
	public void setDisplayLength(BigDecimal displayLength) {
		this.displayLength = displayLength;
	}
	public void setColumnSpec(String columnSpec) {
		this.columnSpec = columnSpec;
	}
	public void setAggregate(String aggregate) {
		this.aggregate = aggregate;
	}
	public void setCompatibility(String compatibility) {
		this.compatibility = compatibility;
	}
	public void setFromClause(String fromClause) {
		this.fromClause = fromClause;
	}
	public void setWhereClause(String whereClause) {
		this.whereClause = whereClause;
	}

	public void setHyperlink(String hyperlink) {
		this.hyperlink = hyperlink;
	}

    public void setReqdAggregateField(String reqdAggregateField) {
        this.reqdAggregateField = reqdAggregateField;
    }

    public void setDailySum(String dailySum) {
        this.dailySum = dailySum;
    }

    public void setBaseFieldCategory(String baseFieldCategory) {
        this.baseFieldCategory = baseFieldCategory;
    }

    //getters
	public String getCompanyId() {
		return companyId;
	}
	public String getReportType() {
		return reportType;
	}
	public BigDecimal getBaseFieldId() {
		return baseFieldId;
	}
	public BigDecimal getRounding() {
		return rounding;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public BigDecimal getDisplayOrder() {
		return displayOrder;
	}
	public BigDecimal getDisplayLength() {
		return displayLength;
	}
	public String getColumnSpec() {
		return columnSpec;
	}
	public String getAggregate() {
		return aggregate;
	}
	public String getCompatibility() {
		return compatibility;
	}
	public String getFromClause() {
		return fromClause;
	}
	public String getWhereClause() {
		return whereClause;
	}

	public String getHyperlink() {
		return hyperlink;
	}

    public String getReqdAggregateField() {
        return reqdAggregateField;
    }

    public String getDailySum() {
        return dailySum;
    }

    public String getBaseFieldCategory() {
        return baseFieldCategory;
    }

	public String getChemicalId() {
		return chemicalId;
	}

	public void setChemicalId(String chemicalId) {
		this.chemicalId = chemicalId;
	}

	public String getChemicalIdRequired() {
		return chemicalIdRequired;
	}

	public void setChemicalIdRequired(String chemicalIdRequired) {
		this.chemicalIdRequired = chemicalIdRequired;
	}
}