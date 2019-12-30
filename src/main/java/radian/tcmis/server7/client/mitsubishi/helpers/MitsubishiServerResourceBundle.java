package radian.tcmis.server7.client.mitsubishi.helpers;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class MitsubishiServerResourceBundle  extends ServerResourceBundle {

	     static final Object[][] cHash = {
	          {"LOG_DIR","/home/servlet/radian/logs/mitsubishi"},
	          {"DEBUG","false"},
	          {"DB_CLIENT","MITSUBISHI"},
	          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

	          {"COST_REPORT","/tcmIS/mitsubishi/betacostreport?"},
	          {"SCHEDULDE_DELIVERY","/tcmIS/mitsubishi/schdelivery?"},
	          {"CANCEL_MR","/tcmIS/mitsubishi/cancel?"},
	          {"WASTE_ORDER","/tcmIS/mitsubishi/wasteorder?"},
	          {"WASTE_ADHOC_REPORT","/tcmIS/mitsubishi/wastereport?"},
	          {"USAGE_ADHOC_REPORT","/tcmIS/mitsubishi/usagereport?"},
	          {"MATERIAL_MATRIX_REPORT","/tcmIS/mitsubishi/matrixreport?"},
	          {"VIEW_MSDS","/tcmIS/mitsubishi/ViewMsds?"},
	          {"BATCH_REPORT_SERVLET","/tcmIS/mitsubishi/batchreport?"},
	          {"REGISTER_SERVLET","/tcmIS/mitsubishi/Register"},

	          {"SALES_ORDER_LOCATION","www.tcmis.com"},
	          {"SALES_ORDER_PORT","443"},
	          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.mitsubishi.servlets.MitsubishiBuildSalesOrder"},

	          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
	          {"BASE_DIR_TCM_WWW","/webdata/html/"},
	          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
	          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
	          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
	          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
	          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
	     };

	     public MitsubishiServerResourceBundle(){
	            super();
	            addHash(cHash);
	     }

	     //trong 5/15
	     public String getDbClient(){
	      return ("MITSUBISHI");
	     }

	}