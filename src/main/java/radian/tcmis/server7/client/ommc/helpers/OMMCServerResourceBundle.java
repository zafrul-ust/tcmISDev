package radian.tcmis.server7.client.ommc.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class OMMCServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/ommc"},
          {"DEBUG","false"},
          {"DB_CLIENT","OMMC"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/ommc/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/ommc/schdelivery?"},
          {"CANCEL_MR","/tcmIS/ommc/cancel?"},
          {"WASTE_ORDER","/tcmIS/ommc/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/ommc/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/ommc/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/ommc/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/ommc/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/ommc/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/ommc/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.ommc.servlets.OMMCBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public OMMCServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("OMMC");
     }

}