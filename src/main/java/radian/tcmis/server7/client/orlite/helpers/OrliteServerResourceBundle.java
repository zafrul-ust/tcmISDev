package radian.tcmis.server7.client.orlite.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class OrliteServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/orlite"},
          {"DEBUG","false"},
          {"DB_CLIENT","Orlite"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/orlite/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/orlite/schdelivery?"},
          {"CANCEL_MR","/tcmIS/orlite/cancel?"},
          {"WASTE_ORDER","/tcmIS/orlite/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/orlite/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/orlite/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/orlite/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/orlite/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/orlite/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/orlite/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.orlite.servlets.OrliteBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public OrliteServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("Orlite");
     }

}