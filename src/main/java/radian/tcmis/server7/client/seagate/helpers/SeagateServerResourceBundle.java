package radian.tcmis.server7.client.seagate.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class SeagateServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/seagate"},
          {"DEBUG","false"},
          {"DB_CLIENT","Seagate"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/seagate/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/seagate/schdelivery?"},
          {"CANCEL_MR","/tcmIS/seagate/cancel?"},
          {"WASTE_ORDER","/tcmIS/seagate/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/seagate/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/seagate/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/seagate/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/seagate/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/seagate/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/seagate/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.seagate.servlets.SeagateBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public SeagateServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("Seagate");
     }

}