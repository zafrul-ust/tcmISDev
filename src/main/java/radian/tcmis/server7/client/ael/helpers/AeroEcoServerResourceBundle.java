package radian.tcmis.server7.client.ael.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class AeroEcoServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/ael"},
          {"DEBUG","false"},
          {"DB_CLIENT","AeroEco"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/ael/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/ael/schdelivery?"},
          {"CANCEL_MR","/tcmIS/ael/cancel?"},
          {"WASTE_ORDER","/tcmIS/ael/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/ael/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/ael/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/ael/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/ael/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/ael/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/ael/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.ael.servlets.AeroEcoBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public AeroEcoServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("AeroEco");
     }

}