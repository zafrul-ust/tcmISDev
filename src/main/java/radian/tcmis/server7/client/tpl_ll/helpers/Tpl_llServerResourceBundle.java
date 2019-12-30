package radian.tcmis.server7.client.tpl_ll.helpers;

	import radian.tcmis.server7.share.helpers.ServerResourceBundle;

	public class Tpl_llServerResourceBundle  extends ServerResourceBundle {

		     static final Object[][] cHash = {
		          {"LOG_DIR","/home/servlet/radian/logs/tpl_ll"},
		          {"DEBUG","false"},
		          {"DB_CLIENT","TPL_LL"},
		          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

		          {"COST_REPORT","/tcmIS/tpl_ll/betacostreport?"},
		          {"SCHEDULDE_DELIVERY","/tcmIS/tpl_ll/schdelivery?"},
		          {"CANCEL_MR","/tcmIS/tpl_ll/cancel?"},
		          {"WASTE_ORDER","/tcmIS/tpl_ll/wasteorder?"},
		          {"WASTE_ADHOC_REPORT","/tcmIS/tpl_ll/wastereport?"},
		          {"USAGE_ADHOC_REPORT","/tcmIS/tpl_ll/usagereport?"},
		          {"MATERIAL_MATRIX_REPORT","/tcmIS/tpl_ll/matrixreport?"},
		          {"VIEW_MSDS","/tcmIS/tpl_ll/ViewMsds?"},
		          {"BATCH_REPORT_SERVLET","/tcmIS/tpl_ll/batchreport?"},
		          {"REGISTER_SERVLET","/tcmIS/tpl_ll/Register"},

		          {"SALES_ORDER_LOCATION","www.tcmis.com"},
		          {"SALES_ORDER_PORT","443"},
		          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.tpl_ll.servlets.Tpl_llBuildSalesOrder"},

		          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
		          {"BASE_DIR_TCM_WWW","/webdata/html/"},
		          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
		          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
		          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
		          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
		          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
		     };

		     public Tpl_llServerResourceBundle(){
		            super();
		            addHash(cHash);
		     }

		     //trong 5/15
		     public String getDbClient(){
		      return ("TPL_LL");
		     }

		}
