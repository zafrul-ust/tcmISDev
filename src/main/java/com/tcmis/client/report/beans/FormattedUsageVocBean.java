package com.tcmis.client.report.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: ListBean <br>
 * @version: 1.0, Oct 6, 2005 <br>
 *****************************************************************************/

/*
        Hashtable h = new Hashtable();
        h.put("CAS_NUMBER",BothHelpObjs.makeBlankFromNull(rs.getString("cas_number")));
        h.put("RPT_CHEMICAL",BothHelpObjs.makeBlankFromNull(rs.getString("display_name")));
        h.put("FACILITY",BothHelpObjs.makeBlankFromNull(rs.getString("facility")));
        h.put("WORK_AREA",BothHelpObjs.makeBlankFromNull(rs.getString("application_display")));
        h.put("DELIVERY_POINT",BothHelpObjs.makeBlankFromNull(rs.getString("delivery_point_display")));
        h.put("FAC_PART_ID",BothHelpObjs.makeBlankFromNull(rs.getString("fac_part_id")));
        h.put("TRADE_NAME",BothHelpObjs.makeBlankFromNull(rs.getString("trade_name")));
        h.put("QTY_SHIPPED",BothHelpObjs.makeBlankFromNull(rs.getString("qty_shipped")));
        h.put("WT_PER_UNIT",BothHelpObjs.makeBlankFromNull(rs.getString("wt_per_unit")));
        h.put("WT_SHIPPED",BothHelpObjs.makeBlankFromNull(rs.getString("wt_shipped")));
        h.put("PERCENT",BothHelpObjs.makeBlankFromNull(rs.getString("percent")));
        h.put("LBS_REPORTABLE",BothHelpObjs.makeBlankFromNull(rs.getString("lbs_reportable")));
        if(groupByMonth){
          h.put("YEAR_MONTH",BothHelpObjs.makeBlankFromNull(rs.getString("year_month")));
        }else{
          h.put("YEAR_MONTH","year_month");
        }

        h.put("MIXTURE_VOC",BothHelpObjs.makeBlankFromNull(rs.getString("mixture_voc")));
        }

*/

public class FormattedUsageVocBean extends BaseDataBean {

	private String casNumber;
	private String rptChemical;
        private String facility;
        private String workArea;
        private String deliveryPoint;
        private String facPartNo;
        private String tradeName;
        private BigDecimal qtyShipped;
        private BigDecimal wtPerUnit;
        private BigDecimal wtShipped;
        private BigDecimal percent;
        private BigDecimal lbsReportable;
        private Date dateShipped;
        private BigDecimal wtVoc;

	//constructor
	public FormattedUsageVocBean() {
	}

	//setters
        public void setCasNumber(String s) {
          this.casNumber = s;
        }
        public void setRptChemical(String s) {
          this.rptChemical = s;
        }
        public void setFacility(String s) {
          this.facility = s;
        }
        public void setWorkArea(String s) {
          this.workArea = s;
        }
        public void setDeliveryPoint(String s) {
          this.deliveryPoint = s;
        }
        public void setFacPartNo(String s) {
          this.facPartNo = s;
        }
        public void setTradeName(String s) {
          this.tradeName = s;
        }
        public void setQtyShipped(BigDecimal bd) {
          this.qtyShipped = bd;
        }
        public void setWtPerUnit(BigDecimal bd) {
          this.wtPerUnit = bd;
        }
        public void setWtShipped(BigDecimal bd) {
          this.wtShipped = bd;
        }
        public void setPercent(BigDecimal bd) {
          this.percent = bd;
        }
        public void setLbsReportable(BigDecimal bd) {
          this.lbsReportable = bd;
        }
        public void setDateShipped(Date d) {
          this.dateShipped = d;
        }
        public void setWtVoc(BigDecimal bd) {
          this.wtVoc = bd;
        }

	//getters
        public String getCasNumber() {
          return this.casNumber;
        }
        public String getRptChemical() {
          return this.rptChemical;
        }
        public String getFacility() {
          return this.facility;
        }
        public String getWorkArea() {
          return this.workArea;
        }
        public String getDeliveryPoint() {
          return this.deliveryPoint;
        }
        public String getFacPartNo() {
          return this.facPartNo;
        }
        public String getTradeName() {
          return this.tradeName;
        }
        public BigDecimal getQtyShipped() {
          return this.qtyShipped;
        }
        public BigDecimal getWtPerUnit() {
          return this.wtPerUnit;
        }
        public BigDecimal getWtShipped() {
          return this.wtShipped;
        }
        public BigDecimal getPercent() {
          return this.percent;
        }
        public BigDecimal getLbsReportable() {
          return this.lbsReportable;
        }
        public Date getDateShipped() {
          return this.dateShipped;
        }
        public BigDecimal getWtVoc() {
          return this.wtVoc;
        }

}