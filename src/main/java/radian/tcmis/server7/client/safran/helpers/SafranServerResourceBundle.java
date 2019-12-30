package radian.tcmis.server7.client.safran.helpers;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class SafranServerResourceBundle  extends ServerResourceBundle {

	     static final Object[][] cHash = {
	          {"LOG_DIR","/home/servlet/radian/logs/safran"},
	          {"DEBUG","false"},
	          {"DB_CLIENT","SAFRAN"},
	          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

	          {"COST_REPORT","/tcmIS/safran/betacostreport?"},
	          {"SCHEDULDE_DELIVERY","/tcmIS/safran/schdelivery?"},
	          {"CANCEL_MR","/tcmIS/safran/cancel?"},
	          {"WASTE_ORDER","/tcmIS/safran/wasteorder?"},
	          {"WASTE_ADHOC_REPORT","/tcmIS/safran/wastereport?"},
	          {"USAGE_ADHOC_REPORT","/tcmIS/safran/usagereport?"},
	          {"MATERIAL_MATRIX_REPORT","/tcmIS/safran/matrixreport?"},
	          {"VIEW_MSDS","/tcmIS/safran/ViewMsds?"},
	          {"BATCH_REPORT_SERVLET","/tcmIS/safran/batchreport?"},
	          {"REGISTER_SERVLET","/tcmIS/safran/Register"},

	          {"SALES_ORDER_LOCATION","www.tcmis.com"},
	          {"SALES_ORDER_PORT","443"},
	          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.safran.servlets.SafranBuildSalesOrder"},

	          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
	          {"BASE_DIR_TCM_WWW","/webdata/html/"},
	          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
	          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
	          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
	          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
	          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
	     };

	     public SafranServerResourceBundle(){
	            super();
	            addHash(cHash);
	     }

	     //trong 5/15
	     public String getDbClient(){
	      return ("SAFRAN");
	     }

	}

