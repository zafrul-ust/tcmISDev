package radian.tcmis.server7.client.siemens.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class SiemensServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/siemens"},
          {"DEBUG","false"},
          {"DB_CLIENT","Siemens"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/siemens/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/siemens/schdelivery?"},
          {"CANCEL_MR","/tcmIS/siemens/cancel?"},
          {"WASTE_ORDER","/tcmIS/siemens/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/siemens/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/siemens/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/siemens/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/siemens/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/siemens/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/siemens/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.siemens.servlets.SiemensBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public SiemensServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("Siemens");
     }

}