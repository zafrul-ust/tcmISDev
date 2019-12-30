package radian.tcmis.server7.client.tomkins.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class TomkinsServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/tomkins"},
          {"DEBUG","false"},
          {"DB_CLIENT","Tomkins"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/tomkins/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/tomkins/schdelivery?"},
          {"CANCEL_MR","/tcmIS/tomkins/cancel?"},
          {"WASTE_ORDER","/tcmIS/tomkins/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/tomkins/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/tomkins/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/tomkins/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/tomkins/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/tomkins/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/tomkins/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.tomkins.servlets.TomkinsBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public TomkinsServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("Tomkins");
     }

}