package radian.tcmis.server7.client.terma.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class TermaServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/terma"},
          {"DEBUG","false"},
          {"DB_CLIENT","TERMA"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/terma/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/terma/schdelivery?"},
          {"CANCEL_MR","/tcmIS/terma/cancel?"},
          {"WASTE_ORDER","/tcmIS/terma/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/terma/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/terma/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/terma/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/terma/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/terma/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/terma/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.termaservlets.TermaBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public TermaServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("terma");
     }

}