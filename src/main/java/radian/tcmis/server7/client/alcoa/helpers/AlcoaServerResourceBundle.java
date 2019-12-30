package radian.tcmis.server7.client.alcoa.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class AlcoaServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/alcoa"},
          {"DEBUG","false"},
          {"DB_CLIENT","Alcoa"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/alcoa/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/alcoa/schdelivery?"},
          {"CANCEL_MR","/tcmIS/alcoa/cancel?"},
          {"WASTE_ORDER","/tcmIS/alcoa/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/alcoa/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/alcoa/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/alcoa/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/alcoa/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/alcoa/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/alcoa/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.alcoa.servlets.AlcoaBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public AlcoaServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("Alcoa");
     }

}