package radian.tcmis.server7.client.sales.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class SalesServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/sales"},
          {"DEBUG","false"},
          {"DB_CLIENT","Sales"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/sales/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/sales/schdelivery?"},
          {"CANCEL_MR","/tcmIS/sales/cancel?"},
          {"WASTE_ORDER","/tcmIS/sales/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/sales/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/sales/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/sales/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/sales/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/sales/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/sales/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.sales.servlets.SalesBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public SalesServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("Sales");
     }

}