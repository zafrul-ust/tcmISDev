package radian.tcmis.server7.client.hrgivon.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class HrgivonServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/hrgivon"},
          {"DEBUG","false"},
          {"DB_CLIENT","Hrgivon"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/hrgivon/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/hrgivon/schdelivery?"},
          {"CANCEL_MR","/tcmIS/hrgivon/cancel?"},
          {"WASTE_ORDER","/tcmIS/hrgivon/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/hrgivon/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/hrgivon/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/hrgivon/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/hrgivon/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/hrgivon/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/hrgivon/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.hrgivon.servlets.HrgivonBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public HrgivonServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("Hrgivon");
     }

}