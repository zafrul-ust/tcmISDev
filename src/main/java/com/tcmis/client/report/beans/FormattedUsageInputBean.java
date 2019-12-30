package com.tcmis.client.report.beans;

import java.util.Date;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.ArrayList;
import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: FormattedUsageInputBean <br>
 * @version: 1.0, Aug 5, 2005 <br>
 *****************************************************************************/


public class FormattedUsageInputBean extends BaseDataBean {

	private String reportType;
        private String listOption;
        private String chemicalListName;
        private String casNumber;
        private String facilityId;
        private String facilityDesc;
        private String workAreaId;
        private String workAreaDesc;
        private String beginMonth;
        private String beginYear;
        private String endMonth;
        private String endYear;
        private String generateReport;
        private String[] groupByOptionList;
        private String[] groupByList;
        private String orderBy;
    private String submitValue;
    private Date beginDate;
    private Date endDate;
    //constructor
	public FormattedUsageInputBean() {
	}

	//setters
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
        public void setListOption(String listOption) {
                this.listOption = listOption;
        }
        public void setCasNumber(String casNumber) {
                this.casNumber = casNumber;
        }
    public void setChemicalListName(String chemicalListName) {
                this.chemicalListName = chemicalListName;
        }
        public void setFacilityId(String facilityId) {
                this.facilityId = facilityId;
        }
        public void setFacilityDesc(String facilityDesc) {
                this.facilityDesc = facilityDesc;
        }
        public void setWorkAreaId(String workAreaId) {
                this.workAreaId = workAreaId;
        }
        public void setWorkAreaDesc(String workAreaDesc) {
                this.workAreaDesc = workAreaDesc;
        }
        public void setBeginMonth(String beginMonth) {
                this.beginMonth = beginMonth;
        }
        public void setBeginYear(String beginYear) {
                this.beginYear = beginYear;
        }
        public void setEndMonth(String endMonth) {
                this.endMonth = endMonth;
        }
        public void setEndYear(String endYear) {
                this.endYear = endYear;
        }
        public void setGenerateReport(String generateReport) {
                this.generateReport = generateReport;
        }
        public void setGroupByOptionList(String[] groupByOptionList) {
                this.groupByOptionList = groupByOptionList;
        }
        public void setGroupByList(String[] groupByList) {
                this.groupByList = groupByList;
        }
        public void setOrderBy(String orderBy) {
                this.orderBy = orderBy;
        }

        public void setSubmitValue(String s) {
                this.submitValue = s;
        }
           public void setBeginDate(Date d) {
                this.beginDate = d;
        }
              public void setEndDate(Date d) {
                this.endDate = d;
        }
    //getters
	public String getReportType() {
		return reportType;
	}
        public String getListOption() {
                return listOption;
        }
        public String getCasNumber() {
                return casNumber;
        }
    public String getChemicalListName() {
                return chemicalListName;
        }
        public String getFacilityId() {
                return facilityId;
        }
        public String getFacilityDesc() {
                return facilityDesc;
        }
        public String getWorkAreaId() {
                return workAreaId;
        }
        public String getWorkAreaDesc() {
                return workAreaDesc;
        }
        public String getBeginMonth() {
                return beginMonth;
        }
        public String getBeginYear() {
                return beginYear;
        }
        public String getEndMonth() {
                return endMonth;
        }
        public String getEndYear() {
                return endYear;
        }
        public String getGenerateReport() {
                return generateReport;
        }
        public String[] getGroupByOptionList() {
                return groupByOptionList;
        }
        public String[] getGroupByList() {
                return groupByList;
        }
        public String getOrderBy() {
                return orderBy;
        }
       public String getSubmitValue() {
                return submitValue;
        }
       public Date getBeginDate() {
                return beginDate;
        }
       public Date getEndDate() {
                return endDate;
        }
} //end of class

