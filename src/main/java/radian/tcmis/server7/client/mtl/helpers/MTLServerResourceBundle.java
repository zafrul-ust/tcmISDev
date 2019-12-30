package radian.tcmis.server7.client.mtl.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class MTLServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/mtl"},
          {"DEBUG","false"},
          {"DB_CLIENT","MTL"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/mtl/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/mtl/schdelivery?"},
          {"CANCEL_MR","/tcmIS/mtl/cancel?"},
          {"WASTE_ORDER","/tcmIS/mtl/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/mtl/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/mtl/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/mtl/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/mtl/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/mtl/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/mtl/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.mtl.servlets.MTLBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public MTLServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("MTL");
     }

}