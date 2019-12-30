package radian.tcmis.server7.client.rollsroyce.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class RollsRoyceServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/rollsroyce"},
          {"DEBUG","false"},
          {"DB_CLIENT","ROLLS_ROYCE"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/rollsroyce/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/rollsroyce/schdelivery?"},
          {"CANCEL_MR","/tcmIS/rollsroyce/cancel?"},
          {"WASTE_ORDER","/tcmIS/rollsroyce/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/rollsroyce/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/rollsroyce/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/rollsroyce/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/rollsroyce/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/rollsroyce/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/rollsroyce/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.rollsroyceservlets.RollsRoyceBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public RollsRoyceServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("RollsRoyce");
     }

}