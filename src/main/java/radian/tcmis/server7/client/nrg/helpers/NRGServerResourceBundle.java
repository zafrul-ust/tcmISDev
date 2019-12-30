package radian.tcmis.server7.client.nrg.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class NRGServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/nrg"},
          {"DEBUG","false"},
          {"DB_CLIENT","NRG"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/nrg/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/nrg/schdelivery?"},
          {"CANCEL_MR","/tcmIS/nrg/cancel?"},
          {"WASTE_ORDER","/tcmIS/nrg/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/nrg/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/nrg/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/nrg/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/nrg/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/nrg/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/nrg/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.nrg.servlets.NRGBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public NRGServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("NRG");
     }

}