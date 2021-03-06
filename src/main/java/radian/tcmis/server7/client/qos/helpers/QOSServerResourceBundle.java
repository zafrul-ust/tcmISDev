package radian.tcmis.server7.client.qos.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class QOSServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/qos"},
          {"DEBUG","false"},
          {"DB_CLIENT","QOS"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/qos/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/qos/schdelivery?"},
          {"CANCEL_MR","/tcmIS/qos/cancel?"},
          {"WASTE_ORDER","/tcmIS/qos/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/qos/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/qos/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/qos/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/qos/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/qos/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/qos/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.qos.servlets.QOSBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public QOSServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("QOS");
     }

}