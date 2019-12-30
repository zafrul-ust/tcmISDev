package radian.tcmis.server7.client.tambour.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class TambourServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/tambour"},
          {"DEBUG","false"},
          {"DB_CLIENT","Tambour"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/tambour/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/tambour/schdelivery?"},
          {"CANCEL_MR","/tcmIS/tambour/cancel?"},
          {"WASTE_ORDER","/tcmIS/tambour/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/tambour/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/tambour/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/tambour/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/tambour/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/tambour/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/tambour/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.tambour.servlets.TambourBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public TambourServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("Tambour");
     }

}