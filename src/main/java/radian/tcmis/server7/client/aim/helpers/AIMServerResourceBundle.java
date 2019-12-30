package radian.tcmis.server7.client.aim.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class AIMServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/aim"},
          {"DEBUG","false"},
          {"DB_CLIENT","AIM_NORWAY"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/magna/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/aim/schdelivery?"},
          {"CANCEL_MR","/tcmIS/aim/cancel?"},
          {"WASTE_ORDER","/tcmIS/aim/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/aim/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/aim/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/aim/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/aim/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/aim/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/aim/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.aim.servlets.AIMBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public AIMServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("AIM_NORWAY");
     }

}