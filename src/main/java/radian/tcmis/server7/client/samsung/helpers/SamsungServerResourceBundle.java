package radian.tcmis.server7.client.samsung.helpers;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class SamsungServerResourceBundle  extends ServerResourceBundle {

	     static final Object[][] cHash = {
	          {"LOG_DIR","/home/servlet/radian/logs/samsung"},
	          {"DEBUG","false"},
	          {"DB_CLIENT","SAMSUNG"},
	          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

	          {"COST_REPORT","/tcmIS/samsung/betacostreport?"},
	          {"SCHEDULDE_DELIVERY","/tcmIS/samsung/schdelivery?"},
	          {"CANCEL_MR","/tcmIS/samsung/cancel?"},
	          {"WASTE_ORDER","/tcmIS/samsung/wasteorder?"},
	          {"WASTE_ADHOC_REPORT","/tcmIS/samsung/wastereport?"},
	          {"USAGE_ADHOC_REPORT","/tcmIS/samsung/usagereport?"},
	          {"MATERIAL_MATRIX_REPORT","/tcmIS/samsung/matrixreport?"},
	          {"VIEW_MSDS","/tcmIS/samsung/ViewMsds?"},
	          {"BATCH_REPORT_SERVLET","/tcmIS/samsung/batchreport?"},
	          {"REGISTER_SERVLET","/tcmIS/samsung/Register"},

	          {"SALES_ORDER_LOCATION","www.tcmis.com"},
	          {"SALES_ORDER_PORT","443"},
	          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.samsung.servlets.SamsungBuildSalesOrder"},

	          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
	          {"BASE_DIR_TCM_WWW","/webdata/html/"},
	          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
	          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
	          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
	          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
	          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
	     };

	     public SamsungServerResourceBundle(){
	            super();
	            addHash(cHash);
	     }

	     //trong 5/15
	     public String getDbClient(){
	      return ("SAMSUNG");
	     }

	}