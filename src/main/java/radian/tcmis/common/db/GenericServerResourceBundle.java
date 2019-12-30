package radian.tcmis.common.db;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class GenericServerResourceBundle  extends ServerResourceBundle {

  String client;
  static final Object[][] rayHash = {
       {"LOG_DIR","/home/servlet/radian/logs/ray"},
       {"DEBUG","false"},
       {"DB_CLIENT","Ray"},
       {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},
       {"ORACLE_URL","jdbc:oracle:thin:@dbprod.tcmis.com:1521:tcmprod"},
       {"DB_USER","raytheon"},
       {"DB_PASS","r8the0n"},

       {"COST_REPORT","/tcmIS/ray/betacostreport?"},
       {"SCHEDULDE_DELIVERY","/tcmIS/ray/schdelivery?"},
       {"CANCEL_MR","/tcmIS/ray/cancel?"},
       {"WASTE_ORDER","/tcmIS/ray/wasteorder?"},
       {"WASTE_ADHOC_REPORT","/tcmIS/ray/wastereport?"},
       {"USAGE_ADHOC_REPORT","/tcmIS/ray/usagereport?"},
       {"MATERIAL_MATRIX_REPORT","/tcmIS/ray/matrixreport?"},
       {"VIEW_MSDS","/tcmIS/ray/ViewMsds?"},
       {"BATCH_REPORT_SERVLET","/tcmIS/ray/batchreport?"},
       {"REGISTER_SERVLET","/tcmIS/ray/Register"},

       {"SALES_ORDER_LOCATION","www.tcmis.com"},
       {"SALES_ORDER_PORT","443"},
       {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.ray.servlets.RayBuildSalesOrder"},

       {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
       {"BASE_DIR_TCM_WWW","/webdata/html/"},
       {"WEBS_HOME_WWW","https://www.tcmis.com/"},
       {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
       {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
       {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
       {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
  };

  static final Object[][] baeHash = {
     {"LOG_DIR","/home/servlet/radian/logs/bae"},
     {"DEBUG","false"},
     {"DB_CLIENT","BAE"},
     {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},
     {"ORACLE_URL","jdbc:oracle:thin:@dbprod.tcmis.com:1521:tcmprod"},
     {"DB_USER","bae"},
     {"DB_PASS","j0hns0nc1ty"},

     {"COST_REPORT","/tcmIS/bae/betacostreport?"},
     {"SCHEDULDE_DELIVERY","/tcmIS/bae/schdelivery?"},
     {"CANCEL_MR","/tcmIS/bae/cancel?"},
     {"WASTE_ORDER","/tcmIS/bae/wasteorder?"},
     {"WASTE_ADHOC_REPORT","/tcmIS/bae/wastereport?"},
     {"USAGE_ADHOC_REPORT","/tcmIS/bae/usagereport?"},
     {"MATERIAL_MATRIX_REPORT","/tcmIS/bae/matrixreport?"},
     {"VIEW_MSDS","/tcmIS/bae/ViewMsds?"},
     {"BATCH_REPORT_SERVLET","/tcmIS/bae/batchreport?"},
     {"REGISTER_SERVLET","/tcmIS/bae/Register"},

     {"SALES_ORDER_LOCATION","www.tcmis.com"},
     {"SALES_ORDER_PORT","443"},
     {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.bae.servlets.BAEBuildSalesOrder"},

     {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
     {"BASE_DIR_TCM_WWW","/webdata/html/"},
     {"WEBS_HOME_WWW","https://www.tcmis.com/"},
     {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
     {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
     {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
     {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
};

 static final Object[][] calHash = {
      {"LOG_DIR","/home/servlet/radian/logs/cal"},
      {"DEBUG","false"},
      {"DB_CLIENT","CAL"},
      {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},
      {"ORACLE_URL","jdbc:oracle:thin:@dbprod.tcmis.com:1521:tcmprod"},
      {"DB_USER","CAL"},
      {"DB_PASS","h0ust0n"},

      {"COST_REPORT","/tcmIS/cal/betacostreport?"},
      {"SCHEDULDE_DELIVERY","/tcmIS/cal/schdelivery?"},
      {"CANCEL_MR","/tcmIS/cal/cancel?"},
      {"WASTE_ORDER","/tcmIS/cal/wasteorder?"},
      {"WASTE_ADHOC_REPORT","/tcmIS/cal/wastereport?"},
      {"USAGE_ADHOC_REPORT","/tcmIS/cal/usagereport?"},
      {"MATERIAL_MATRIX_REPORT","/tcmIS/cal/matrixreport?"},
      {"VIEW_MSDS","/tcmIS/cal/ViewMsds?"},
      {"BATCH_REPORT_SERVLET","/tcmIS/cal/batchreport?"},
      {"REGISTER_SERVLET","/tcmIS/cal/Register"},

      {"SALES_ORDER_LOCATION","www.tcmis.com"},
      {"SALES_ORDER_PORT","443"},
      {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.cal.servlets.CALBuildSalesOrder"},

      {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
      {"BASE_DIR_TCM_WWW","/webdata/html/"},
      {"WEBS_HOME_WWW","https://www.tcmis.com/"},
      {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
      {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
      {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
      {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
 };

 static final Object[][] drsHash = {
      {"LOG_DIR","/home/servlet/radian/logs.drs"},
      {"DEBUG","false"},
      {"DB_CLIENT","DRS"},
      {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},
      {"ORACLE_URL","jdbc:oracle:thin:@dbprod.tcmis.com:1521:tcmprod"},
      {"DB_USER","drs"},
      {"DB_PASS","irt3ch"},

      {"COST_REPORT","/tcmIS/drs/betacostreport?"},
      {"SCHEDULDE_DELIVERY","/tcmIS/drs/schdelivery?"},
      {"CANCEL_MR","/tcmIS/drs/cancel?"},
      {"WASTE_ORDER","/tcmIS/drs/wasteorder?"},
      {"WASTE_ADHOC_REPORT","/tcmIS/drs/wastereport?"},
      {"USAGE_ADHOC_REPORT","/tcmIS/drs/usagereport?"},
      {"MATERIAL_MATRIX_REPORT","/tcmIS/drs/matrixreport?"},
      {"VIEW_MSDS","/tcmIS/drs/ViewMsds?"},
      {"BATCH_REPORT_SERVLET","/tcmIS/drs/batchreport?"},
      {"REGISTER_SERVLET","/tcmIS/drs/Register"},

      {"SALES_ORDER_LOCATION","www.tcmis.com"},
      {"SALES_ORDER_PORT","443"},
      {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.drs.servlets.DRSBuildSalesOrder"},

      {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
      {"BASE_DIR_TCM_WWW","/webdata/html/"},
      {"WEBS_HOME_WWW","https://www.tcmis.com/"},
      {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
      {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
      {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
      {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
 };

 static final Object[][] fecHash = {
     {"LOG_DIR","/home/servlet/radian/logs/fec"},
     {"DEBUG","false"},
     {"DB_CLIENT","FEC"},
     {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},
     {"ORACLE_URL","jdbc:oracle:thin:@dbprod.tcmis.com:1521:tcmprod"},
     {"DB_USER","FEC"},
     {"DB_PASS","h0ust0n"},

     {"COST_REPORT","/tcmIS/fec/betacostreport?"},
     {"SCHEDULDE_DELIVERY","/tcmIS/fec/schdelivery?"},
     {"CANCEL_MR","/tcmIS/fec/cancel?"},
     {"WASTE_ORDER","/tcmIS/fec/wasteorder?"},
     {"WASTE_ADHOC_REPORT","/tcmIS/fec/wastereport?"},
     {"USAGE_ADHOC_REPORT","/tcmIS/fec/usagereport?"},
     {"MATERIAL_MATRIX_REPORT","/tcmIS/fec/matrixreport?"},
     {"VIEW_MSDS","/tcmIS/fec/ViewMsds?"},
     {"BATCH_REPORT_SERVLET","/tcmIS/fec/batchreport?"},
     {"REGISTER_SERVLET","/tcmIS/fec/Register"},

     {"SALES_ORDER_LOCATION","www.tcmis.com"},
     {"SALES_ORDER_PORT","443"},
     {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.fec.servlets.FECBuildSalesOrder"},

     {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
     {"BASE_DIR_TCM_WWW","/webdata/html/"},
     {"WEBS_HOME_WWW","https://www.tcmis.com/"},
     {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
     {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
     {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
     {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
};
 static final Object[][] gmHash = {
      {"LOG_DIR","/home/servlet/radian/logs/gm"},
      {"DEBUG","false"},
      {"DB_CLIENT","GM"},
      {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},
      {"ORACLE_URL","jdbc:oracle:thin:@dbprod.tcmis.com:1521:tcmprod"},
      {"DB_USER","GM"},
      {"DB_PASS","p0nt1ac"},

      {"COST_REPORT","/tcmIS/gm/betacostreport?"},
      {"SCHEDULDE_DELIVERY","/tcmIS/gm/schdelivery?"},
      {"CANCEL_MR","/tcmIS/gm/cancel?"},
      {"WASTE_ORDER","/tcmIS/gm/wasteorder?"},
      {"WASTE_ADHOC_REPORT","/tcmIS/gm/wastereport?"},
      {"USAGE_ADHOC_REPORT","/tcmIS/gm/usagereport?"},
      {"MATERIAL_MATRIX_REPORT","/tcmIS/gm/matrixreport?"},
      {"VIEW_MSDS","/tcmIS/gm/ViewMsds?"},
      {"BATCH_REPORT_SERVLET","/tcmIS/gm/batchreport?"},
      {"REGISTER_SERVLET","/tcmIS/gm/Register"},

      {"SALES_ORDER_LOCATION","www.tcmis.com"},
      {"SALES_ORDER_PORT","443"},
      {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.gm.servlets.GMBuildSalesOrder"},

      {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
      {"BASE_DIR_TCM_WWW","/webdata/html/"},
      {"WEBS_HOME_WWW","https://www.tcmis.com/"},
      {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
      {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
      {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
      {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
 };
 static final Object[][] usgovHash = {
      {"LOG_DIR","/home/servlet/radian/logs/usgov"},
      {"DEBUG","false"},
      {"DB_CLIENT","Tcm_Ops"},
      {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},
      {"ORACLE_URL","jdbc:oracle:thin:@dbprod.tcmis.com:1521:tcmprod"},
      {"DB_USER","tcm_ops"},
      {"DB_PASS","drives"},
      {"SALES_ORDER_LOCATION","www.tcmis.com"},
      {"SALES_ORDER_PORT","443"},
      {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.usgov.servlets.USGOVBuildSalesOrder"}
 };

 static final Object[][] lmcoHash = {
      {"LOG_DIR","/home/servlet/radian/logs/lmco"},
      {"DEBUG","false"},
      {"DB_CLIENT","LMCO"},
      {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},
      {"ORACLE_URL","jdbc:oracle:thin:@dbprod.tcmis.com:1521:tcmprod"},
      {"DB_USER","lockheed"},
      {"DB_PASS","b1teme"},

      {"COST_REPORT","/tcmIS/lmco/betacostreport?"},
      {"SCHEDULDE_DELIVERY","/tcmIS/lmco/schdelivery?"},
      {"CANCEL_MR","/tcmIS/lmco/cancel?"},
      {"WASTE_ORDER","/tcmIS/lmco/wasteorder?"},
      {"WASTE_ADHOC_REPORT","/tcmIS/lmco/wastereport?"},
      {"USAGE_ADHOC_REPORT","/tcmIS/lmco/usagereport?"},
      {"MATERIAL_MATRIX_REPORT","/tcmIS/lmco/matrixreport?"},
      {"VIEW_MSDS","/tcmIS/lmco/ViewMsds?"},
      {"BATCH_REPORT_SERVLET","/tcmIS/lmco/batchreport?"},
      {"REGISTER_SERVLET","/tcmIS/lmco/Register"},

      {"SALES_ORDER_LOCATION","www.tcmis.com"},
      {"SALES_ORDER_PORT","443"},
      {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.lmco.servlets.LMCOBuildSalesOrder"},

      {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
      {"BASE_DIR_TCM_WWW","/webdata/html/"},
      {"WEBS_HOME_WWW","https://www.tcmis.com/"},
      {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
      {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
      {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
      {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
 };

 static final Object[][] millerHash = {
      {"LOG_DIR","/home/servlet/radian/logs/miller"},
      {"DEBUG","false"},
      {"DB_CLIENT","miller"},
      {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},
      {"ORACLE_URL","jdbc:oracle:thin:@dbprod.tcmis.com:1521:tcmprod"},
      {"DB_USER","miller"},
      {"DB_PASS","suds"},

      {"COST_REPORT","/tcmIS/miller/betacostreport?"},
      {"SCHEDULDE_DELIVERY","/tcmIS/miller/schdelivery?"},
      {"CANCEL_MR","/tcmIS/miller/cancel?"},
      {"WASTE_ORDER","/tcmIS/miller/wasteorder?"},
      {"WASTE_ADHOC_REPORT","/tcmIS/miller/wastereport?"},
      {"USAGE_ADHOC_REPORT","/tcmIS/miller/usagereport?"},
      {"MATERIAL_MATRIX_REPORT","/tcmIS/miller/matrixreport?"},
      {"VIEW_MSDS","/tcmIS/miller/ViewMsds?"},
      {"BATCH_REPORT_SERVLET","/tcmIS/miller/batchreport?"},
      {"REGISTER_SERVLET","/tcmIS/miller/Register"},

      {"SALES_ORDER_LOCATION","www.tcmis.com"},
      {"SALES_ORDER_PORT","443"},
      {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.miller.servlets.MillerBuildSalesOrder"},

      {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
      {"BASE_DIR_TCM_WWW","/webdata/html/"},
      {"WEBS_HOME_WWW","https://www.tcmis.com/"},
      {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
      {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
      {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
      {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
 };

 static final Object[][] sdHash = {
      {"LOG_DIR","/home/servlet/radian/logs/sd"},
      {"DEBUG","false"},
      {"DB_CLIENT","SD"},
      {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},
      {"ORACLE_URL","jdbc:oracle:thin:@dbprod.tcmis.com:1521:tcmprod"},
      {"DB_USER","sauer_danfoss"},
      {"DB_PASS","sd1"},

      {"COST_REPORT","/tcmIS/sd/betacostreport?"},
      {"SCHEDULDE_DELIVERY","/tcmIS/sd/schdelivery?"},
      {"CANCEL_MR","/tcmIS/sd/cancel?"},
      {"WASTE_ORDER","/tcmIS/sd/wasteorder?"},
      {"WASTE_ADHOC_REPORT","/tcmIS/sd/wastereport?"},
      {"USAGE_ADHOC_REPORT","/tcmIS/sd/usagereport?"},
      {"MATERIAL_MATRIX_REPORT","/tcmIS/sd/matrixreport?"},
      {"VIEW_MSDS","/tcmIS/sd/ViewMsds?"},
      {"BATCH_REPORT_SERVLET","/tcmIS/sd/batchreport?"},
      {"REGISTER_SERVLET","/tcmIS/sd/Register"},

      {"SALES_ORDER_LOCATION","www.tcmis.com"},
      {"SALES_ORDER_PORT","443"},
      {"SALES_ORDER_URI","tcmIS/servlet/radian.tcmis.server7.client.sd.servlets.SDBuildSalesOrder"},

      {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
      {"BASE_DIR_TCM_WWW","/webdata/html/"},
      {"WEBS_HOME_WWW","https://www.tcmis.com/"},
      {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
      {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
      {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
      {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
 };

 static final Object[][] seagateHash = {
      {"LOG_DIR","/home/servlet/radian/logs/seagate"},
      {"DEBUG","false"},
      {"DB_CLIENT","Seagate"},
      {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},
      {"ORACLE_URL","jdbc:oracle:thin:@dbprod.tcmis.com:1521:tcmprod"},
      {"DB_USER","seagate"},
      {"DB_PASS","drives"},

      {"COST_REPORT","/tcmIS/seagate/betacostreport?"},
      {"SCHEDULDE_DELIVERY","/tcmIS/seagate/schdelivery?"},
      {"CANCEL_MR","/tcmIS/seagate/cancel?"},
      {"WASTE_ORDER","/tcmIS/seagate/wasteorder?"},
      {"WASTE_ADHOC_REPORT","/tcmIS/seagate/wastereport?"},
      {"USAGE_ADHOC_REPORT","/tcmIS/seagate/usagereport?"},
      {"MATERIAL_MATRIX_REPORT","/tcmIS/seagate/matrixreport?"},
      {"VIEW_MSDS","/tcmIS/seagate/ViewMsds?"},
      {"BATCH_REPORT_SERVLET","/tcmIS/seagate/batchreport?"},
      {"REGISTER_SERVLET","/tcmIS/seagate/Register"},

      {"SALES_ORDER_LOCATION","www.tcmis.com"},
      {"SALES_ORDER_PORT","443"},
      {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.seagate.servlets.SeagateBuildSalesOrder"},

      {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
      {"BASE_DIR_TCM_WWW","/webdata/html/"},
      {"WEBS_HOME_WWW","https://www.tcmis.com/"},
      {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
      {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
      {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
      {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
 };

 static final Object[][] swaHash = {
      {"LOG_DIR","/home/servlet/radian/logs/swa"},
      {"DEBUG","false"},
      {"DB_CLIENT","SWA"},
      {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},
      {"ORACLE_URL","jdbc:oracle:thin:@dbprod.tcmis.com:1521:tcmprod"},
      {"DB_USER","swa"},
      {"DB_PASS","a1rl1n3"},

      {"COST_REPORT","/tcmIS/swa/betacostreport?"},
      {"SCHEDULDE_DELIVERY","/tcmIS/swa/schdelivery?"},
      {"CANCEL_MR","/tcmIS/swa/cancel?"},
      {"WASTE_ORDER","/tcmIS/swa/wasteorder?"},
      {"WASTE_ADHOC_REPORT","/tcmIS/swa/wastereport?"},
      {"USAGE_ADHOC_REPORT","/tcmIS/swa/usagereport?"},
      {"MATERIAL_MATRIX_REPORT","/tcmIS/swa/matrixreport?"},
      {"VIEW_MSDS","/tcmIS/swa/ViewMsds?"},
      {"BATCH_REPORT_SERVLET","/tcmIS/swa/batchreport?"},
      {"REGISTER_SERVLET","/tcmIS/swa/Register"},

      {"SALES_ORDER_LOCATION","www.tcmis.com"},
      {"SALES_ORDER_PORT","443"},
      {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.swa.servlets.SWABuildSalesOrder"},

      {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
      {"BASE_DIR_TCM_WWW","/webdata/html/"},
      {"WEBS_HOME_WWW","https://www.tcmis.com/"},
      {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
      {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
      {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
      {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
 };

 static final Object[][] utcHash = {
      {"LOG_DIR","/home/servlet/radian/logs/utc"},
      {"DEBUG","false"},
      {"DB_CLIENT","UTC"},
      {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},
      {"ORACLE_URL","jdbc:oracle:thin:@dbprod.tcmis.com:1521:tcmprod"},
      {"DB_USER","utc"},
      {"DB_PASS","ctu"},

      {"COST_REPORT","/tcmIS/utc/betacostreport?"},
      {"SCHEDULDE_DELIVERY","/tcmIS/utc/schdelivery?"},
      {"CANCEL_MR","/tcmIS/utc/cancel?"},
      {"WASTE_ORDER","/tcmIS/utc/wasteorder?"},
      {"WASTE_ADHOC_REPORT","/tcmIS/utc/wastereport?"},
      {"USAGE_ADHOC_REPORT","/tcmIS/utc/usagereport?"},
      {"MATERIAL_MATRIX_REPORT","/tcmIS/utc/matrixreport?"},
      {"VIEW_MSDS","/tcmIS/utc/ViewMsds?"},
      {"BATCH_REPORT_SERVLET","/tcmIS/utc/batchreport?"},
      {"REGISTER_SERVLET","/tcmIS/utc/Register"},

      {"SALES_ORDER_LOCATION","www.tcmis.com"},
      {"SALES_ORDER_PORT","443"},
      {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.utc.servlets.UTCBuildSalesOrder"},

      {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
      {"BASE_DIR_TCM_WWW","/webdata/html/"},
      {"WEBS_HOME_WWW","https://www.tcmis.com/"},
      {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
      {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
      {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
      {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
 };


 public GenericServerResourceBundle(String client) {
   super();
   this.client = client;
   if (client.equalsIgnoreCase("ray")) {
     addHash(rayHash);
   }
   else if (client.equalsIgnoreCase("bae")) {
     addHash(baeHash);
   }
   else if (client.equalsIgnoreCase("cal")) {
     addHash(calHash);
   }
   else if (client.equalsIgnoreCase("drs")) {
     addHash(drsHash);
   }
   else if (client.equalsIgnoreCase("fec")) {
     addHash(fecHash);
   }
   else if (client.equalsIgnoreCase("gm")) {
     addHash(gmHash);
   }
   else if (client.equalsIgnoreCase("internal")) {
     addHash(usgovHash);
   }
   else if (client.equalsIgnoreCase("lmco")) {
     addHash(lmcoHash);
   }
   else if (client.equalsIgnoreCase("miller")) {
     addHash(millerHash);
   }
   else if (client.equalsIgnoreCase("sd")) {
     addHash(sdHash);
   }
   else if (client.equalsIgnoreCase("seagate")) {
     addHash(seagateHash);
   }
   else if (client.equalsIgnoreCase("swa")) {
     addHash(swaHash);
   }
   else if (client.equalsIgnoreCase("utc")) {
     addHash(utcHash);
   }
 }

 //trong 5/15
 public String getDbClient() {
   return client;
 }
}
