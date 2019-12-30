package radian.tcmis.server7.client.aircelle.helpers;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class AircelleServerResourceBundle  extends ServerResourceBundle {

	     static final Object[][] cHash = {
	          {"LOG_DIR","/home/servlet/radian/logs/aircelle"},
	          {"DEBUG","false"},
	          {"DB_CLIENT","AIRCELLE"},
	          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

	          {"COST_REPORT","/tcmIS/aircelle/betacostreport?"},
	          {"SCHEDULDE_DELIVERY","/tcmIS/aircelle/schdelivery?"},
	          {"CANCEL_MR","/tcmIS/aircelle/cancel?"},
	          {"WASTE_ORDER","/tcmIS/aircelle/wasteorder?"},
	          {"WASTE_ADHOC_REPORT","/tcmIS/aircelle/wastereport?"},
	          {"USAGE_ADHOC_REPORT","/tcmIS/aircelle/usagereport?"},
	          {"MATERIAL_MATRIX_REPORT","/tcmIS/aircelle/matrixreport?"},
	          {"VIEW_MSDS","/tcmIS/aircelle/ViewMsds?"},
	          {"BATCH_REPORT_SERVLET","/tcmIS/aircelle/batchreport?"},
	          {"REGISTER_SERVLET","/tcmIS/aircelle/Register"},

	          {"SALES_ORDER_LOCATION","www.tcmis.com"},
	          {"SALES_ORDER_PORT","443"},
	          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.aircelle.servlets.AircelleBuildSalesOrder"},

	          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
	          {"BASE_DIR_TCM_WWW","/webdata/html/"},
	          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
	          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
	          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
	          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
	          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
	     };

	     public AircelleServerResourceBundle(){
	            super();
	            addHash(cHash);
	     }

	     //trong 5/15
	     public String getDbClient(){
	      return ("AIRCELLE");
	     }

	}