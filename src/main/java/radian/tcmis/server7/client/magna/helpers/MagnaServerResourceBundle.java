package radian.tcmis.server7.client.magna.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class MagnaServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/magna"},
          {"DEBUG","false"},
          {"DB_CLIENT","MAGNA"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/magna/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/magna/schdelivery?"},
          {"CANCEL_MR","/tcmIS/magna/cancel?"},
          {"WASTE_ORDER","/tcmIS/magna/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/magna/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/magna/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/magna/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/magna/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/magna/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/magna/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.magna.servlets.MagnaBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public MagnaServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("MAGNA");
     }

}