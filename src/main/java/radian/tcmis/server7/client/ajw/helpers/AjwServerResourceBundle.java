package radian.tcmis.server7.client.ajw.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

	public class AjwServerResourceBundle  extends ServerResourceBundle {

	     static final Object[][] cHash = {
	          {"LOG_DIR","/home/servlet/radian/logs/dksh"},
	          {"DEBUG","false"},
	          {"DB_CLIENT","DKSH"},
	          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

	          {"COST_REPORT","/tcmIS/dksh/betacostreport?"},
	          {"SCHEDULDE_DELIVERY","/tcmIS/dksh/schdelivery?"},
	          {"CANCEL_MR","/tcmIS/dksh/cancel?"},
	          {"WASTE_ORDER","/tcmIS/dksh/wasteorder?"},
	          {"WASTE_ADHOC_REPORT","/tcmIS/dksh/wastereport?"},
	          {"USAGE_ADHOC_REPORT","/tcmIS/dksh/usagereport?"},
	          {"MATERIAL_MATRIX_REPORT","/tcmIS/dksh/matrixreport?"},
	          {"VIEW_MSDS","/tcmIS/dksh/ViewMsds?"},
	          {"BATCH_REPORT_SERVLET","/tcmIS/dksh/batchreport?"},
	          {"REGISTER_SERVLET","/tcmIS/dksh/Register"},

	          {"SALES_ORDER_LOCATION","www.tcmis.com"},
	          {"SALES_ORDER_PORT","443"},
	          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.dksh.servlets.DkshBuildSalesOrder"},

	          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
	          {"BASE_DIR_TCM_WWW","/webdata/html/"},
	          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
	          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
	          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
	          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
	          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
	     };

	     public AjwServerResourceBundle(){
	            super();
	            addHash(cHash);
	     }

	     //trong 5/15
	     public String getDbClient(){
	      return ("DKSH");
	     }

	}