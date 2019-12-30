package radian.tcmis.server7.client.goodrich.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class GoodrichServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/goodrich"},
          {"DEBUG","false"},
          {"DB_CLIENT","Goodrich"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/goodrich/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/goodrich/schdelivery?"},
          {"CANCEL_MR","/tcmIS/goodrich/cancel?"},
          {"WASTE_ORDER","/tcmIS/goodrich/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/goodrich/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/goodrich/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/goodrich/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/goodrich/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/goodrich/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/goodrich/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.goodrich.servlets.GoodrichBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public GoodrichServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("Goodrich");
     }

}