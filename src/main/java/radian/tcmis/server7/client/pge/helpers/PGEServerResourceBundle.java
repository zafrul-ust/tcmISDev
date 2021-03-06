package radian.tcmis.server7.client.pge.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class PGEServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/pge"},
          {"DEBUG","false"},
          {"DB_CLIENT","PGE"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/pge/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/pge/schdelivery?"},
          {"CANCEL_MR","/tcmIS/pge/cancel?"},
          {"WASTE_ORDER","/tcmIS/pge/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/pge/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/pge/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/pge/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/pge/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/pge/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/pge/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.pge.servlets.PGEBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public PGEServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("PGE");
     }

}