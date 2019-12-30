package radian.tcmis.server7.client.verasun.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class VerasunServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/verasun"},
          {"DEBUG","false"},
          {"DB_CLIENT","Verasun"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/verasun/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/verasun/schdelivery?"},
          {"CANCEL_MR","/tcmIS/verasun/cancel?"},
          {"WASTE_ORDER","/tcmIS/verasun/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/verasun/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/verasun/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/verasun/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/verasun/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/verasun/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/verasun/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.verasun.servlets.VerasunBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public VerasunServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("Verasun");
     }

}