package radian.tcmis.server7.client.fec.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class FECServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/fec"},
          {"DEBUG","false"},
          {"DB_CLIENT","FEC"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/fec/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/fec/schdelivery?"},
          {"CANCEL_MR","/tcmIS/fec/cancel?"},
          {"WASTE_ORDER","/tcmIS/fec/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/fec/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/fec/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/fec/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/fec/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/fec/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/fec/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.fec.servlets.FECBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public FECServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("FEC");
     }

}