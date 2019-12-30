package radian.tcmis.server7.client.hudson.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

	public class HudsonServerResourceBundle  extends ServerResourceBundle {

	     static final Object[][] cHash = {
	          {"LOG_DIR","/home/servlet/radian/logs/hudson"},
	          {"DEBUG","false"},
	          {"DB_CLIENT","HUDSON"},
	          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

	          {"COST_REPORT","/tcmIS/hudson/betacostreport?"},
	          {"SCHEDULDE_DELIVERY","/tcmIS/hudson/schdelivery?"},
	          {"CANCEL_MR","/tcmIS/hudson/cancel?"},
	          {"WASTE_ORDER","/tcmIS/hudson/wasteorder?"},
	          {"WASTE_ADHOC_REPORT","/tcmIS/hudson/wastereport?"},
	          {"USAGE_ADHOC_REPORT","/tcmIS/hudson/usagereport?"},
	          {"MATERIAL_MATRIX_REPORT","/tcmIS/hudson/matrixreport?"},
	          {"VIEW_MSDS","/tcmIS/hudson/ViewMsds?"},
	          {"BATCH_REPORT_SERVLET","/tcmIS/hudson/batchreport?"},
	          {"REGISTER_SERVLET","/tcmIS/hudson/Register"},

	          {"SALES_ORDER_LOCATION","www.tcmis.com"},
	          {"SALES_ORDER_PORT","443"},
	          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.hudson.servlets.HudsonBuildSalesOrder"},

	          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
	          {"BASE_DIR_TCM_WWW","/webdata/html/"},
	          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
	          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
	          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
	          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
	          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
	     };

	     public HudsonServerResourceBundle(){
	            super();
	            addHash(cHash);
	     }

	     //trong 5/15
	     public String getDbClient(){
	      return ("HUDSON");
	     }

	}