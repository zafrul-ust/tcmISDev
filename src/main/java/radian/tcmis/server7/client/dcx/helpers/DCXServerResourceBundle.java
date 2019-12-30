package radian.tcmis.server7.client.dcx.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class DCXServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/dcx"},
          {"DEBUG","false"},
          {"DB_CLIENT","DCX"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/dcx/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/dcx/schdelivery?"},
          {"CANCEL_MR","/tcmIS/dcx/cancel?"},
          {"WASTE_ORDER","/tcmIS/dcx/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/dcx/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/dcx/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/dcx/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/dcx/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/dcx/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/dcx/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.dcx.servlets.DCXBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public DCXServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("DCX");
     }

}