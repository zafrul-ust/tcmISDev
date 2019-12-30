package radian.tcmis.server7.client.lmco.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class LMCOServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/lmco"},
          {"DEBUG","false"},
          {"DB_CLIENT","LMCO"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/lmco/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/lmco/schdelivery?"},
          {"CANCEL_MR","/tcmIS/lmco/cancel?"},
          {"WASTE_ORDER","/tcmIS/lmco/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/lmco/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/lmco/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/lmco/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/lmco/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/lmco/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/lmco/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.lmco.servlets.LMCOBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public LMCOServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("LMCO");
     }

}