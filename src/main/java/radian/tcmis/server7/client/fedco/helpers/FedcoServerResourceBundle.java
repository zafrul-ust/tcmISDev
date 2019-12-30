package radian.tcmis.server7.client.fedco.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class FedcoServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/fedco"},
          {"DEBUG","false"},
          {"DB_CLIENT","Fedco"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/fedco/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/fedco/schdelivery?"},
          {"CANCEL_MR","/tcmIS/fedco/cancel?"},
          {"WASTE_ORDER","/tcmIS/fedco/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/fedco/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/fedco/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/fedco/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/fedco/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/fedco/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/fedco/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.fedco.servlets.FedcoBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public FedcoServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("Fedco");
     }

}