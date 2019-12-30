package radian.tcmis.server7.client.team.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class TeamServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/home/servlet/radian/logs/team"},
          {"DEBUG","false"},
          {"DB_CLIENT","Team"},
          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

          {"COST_REPORT","/tcmIS/team/betacostreport?"},
          {"SCHEDULDE_DELIVERY","/tcmIS/team/schdelivery?"},
          {"CANCEL_MR","/tcmIS/team/cancel?"},
          {"WASTE_ORDER","/tcmIS/team/wasteorder?"},
          {"WASTE_ADHOC_REPORT","/tcmIS/team/wastereport?"},
          {"USAGE_ADHOC_REPORT","/tcmIS/team/usagereport?"},
          {"MATERIAL_MATRIX_REPORT","/tcmIS/team/matrixreport?"},
          {"VIEW_MSDS","/tcmIS/team/ViewMsds?"},
          {"BATCH_REPORT_SERVLET","/tcmIS/team/batchreport?"},
          {"REGISTER_SERVLET","/tcmIS/team/Register"},

          {"SALES_ORDER_LOCATION","www.tcmis.com"},
          {"SALES_ORDER_PORT","443"},
          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.team.servlets.TeamBuildSalesOrder"},

          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
     };

     public TeamServerResourceBundle(){
            super();
            addHash(cHash);
     }

     //trong 5/15
     public String getDbClient(){
      return ("Team");
     }

}