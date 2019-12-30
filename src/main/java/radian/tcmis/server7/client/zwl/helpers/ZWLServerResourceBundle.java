package radian.tcmis.server7.client.zwl.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class ZWLServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/zwl"},
          {"DEBUG","false"},
          {"DB_CLIENT","ZWL"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/zwl/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/zwl/schdelivery?"},
          {"CANCEL_MR","/tcmIS/zwl/cancel?"},
          {"WASTE_ORDER","/tcmIS/zwl/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/zwl/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/zwl/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/zwl/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/zwl/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/zwl/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/zwl/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.zwl.servlets.ZWLBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public ZWLServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("ZWL");
     }

}