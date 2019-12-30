package radian.tcmis.server7.client.gd.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class GDServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/gd"},
          {"DEBUG","false"},
          {"DB_CLIENT","GD"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/gd/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/gd/schdelivery?"},
          {"CANCEL_MR","/tcmIS/gd/cancel?"},
          {"WASTE_ORDER","/tcmIS/gd/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/gd/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/gd/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/gd/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/gd/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/gd/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/gd/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.gd.servlets.GDBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public GDServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("GD");
     }

}