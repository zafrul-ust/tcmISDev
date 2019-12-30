package radian.tcmis.server7.client.sd.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class SDServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/sd"},
          {"DEBUG","false"},
          {"DB_CLIENT","SD"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/sd/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/sd/schdelivery?"},
          {"CANCEL_MR","/tcmIS/sd/cancel?"},
          {"WASTE_ORDER","/tcmIS/sd/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/sd/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/sd/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/sd/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/sd/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/sd/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/sd/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","tcmIS/servlet/radian.tcmis.server7.client.sd.servlets.SDBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public SDServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("SD");
     }

}