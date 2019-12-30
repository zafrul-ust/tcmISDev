package radian.tcmis.server7.client.drs.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class DRSServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs.drs"},
          {"DEBUG","false"},
          {"DB_CLIENT","DRS"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/drs/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/drs/schdelivery?"},
          {"CANCEL_MR","/tcmIS/drs/cancel?"},
          {"WASTE_ORDER","/tcmIS/drs/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/drs/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/drs/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/drs/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/drs/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/drs/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/drs/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.drs.servlets.DRSBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public DRSServerResourceBundle(){
            super();
            addHash(cHash);
     }

     public String getDbClient(){
      return ("DRS");
     }
}