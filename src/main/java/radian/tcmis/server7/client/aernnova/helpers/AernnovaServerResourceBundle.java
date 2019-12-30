package radian.tcmis.server7.client.aernnova.helpers;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class AernnovaServerResourceBundle  extends ServerResourceBundle {

	     static final Object[][] cHash = {
	          {"LOG_DIR","/home/servlet/radian/logs/aernnova"},
	          {"DEBUG","false"},
	          {"DB_CLIENT","AERNNOVA"},
	          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

	          {"COST_REPORT","/tcmIS/aernnova/betacostreport?"},
	          {"SCHEDULDE_DELIVERY","/tcmIS/aernnova/schdelivery?"},
	          {"CANCEL_MR","/tcmIS/aernnova/cancel?"},
	          {"WASTE_ORDER","/tcmIS/aernnova/wasteorder?"},
	          {"WASTE_ADHOC_REPORT","/tcmIS/aernnova/wastereport?"},
	          {"USAGE_ADHOC_REPORT","/tcmIS/aernnova/usagereport?"},
	          {"MATERIAL_MATRIX_REPORT","/tcmIS/aernnova/matrixreport?"},
	          {"VIEW_MSDS","/tcmIS/aernnova/ViewMsds?"},
	          {"BATCH_REPORT_SERVLET","/tcmIS/aernnova/batchreport?"},
	          {"REGISTER_SERVLET","/tcmIS/aernnova/Register"},

	          {"SALES_ORDER_LOCATION","www.tcmis.com"},
	          {"SALES_ORDER_PORT","443"},
	          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.aernnova.servlets.AernnovaBuildSalesOrder"},

	          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
	          {"BASE_DIR_TCM_WWW","/webdata/html/"},
	          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
	          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
	          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
	          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
	          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
	     };

	     public AernnovaServerResourceBundle(){
	            super();
	            addHash(cHash);
	     }

	     //trong 5/15
	     public String getDbClient(){
	      return ("AERNNOVA");
	     }

	}