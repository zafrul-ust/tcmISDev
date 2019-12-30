package radian.tcmis.server7.client.merck.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class MerckServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/merck"},
          {"DEBUG","false"},
          {"DB_CLIENT","MERCK"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/merck/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/merck/schdelivery?"},
          {"CANCEL_MR","/tcmIS/merck/cancel?"},
          {"WASTE_ORDER","/tcmIS/merck/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/merck/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/merck/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/merck/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/merck/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/merck/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/merck/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.merck.servlets.MerckBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public MerckServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("MERCK");
     }

}