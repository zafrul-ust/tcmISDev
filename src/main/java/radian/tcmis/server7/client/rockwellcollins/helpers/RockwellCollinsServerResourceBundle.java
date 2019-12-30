package radian.tcmis.server7.client.rockwellcollins.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class RockwellCollinsServerResourceBundle extends ServerResourceBundle {

	     static final Object[][] cHash = {
	          {"LOG_DIR","/home/servlet/radian/logs/rockwell"},
	          {"DEBUG","false"},
	          {"DB_CLIENT","ROCKWELL"},
	          {"DB_CLIENT_NAME","ROCKWELL COLLINS"},	          
	          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

	          {"COST_REPORT","/tcmIS/rockwell/betacostreport?"},
	          {"SCHEDULDE_DELIVERY","/tcmIS/rockwell/schdelivery?"},
	          {"CANCEL_MR","/tcmIS/rockwell/cancel?"},
	          {"WASTE_ORDER","/tcmIS/rockwell/wasteorder?"},
	          {"WASTE_ADHOC_REPORT","/tcmIS/rockwell/wastereport?"},
	          {"USAGE_ADHOC_REPORT","/tcmIS/rockwell/usagereport?"},
	          {"MATERIAL_MATRIX_REPORT","/tcmIS/rockwell/matrixreport?"},
	          {"VIEW_MSDS","/tcmIS/rockwell/ViewMsds?"},
	          {"BATCH_REPORT_SERVLET","/tcmIS/rockwell/batchreport?"},
	          {"REGISTER_SERVLET","/tcmIS/rockwell/Register"},

	          {"SALES_ORDER_LOCATION","www.tcmis.com"},
	          {"SALES_ORDER_PORT","443"},
	          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.rockwell.servlets.RockwellCollinsBuildSalesOrder"},

	          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
	          {"BASE_DIR_TCM_WWW","/webdata/html/"},
	          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
	          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
	          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
	          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
	          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
	     };

	     public RockwellCollinsServerResourceBundle(){
	            super();
	            addHash(cHash);
	     }

	     //trong 5/15
	     public String getDbClient(){
	      return ("ROCKWELL");
	     }

	}
