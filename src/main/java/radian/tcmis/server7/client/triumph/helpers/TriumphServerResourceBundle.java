package radian.tcmis.server7.client.triumph.helpers;

	import radian.tcmis.server7.share.helpers.ServerResourceBundle;

	public class TriumphServerResourceBundle  extends ServerResourceBundle {

		     static final Object[][] cHash = {
		          {"LOG_DIR","/home/servlet/radian/logs/triumph"},
		          {"DEBUG","false"},
		          {"DB_CLIENT","TRIUMPH"},
		          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

		          {"COST_REPORT","/tcmIS/triumph/betacostreport?"},
		          {"SCHEDULDE_DELIVERY","/tcmIS/triumph/schdelivery?"},
		          {"CANCEL_MR","/tcmIS/triumph/cancel?"},
		          {"WASTE_ORDER","/tcmIS/triumph/wasteorder?"},
		          {"WASTE_ADHOC_REPORT","/tcmIS/triumph/wastereport?"},
		          {"USAGE_ADHOC_REPORT","/tcmIS/triumph/usagereport?"},
		          {"MATERIAL_MATRIX_REPORT","/tcmIS/triumph/matrixreport?"},
		          {"VIEW_MSDS","/tcmIS/triumph/ViewMsds?"},
		          {"BATCH_REPORT_SERVLET","/tcmIS/triumph/batchreport?"},
		          {"REGISTER_SERVLET","/tcmIS/triumph/Register"},

		          {"SALES_ORDER_LOCATION","www.tcmis.com"},
		          {"SALES_ORDER_PORT","443"},
		          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.triumph.servlets.TriumphBuildSalesOrder"},

		          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
		          {"BASE_DIR_TCM_WWW","/webdata/html/"},
		          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
		          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
		          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
		          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
		          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
		     };

		     public TriumphServerResourceBundle(){
		            super();
		            addHash(cHash);
		     }

		     //trong 5/15
		     public String getDbClient(){
		      return ("TRIUMPH");
		     }

		}
