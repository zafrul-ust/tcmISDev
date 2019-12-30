package radian.tcmis.server7.client.jtekt.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class JtektServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/jtekt"},
          {"DEBUG","false"},
          {"DB_CLIENT","JTEKT"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/jtekt/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/jtekt/schdelivery?"},
          {"CANCEL_MR","/tcmIS/jtekt/cancel?"},
          {"WASTE_ORDER","/tcmIS/jtekt/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/jtekt/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/jtekt/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/jtekt/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/jtekt/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/jtekt/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/jtekt/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.jtekt.servlets.JtektBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public JtektServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("JTEKT");
     }

}