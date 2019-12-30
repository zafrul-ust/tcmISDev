package radian.tcmis.server7.client.cat.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class CaterpillarServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/cat"},
          {"DEBUG","false"},
          {"DB_CLIENT","Caterpillar"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/cat/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/cat/schdelivery?"},
          {"CANCEL_MR","/tcmIS/cat/cancel?"},
          {"WASTE_ORDER","/tcmIS/cat/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/cat/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/cat/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/cat/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/cat/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/cat/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/cat/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.cat.servlets.CaterpillarBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public CaterpillarServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("Caterpillar");
     }

}