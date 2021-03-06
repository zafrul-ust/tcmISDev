package radian.tcmis.server7.client.pcc.helpers;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

	public class PCCServerResourceBundle  extends ServerResourceBundle {

	     static final Object[][] cHash = {
	          {"LOG_DIR","/home/servlet/radian/logs/pcc"},
	          {"DEBUG","false"},
	          {"DB_CLIENT","PCC"},
	          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

	          {"COST_REPORT","/tcmIS/pcc/betacostreport?"},
	          {"SCHEDULDE_DELIVERY","/tcmIS/pcc/schdelivery?"},
	          {"CANCEL_MR","/tcmIS/pcc/cancel?"},
	          {"WASTE_ORDER","/tcmIS/pcc/wasteorder?"},
	          {"WASTE_ADHOC_REPORT","/tcmIS/pcc/wastereport?"},
	          {"USAGE_ADHOC_REPORT","/tcmIS/pcc/usagereport?"},
	          {"MATERIAL_MATRIX_REPORT","/tcmIS/pcc/matrixreport?"},
	          {"VIEW_MSDS","/tcmIS/pcc/ViewMsds?"},
	          {"BATCH_REPORT_SERVLET","/tcmIS/pcc/batchreport?"},
	          {"REGISTER_SERVLET","/tcmIS/pcc/Register"},

	          {"SALES_ORDER_LOCATION","www.tcmis.com"},
	          {"SALES_ORDER_PORT","443"},
	          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.pcc.servlets.PCCBuildSalesOrder"},

	          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
	          {"BASE_DIR_TCM_WWW","/webdata/html/"},
	          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
	          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
	          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
	          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
	          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
	     };

	     public PCCServerResourceBundle(){
	            super();
	            addHash(cHash);
	     }

	     //trong 5/15
	     public String getDbClient(){
	      return ("PCC");
	     }

	}