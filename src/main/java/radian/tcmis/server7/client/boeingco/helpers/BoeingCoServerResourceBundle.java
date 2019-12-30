package radian.tcmis.server7.client.boeingco.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class BoeingCoServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/boeingco"},
          {"DEBUG","false"},
          {"DB_CLIENT","BOEING_CO"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/boeingco/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/boeingco/schdelivery?"},
          {"CANCEL_MR","/tcmIS/boeingco/cancel?"},
          {"WASTE_ORDER","/tcmIS/boeingco/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/boeingco/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/boeingco/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/boeingco/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/boeingco/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/boeingco/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/boeingco/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.boeingcoservlets.BoeingCoBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public BoeingCoServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("boeingco");
     }

}