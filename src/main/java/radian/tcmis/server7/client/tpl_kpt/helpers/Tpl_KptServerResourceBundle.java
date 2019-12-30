package radian.tcmis.server7.client.tpl_kpt.helpers;

	import radian.tcmis.server7.share.helpers.ServerResourceBundle;

	public class Tpl_KptServerResourceBundle  extends ServerResourceBundle {

		     static final Object[][] cHash = {
		          {"LOG_DIR","/home/servlet/radian/logs/tpl_kpt"},
		          {"DEBUG","false"},
		          {"DB_CLIENT","TPL_KPT"},
		          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

		          {"COST_REPORT","/tcmIS/tpl_kpt/betacostreport?"},
		          {"SCHEDULDE_DELIVERY","/tcmIS/tpl_kpt/schdelivery?"},
		          {"CANCEL_MR","/tcmIS/tpl_kpt/cancel?"},
		          {"WASTE_ORDER","/tcmIS/tpl_kpt/wasteorder?"},
		          {"WASTE_ADHOC_REPORT","/tcmIS/tpl_kpt/wastereport?"},
		          {"USAGE_ADHOC_REPORT","/tcmIS/tpl_kpt/usagereport?"},
		          {"MATERIAL_MATRIX_REPORT","/tcmIS/tpl_kpt/matrixreport?"},
		          {"VIEW_MSDS","/tcmIS/tpl_kpt/ViewMsds?"},
		          {"BATCH_REPORT_SERVLET","/tcmIS/tpl_kpt/batchreport?"},
		          {"REGISTER_SERVLET","/tcmIS/tpl_kpt/Register"},

		          {"SALES_ORDER_LOCATION","www.tcmis.com"},
		          {"SALES_ORDER_PORT","443"},
		          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.tpl_Kpt.servlets.Tpl_KptBuildSalesOrder"},

		          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
		          {"BASE_DIR_TCM_WWW","/webdata/html/"},
		          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
		          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
		          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
		          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
		          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
		     };

		     public Tpl_KptServerResourceBundle(){
		            super();
		            addHash(cHash);
		     }

		     //trong 5/15
		     public String getDbClient(){
		      return ("TPL_KPT");
		     }

		}
