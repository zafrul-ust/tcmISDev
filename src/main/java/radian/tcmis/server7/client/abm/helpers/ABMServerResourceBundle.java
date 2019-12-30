package radian.tcmis.server7.client.abm.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class ABMServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/abm"},
          {"DEBUG","false"},
          {"DB_CLIENT","ABM"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/abm/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/abm/schdelivery?"},
          {"CANCEL_MR","/tcmIS/abm/cancel?"},
          {"WASTE_ORDER","/tcmIS/abm/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/abm/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/abm/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/abm/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/abm/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/abm/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/abm/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.abm.servlets.ABMBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public ABMServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("ABM");
     }

}