package com.tcmis.client.report.process;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.*;
import com.tcmis.common.admin.factory.SearchDropDownBeanFactory;
import com.tcmis.common.admin.beans.KeyValuePairBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.client.report.factory.ListBeanFactory;
import com.tcmis.client.report.beans.TcmisConstantBean;
import com.tcmis.client.report.factory.TcmisConstantBeanFactory;
import com.tcmis.client.report.beans.FormattedUsageInputBean;
import com.tcmis.client.report.beans.UsageReportTemplateBean;

/******************************************************************************
 * Process for receiving
 * @version 1.0
 *****************************************************************************/
public class FormattedVocUsageProcess extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public FormattedVocUsageProcess(String client) {
    super(client);
  }



  public void createReport(FormattedUsageInputBean inputBean,
                           PersonnelBean personnelBean, Writer writer,
                                Locale locale) throws
      BaseException,
      Exception {
    ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
    Vector out = new Vector();
/*    String fac = inHash.get("FACILITY").toString();
    String workArea = inHash.get("WORK_AREA").toString();
    String begMonth = inHash.get("BEGMONTH").toString();
    String begYear = inHash.get("BEGYEAR").toString();
    String endMonth = inHash.get("ENDMONTH").toString();
    String endYear = inHash.get("ENDYEAR").toString();
    String sortBy = inHash.get("SORT_BY").toString();
    String userID = inHash.get("USER_ID").toString();
    Vector groupBy = (Vector)inHash.get("GROUP_BY");
    // group by Month
    boolean groupByMonth = false;
    for(int i=0;i<groupBy.size();i++){
      if(groupBy.elementAt(i).toString().equalsIgnoreCase("month")){
        groupByMonth = true;
        break;
      }
    }
*/
    String where = "where ";
    String from = "";
    String orderBy = "";

    // where
    // facility
    if(!StringHandler.isBlankString(inputBean.getFacilityId()) && !"All".equalsIgnoreCase(inputBean.getFacilityId())) {
      where = where + "facility = '" + inputBean.getFacilityId() + "' and ";
    }
    else if ("All".equalsIgnoreCase(inputBean.getFacilityId())) {
      where += "facility in (select facility_id from user_group_member where user_group_id = 'CreateReport' and personnel_id = "+personnelBean.getPersonnelId()+") and ";
    }
    // work area
    if(!StringHandler.isBlankString(inputBean.getWorkAreaId()) && !"all".equalsIgnoreCase(inputBean.getWorkAreaId())) {
      if ("My Work Areas".equalsIgnoreCase(inputBean.getWorkAreaId())) {
        if(!StringHandler.isBlankString(inputBean.getFacilityId()) && !"all".equalsIgnoreCase(inputBean.getFacilityId())) {
          where += "location in (select application from my_workarea_facility_view ugma where ugma.personnel_id = "+personnelBean.getPersonnelId()+" and ugma.facility_id = '"+inputBean.getFacilityId()+"') and ";
        }else {
          where += "location in (select application from user_group_member ugm, my_workarea_facility_view ugma where ugm.facility_id = ugma.facility_id" +
                   " and ugm.personnel_id = ugma.personnel_id and ugm.user_group_id = 'CreateReport' and ugma.personnel_id = "+personnelBean.getPersonnelId()+") and ";
        }
      }else {
        where = where + "location = '" + inputBean.getWorkAreaId() + "' and ";
      }
    }
/*
    // begin date
    Integer begD = new Integer(199701);
    try{
      Integer bm = new Integer(begMonth);
      bm = new Integer(bm.intValue()+1);
      String sm = new String(bm.toString());
      if(sm.length() < 2) sm = "0"+sm;
      Integer by = new Integer(begYear);
      begD = new Integer(by.toString() + sm);
    }catch(Exception e){e.printStackTrace();}

    // end date
    Integer endD = new Integer(209012);
    try{
      Integer em = new Integer(endMonth);
      //add 2 to end month so I can use < (less than)
      em = new Integer(em.intValue()+2);
      Integer ey = new Integer(endYear);
      if(em.intValue() > 12){
        em = new Integer(1);
        ey = new Integer(ey.intValue() + 1);
      }
      String esm = new String(em.toString());
      if(esm.length() < 2) esm = "0"+esm;
      endD = new Integer(ey.toString() + esm);
    }catch(Exception e){e.printStackTrace();}
*/
    where = where + "date_shipped >= to_date('"+ DateHandler.formatDate(inputBean.getBeginDate(),"yyyyMMdd")+ "','YYYYMMDD') and date_shipped < to_date('"+DateHandler.formatDate(inputBean.getEndDate(),"yyyyMMdd")+"','YYYYMMDD') ";

    // order by
    orderBy = "order by ";
/*
    for(int i=0;i<groupBy.size();i++){
      String s = groupBy.elementAt(i).toString();
      if(s.equalsIgnoreCase("FACILITY")){
        s = "facility";
      }else if(s.equalsIgnoreCase("WORK_AREA")){
        s = "location";
      }else if(s.equalsIgnoreCase("DEL_POINT")){
        s = "delivery_point";
      }else if(s.equalsIgnoreCase("MONTH")){
        s = "year_month";
      }else if(s.equalsIgnoreCase("CAS_NUM")){
        s = "";   //not using this anymore
      }
      if (s.length() > 0) {
        orderBy = orderBy + s + ", ";
      }
    }
*/
    if("DELIVERY_POINT".equalsIgnoreCase(inputBean.getOrderBy())){
      orderBy = orderBy + "delivery_point ";
    }else if("WORK_AREA".equalsIgnoreCase(inputBean.getOrderBy())){
      orderBy = orderBy + "location ";
    }else if("PART_NUM".equalsIgnoreCase(inputBean.getOrderBy())){
      orderBy = orderBy + "fac_part_id ";
    }

    // group by
    String gb = " group by fac_part_id,facility,location,trade_name,voc_percent,wt_per_unit,wt_voc,application_display,delivery_point_display ";

    String select = "select fac_part_id,facility,location,sum(qty_shipped) qty_shipped,"+
                    "trade_name,voc_percent,wt_per_unit,sum(wt_shipped) wt_shipped,"+
                    "sum(wt_voc*qty_shipped) wt_voc,wt_voc mixture_voc,application_display,delivery_point_display ";
/*
    if(groupByMonth){
      select = select + ", to_char(date_shipped,'yyyymm') year_month ";
      gb = gb + ", to_char(date_shipped,'yyyymm') ";
    }
*/
    //from = "from customer.report_material_view ";
    from = "from report_material_view ";
    String query = select+from+where+gb+orderBy;
    if(log.isDebugEnabled()) {
        log.debug("QUERY:" + query);
    }
      DbManager dbManager = new DbManager(getClient());
    DataSet reportDataSet = dbManager.select(query);
    if (log.isDebugEnabled()) {
      log.debug("dataset:" + reportDataSet);
    }
    String columns[] = reportDataSet.getColumnNameArray();
    //if the user selected "batch" email the report to them, otherwise pipe
    //it to the stream

      writer.write("<html>");

      this.writeReportHeader(inputBean, writer, locale);
      writer.write("<table border=\"1\">");
      writer.write("<tr>");
      writer.write("<th>"+library.getString("label.facility")+"</th>");
      writer.write("<th>"+library.getString("label.workarea")+"</th>");
      writer.write("<th>"+library.getString("label.partnumber")+"</th>");
      writer.write("<th>"+library.getString("label.tradename")+"</th>");
      writer.write("<th>"+library.getString("label.unitsused")+"</th>");
      writer.write("<th>"+library.getString("label.lbsperunit")+"</th>");
      writer.write("<th>"+library.getString("label.lbsused")+"</th>");
      writer.write("<th>"+library.getString("label.percentvocs")+"</th>");
      writer.write("<th>"+library.getString("label.mixturevoc")+"</th>");
      writer.write("<th>"+library.getString("label.lbsreportable")+"</th>");
      writer.write("</tr>");
      //print rows
      log.debug("row count:" + reportDataSet.getRowCount());
      for (int j = 1; j <= reportDataSet.getRowCount(); j++) {
        log.debug("count:" + j);
        writer.write("<tr>");
        DataSetRow row = reportDataSet.getDataSetRow(j);
          log.debug("fac:" +row.getString("FACILITY"));
        writer.write("<td>" + StringHandler.emptyIfNull(row.getString("FACILITY")) + "</td>");
        writer.write("<td>" + StringHandler.emptyIfNull(row.getString("APPLICATION_DISPLAY")) + "</td>");
        writer.write("<td>" + StringHandler.emptyIfNull(row.getString("FAC_PART_ID")) + "</td>");
        writer.write("<td>" + StringHandler.emptyIfNull(row.getString("TRADE_NAME")) + "</td>");
        writer.write("<td>" + StringHandler.emptyIfNull(row.getString("QTY_SHIPPED")) + "</td>");
        writer.write("<td>" + StringHandler.emptyIfNull(row.getString("WT_PER_UNIT")) + "</td>");
        writer.write("<td>" + StringHandler.emptyIfNull(row.getString("WT_SHIPPED")) + "</td>");
        writer.write("<td>" + StringHandler.emptyIfNull(row.getString("VOC_PERCENT")) + "</td>");
          writer.write("<td>" + StringHandler.emptyIfNull(row.getString("WT_VOC")) + "</td>");
        writer.write("<td>" + StringHandler.emptyIfNull(row.getString("LBS_REPORTABLE")) + "</td>");
/*
        for (int k = 0; k < columns.length; k++) {

          writer.write("<td>" +
                       StringHandler.emptyIfNull(row.getString(columns[k])) +
                       "</td>");
        }
*/
        writer.write("</tr>");
      }
      writer.write("</table>");
      writer.write("</html>");
        writer.flush();
        writer.close();

/*
    DBResultSet dbrs = null;
    ResultSet rs = null;
    try {
      dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
      while(rs.next()){
        Hashtable h = new Hashtable();
        h.put("CAS_NUMBER","cas_number");
        h.put("RPT_CHEMICAL","display_name");
        h.put("FACILITY",BothHelpObjs.makeBlankFromNull(rs.getString("facility")));
        h.put("WORK_AREA",BothHelpObjs.makeBlankFromNull(rs.getString("application_display")));
        h.put("DELIVERY_POINT",BothHelpObjs.makeBlankFromNull(rs.getString("delivery_point_display")));
        h.put("FAC_PART_ID",BothHelpObjs.makeBlankFromNull(rs.getString("fac_part_id")));
        h.put("TRADE_NAME",BothHelpObjs.makeBlankFromNull(rs.getString("trade_name")));
        h.put("QTY_SHIPPED",BothHelpObjs.makeBlankFromNull(rs.getString("qty_shipped")));
        h.put("WT_PER_UNIT",BothHelpObjs.makeBlankFromNull(rs.getString("wt_per_unit")));
        h.put("WT_SHIPPED",BothHelpObjs.makeBlankFromNull(rs.getString("wt_shipped")));
        h.put("PERCENT",BothHelpObjs.makeBlankFromNull(rs.getString("voc_percent")));
        h.put("MIXTURE_VOC",BothHelpObjs.makeBlankFromNull(rs.getString("mixture_voc")));
        h.put("LBS_REPORTABLE",BothHelpObjs.makeBlankFromNull(rs.getString("wt_voc")));
        if(groupByMonth){
          h.put("YEAR_MONTH",BothHelpObjs.makeBlankFromNull(rs.getString("year_month")));
        }else{
          h.put("YEAR_MONTH","year_month");
        }
        out.addElement(h);
      }
    }catch (Exception e) { e.printStackTrace();throw e;
    }finally{
      dbrs.close();
    }
*/

  }

      private void writeReportHeader(FormattedUsageInputBean bean,Writer writer, Locale locale) throws
      BaseException,
      Exception {
      ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
      writer.write("<table>");
      writer.write("<tr>");
      writer.write("<td colspan=\"2\">");
      writer.write(library.getString("formattedvocusagereport.title"));
      writer.write("</td>");
      writer.write("</tr>");
      writer.write("<tr>");
      writer.write("<td colspan=\"2\">");
      writer.write(library.getString("label.date")+" " + DateHandler.formatDate(new Date(), "MM/dd/yyyy"));
      writer.write("</td>");
      writer.write("</tr>");
      writer.write("<tr>");
      writer.write("<td colspan=\"2\">");
      writer.write(library.getString("label.time")+" " + DateHandler.formatDate(new Date(), "h:mm a z"));
      writer.write("</td>");
      writer.write("</tr>");
      writer.write("<tr>");
      writer.write("<td colspan=\"2\">");
      writer.write("&nbsp;");
      writer.write("</td>");
      writer.write("</tr>");
      writer.write("<tr>");
      writer.write("<td colspan=\"2\">");
      writer.write(library.getString("label.selectioncriteria"));
      writer.write("</td>");
      writer.write("</tr>");
      writer.write("<tr>");
      writer.write("<td>");
      writer.write(library.getString("label.facility"));
      writer.write("</td>");
      writer.write("<td>");
      writer.write(StringHandler.emptyIfNull(bean.getFacilityId()));
      writer.write("</td>");
      writer.write("</tr>");
      writer.write("<tr>");
      writer.write("<td>");
      writer.write(library.getString("label.workarea"));
      writer.write("</td>");
      writer.write("<td>");
      writer.write(StringHandler.emptyIfNull(bean.getWorkAreaId()));
      writer.write("</td>");
      writer.write("</tr>");
      writer.write("<tr>");
      writer.write("<td>");
      writer.write(library.getString("label.timeperiod"));
      writer.write("</td>");
      writer.write("<td>");
      writer.write(DateHandler.formatDate(bean.getBeginDate(), "MM/dd/yyyy") + " - " + DateHandler.formatDate(bean.getEndDate(), "MM/dd/yyyy"));
      writer.write("</td>");
      writer.write("</tr>");
      writer.write("<tr>");
      writer.write("<td>");
      writer.write("&nbsp;");
      writer.write("</td>");
      writer.write("<td>");
      writer.write("&nbsp;");
      writer.write("</td>");
      writer.write("</tr>");
      writer.write("<tr>");
      writer.write("<td>");
      writer.write("&nbsp;");
      writer.write("</td>");
      writer.write("<td>");
      writer.write("&nbsp;");
      writer.write("</td>");
      writer.write("</tr>");
      writer.write("</table>");
  }

}