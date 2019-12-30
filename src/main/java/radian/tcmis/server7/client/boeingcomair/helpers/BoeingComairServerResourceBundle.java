package radian.tcmis.server7.client.boeingcomair.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class BoeingComairServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/boeingcomair"},
          {"DEBUG","false"},
          {"DB_CLIENT","BOEING_COMAIR"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/boeing/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/boeing/schdelivery?"},
          {"CANCEL_MR","/tcmIS/boeing/cancel?"},
          {"WASTE_ORDER","/tcmIS/boeing/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/boeing/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/boeing/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/boeing/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/boeing/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/boeing/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/boeing/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.boeingcomair.servlets.BoeingComairBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public BoeingComairServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("BoeingComair");
     }

}