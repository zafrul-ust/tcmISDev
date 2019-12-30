package radian.tcmis.server7.client.latecoere.helpers;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class LatecoereServerResourceBundle  extends ServerResourceBundle {

	     static final Object[][] cHash = {
	          {"LOG_DIR","/home/servlet/radian/logs/latecoere"},
	          {"DEBUG","false"},
	          {"DB_CLIENT","LATECOURE"},
	          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

	          {"COST_REPORT","/tcmIS/latecoere/betacostreport?"},
	          {"SCHEDULDE_DELIVERY","/tcmIS/latecoere/schdelivery?"},
	          {"CANCEL_MR","/tcmIS/latecoere/cancel?"},
	          {"WASTE_ORDER","/tcmIS/latecoere/wasteorder?"},
	          {"WASTE_ADHOC_REPORT","/tcmIS/latecoere/wastereport?"},
	          {"USAGE_ADHOC_REPORT","/tcmIS/latecoere/usagereport?"},
	          {"MATERIAL_MATRIX_REPORT","/tcmIS/latecoere/matrixreport?"},
	          {"VIEW_MSDS","/tcmIS/latecoere/ViewMsds?"},
	          {"BATCH_REPORT_SERVLET","/tcmIS/latecoere/batchreport?"},
	          {"REGISTER_SERVLET","/tcmIS/latecoere/Register"},

	          {"SALES_ORDER_LOCATION","www.tcmis.com"},
	          {"SALES_ORDER_PORT","443"},
	          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.latecoere.servlets.LatecoereBuildSalesOrder"},

	          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
	          {"BASE_DIR_TCM_WWW","/webdata/html/"},
	          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
	          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
	          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
	          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
	          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
	     };

	     public LatecoereServerResourceBundle(){
	            super();
	            addHash(cHash);
	     }

	     //trong 5/15
	     public String getDbClient(){
	      return ("LATECOURE");
	     }

	}