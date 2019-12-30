package radian.tcmis.server7.client.ablaero.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class ABLaeroServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/ablaero"},
          {"DEBUG","false"},
          {"DB_CLIENT","ABLaero"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/ablaero/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/ablaero/schdelivery?"},
          {"CANCEL_MR","/tcmIS/ablaero/cancel?"},
          {"WASTE_ORDER","/tcmIS/ablaero/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/ablaero/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/ablaero/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/ablaero/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/ablaero/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/ablaero/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/ablaero/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.ablaero.servlets.ABLaeroBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public ABLaeroServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("ABLaero");
     }

}