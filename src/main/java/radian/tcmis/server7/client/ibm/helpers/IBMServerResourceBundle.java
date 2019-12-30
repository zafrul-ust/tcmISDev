package radian.tcmis.server7.client.ibm.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

	public class IBMServerResourceBundle  extends ServerResourceBundle {

	     static final Object[][] cHash = {
	          {"LOG_DIR","/home/servlet/radian/logs/ibm"},
	          {"DEBUG","false"},
	          {"DB_CLIENT","IBM"},
	          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

	          {"COST_REPORT","/tcmIS/ibm/betacostreport?"},
	          {"SCHEDULDE_DELIVERY","/tcmIS/ibm/schdelivery?"},
	          {"CANCEL_MR","/tcmIS/ibm/cancel?"},
	          {"WASTE_ORDER","/tcmIS/ibm/wasteorder?"},
	          {"WASTE_ADHOC_REPORT","/tcmIS/ibm/wastereport?"},
	          {"USAGE_ADHOC_REPORT","/tcmIS/ibm/usagereport?"},
	          {"MATERIAL_MATRIX_REPORT","/tcmIS/ibm/matrixreport?"},
	          {"VIEW_MSDS","/tcmIS/ibm/ViewMsds?"},
	          {"BATCH_REPORT_SERVLET","/tcmIS/ibm/batchreport?"},
	          {"REGISTER_SERVLET","/tcmIS/ibm/Register"},

	          {"SALES_ORDER_LOCATION","www.tcmis.com"},
	          {"SALES_ORDER_PORT","443"},
	          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.ibm.servlets.IBMBuildSalesOrder"},

	          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
	          {"BASE_DIR_TCM_WWW","/webdata/html/"},
	          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
	          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
	          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
	          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
	          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
	     };

	     public IBMServerResourceBundle(){
	            super();
	            addHash(cHash);
	     }

	     //trong 5/15
	     public String getDbClient(){
	      return ("IBM");
	     }

	}