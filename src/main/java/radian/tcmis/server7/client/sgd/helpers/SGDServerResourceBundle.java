package radian.tcmis.server7.client.sgd.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class SGDServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/sgd"},
          {"DEBUG","false"},
          {"DB_CLIENT","SGD"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/sgd/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/sgd/schdelivery?"},
          {"CANCEL_MR","/tcmIS/sgd/cancel?"},
          {"WASTE_ORDER","/tcmIS/sgd/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/sgd/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/sgd/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/sgd/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/sgd/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/sgd/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/sgd/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.sgd.servlets.SGDBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public SGDServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("SGD");
     }

}