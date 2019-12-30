package radian.tcmis.server7.client.dana.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class DanaServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/dana"},
          {"DEBUG","false"},
          {"DB_CLIENT","Dana"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/dana/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/dana/schdelivery?"},
          {"CANCEL_MR","/tcmIS/dana/cancel?"},
          {"WASTE_ORDER","/tcmIS/dana/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/dana/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/dana/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/dana/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/dana/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/dana/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/dana/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.dana.servlets.DanaBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public DanaServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("Dana");
     }

}