package radian.tcmis.server7.client.cmm.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class CMMServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/cmm"},
          {"DEBUG","false"},
          {"DB_CLIENT","CMM"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/cmm/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/cmm/schdelivery?"},
          {"CANCEL_MR","/tcmIS/cmm/cancel?"},
          {"WASTE_ORDER","/tcmIS/cmm/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/cmm/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/cmm/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/cmm/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/cmm/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/cmm/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/cmm/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.cmm.servlets.CMMBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public CMMServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("CMM");
     }

}