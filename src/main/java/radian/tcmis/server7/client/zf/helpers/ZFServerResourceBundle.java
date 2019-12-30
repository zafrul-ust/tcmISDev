package radian.tcmis.server7.client.zf.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class ZFServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/zf"},
          {"DEBUG","false"},
          {"DB_CLIENT","ZF"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/zf/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/zf/schdelivery?"},
          {"CANCEL_MR","/tcmIS/zf/cancel?"},
          {"WASTE_ORDER","/tcmIS/zf/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/zf/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/zf/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/zf/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/zf/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/zf/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/zf/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.zf.servlets.ZFBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public ZFServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("ZF");
     }

}