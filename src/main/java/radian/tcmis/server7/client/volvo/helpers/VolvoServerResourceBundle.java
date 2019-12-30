package radian.tcmis.server7.client.volvo.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class VolvoServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/volvo"},
          {"DEBUG","false"},
          {"DB_CLIENT","Volvo"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/volvo/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/volvo/schdelivery?"},
          {"CANCEL_MR","/tcmIS/volvo/cancel?"},
          {"WASTE_ORDER","/tcmIS/volvo/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/volvo/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/volvo/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/volvo/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/volvo/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/volvo/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/volvo/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.volvo.servlets.VolvoBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public VolvoServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("Volvo");
     }

}