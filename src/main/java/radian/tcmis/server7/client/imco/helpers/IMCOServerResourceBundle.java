package radian.tcmis.server7.client.imco.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class IMCOServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/imco"},
          {"DEBUG","false"},
          {"DB_CLIENT","IMCO"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/imco/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/imco/schdelivery?"},
          {"CANCEL_MR","/tcmIS/imco/cancel?"},
          {"WASTE_ORDER","/tcmIS/imco/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/imco/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/imco/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/imco/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/imco/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/imco/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/imco/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.imco.servlets.IMCOBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public IMCOServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("IMCO");
     }

}