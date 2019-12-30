package radian.tcmis.server7.client.nalco.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class NalcoServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/nalco"},
          {"DEBUG","false"},
          {"DB_CLIENT","Nalco"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/nalco/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/nalco/schdelivery?"},
          {"CANCEL_MR","/tcmIS/nalco/cancel?"},
          {"WASTE_ORDER","/tcmIS/nalco/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/nalco/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/nalco/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/nalco/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/nalco/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/nalco/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/nalco/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.nalco.servlets.NalcoBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public NalcoServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("Nalco");
     }

}