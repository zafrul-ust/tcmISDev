package radian.tcmis.both1.beans;


import java.math.BigDecimal;

import radian.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PartCategoryCriteriaBean <br>
 * @version: 1.0, Feb 25, 2004 <br>
 *****************************************************************************/


public class PartCategoryCriteriaBean extends BaseDataBean {

	private String category;
	private String parent;
	private String reference;
	private String display;
	private String criteria;
	private BigDecimal criteriaLimit;
	private int displayOrder;
        private String categorySystem;

	//constructor
	public PartCategoryCriteriaBean() {
	}


	//setters
	public void setCategory(String category) {
		this.category = category;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public void setDisplay(String display) {
		this.display = display;
	}
	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}
	public void setCriteriaLimit(BigDecimal criteriaLimit) {
		this.criteriaLimit = criteriaLimit;
	}
	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}
        public void setCategorySystem(String categorySystem) {
                this.categorySystem = categorySystem;
        }


	//getters
	public String getCategory() {
		return category;
	}
	public String getParent() {
		return parent;
	}
	public String getReference() {
		return reference;
	}
	public String getDisplay() {
		return display;
	}
	public String getCriteria() {
		return criteria;
	}
	public BigDecimal getCriteriaLimit() {
		return criteriaLimit;
	}
	public int getDisplayOrder() {
		return displayOrder;
	}
        public String getCategorySystem() {
                return categorySystem;
        }
}