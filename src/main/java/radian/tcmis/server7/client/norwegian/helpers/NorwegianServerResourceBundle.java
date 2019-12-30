package radian.tcmis.server7.client.norwegian.helpers;

	import radian.tcmis.server7.share.helpers.ServerResourceBundle;

	public class NorwegianServerResourceBundle  extends ServerResourceBundle {

		     static final Object[][] cHash = {
		          {"LOG_DIR","/home/servlet/radian/logs/norwegian"},
		          {"DEBUG","false"},
		          {"DB_CLIENT","NORWEGIAN"},
		          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

		          {"COST_REPORT","/tcmIS/norwegian/betacostreport?"},
		          {"SCHEDULDE_DELIVERY","/tcmIS/norwegian/schdelivery?"},
		          {"CANCEL_MR","/tcmIS/norwegian/cancel?"},
		          {"WASTE_ORDER","/tcmIS/norwegian/wasteorder?"},
		          {"WASTE_ADHOC_REPORT","/tcmIS/norwegian/wastereport?"},
		          {"USAGE_ADHOC_REPORT","/tcmIS/norwegian/usagereport?"},
		          {"MATERIAL_MATRIX_REPORT","/tcmIS/norwegian/matrixreport?"},
		          {"VIEW_MSDS","/tcmIS/norwegian/ViewMsds?"},
		          {"BATCH_REPORT_SERVLET","/tcmIS/norwegian/batchreport?"},
		          {"REGISTER_SERVLET","/tcmIS/norwegian/Register"},

		          {"SALES_ORDER_LOCATION","www.tcmis.com"},
		          {"SALES_ORDER_PORT","443"},
		          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.norwegian.servlets.NorwegianBuildSalesOrder"},

		          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
		          {"BASE_DIR_TCM_WWW","/webdata/html/"},
		          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
		          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
		          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
		          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
		          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
		     };

		     public NorwegianServerResourceBundle(){
		            super();
		            addHash(cHash);
		     }

		     //trong 5/15
		     public String getDbClient(){
		      return ("NORWEGIAN");
		     }

		}
