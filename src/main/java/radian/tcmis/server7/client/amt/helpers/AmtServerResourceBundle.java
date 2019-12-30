package radian.tcmis.server7.client.amt.helpers;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class AmtServerResourceBundle  extends ServerResourceBundle {

	     static final Object[][] cHash = {
	          {"LOG_DIR","/home/servlet/radian/logs/amt"},
	          {"DEBUG","false"},
	          {"DB_CLIENT","AMT"},
	          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

	          {"COST_REPORT","/tcmIS/amt/betacostreport?"},
	          {"SCHEDULDE_DELIVERY","/tcmIS/amt/schdelivery?"},
	          {"CANCEL_MR","/tcmIS/amt/cancel?"},
	          {"WASTE_ORDER","/tcmIS/amt/wasteorder?"},
	          {"WASTE_ADHOC_REPORT","/tcmIS/amt/wastereport?"},
	          {"USAGE_ADHOC_REPORT","/tcmIS/amt/usagereport?"},
	          {"MATERIAL_MATRIX_REPORT","/tcmIS/amt/matrixreport?"},
	          {"VIEW_MSDS","/tcmIS/amt/ViewMsds?"},
	          {"BATCH_REPORT_SERVLET","/tcmIS/amt/batchreport?"},
	          {"REGISTER_SERVLET","/tcmIS/amt/Register"},

	          {"SALES_ORDER_LOCATION","www.tcmis.com"},
	          {"SALES_ORDER_PORT","443"},
	          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.amt.servlets.AmtBuildSalesOrder"},

	          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
	          {"BASE_DIR_TCM_WWW","/webdata/html/"},
	          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
	          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
	          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
	          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
	          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
	     };

	     public AmtServerResourceBundle(){
	            super();
	            addHash(cHash);
	     }

	     //trong 5/15
	     public String getDbClient(){
	      return ("AMT");
	     }

	}