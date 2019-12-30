package radian.tcmis.internal.admin.beans;


import radian.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: SicBean <br>
 * @version: 1.0, May 4, 2004 <br>
 *****************************************************************************/


public class SicBean extends BaseDataBean {

	private String sicCode;
	private String industryTitle;


	//constructor
	public SicBean() {
          super();
	}


	//setters
	public void setSicCode(String sicCode) {
		this.sicCode = sicCode;
	}
	public void setIndustryTitle(String industryTitle) {
		this.industryTitle = industryTitle;
	}


	//getters
	public String getSicCode() {
		return sicCode;
	}
	public String getIndustryTitle() {
		return industryTitle;
	}
}