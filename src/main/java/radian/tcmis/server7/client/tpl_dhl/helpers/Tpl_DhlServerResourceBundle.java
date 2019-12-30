package radian.tcmis.server7.client.tpl_dhl.helpers;

	import radian.tcmis.server7.share.helpers.ServerResourceBundle;

	public class Tpl_DhlServerResourceBundle  extends ServerResourceBundle {

		     static final Object[][] cHash = {
		          {"LOG_DIR","/home/servlet/radian/logs/tpl_dhl"},
		          {"DEBUG","false"},
		          {"DB_CLIENT","TPL_DHL"},
		          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

		          {"COST_REPORT","/tcmIS/tpl_dhl/betacostreport?"},
		          {"SCHEDULDE_DELIVERY","/tcmIS/tpl_dhl/schdelivery?"},
		          {"CANCEL_MR","/tcmIS/tpl_dhl/cancel?"},
		          {"WASTE_ORDER","/tcmIS/tpl_dhl/wasteorder?"},
		          {"WASTE_ADHOC_REPORT","/tcmIS/tpl_dhl/wastereport?"},
		          {"USAGE_ADHOC_REPORT","/tcmIS/tpl_dhl/usagereport?"},
		          {"MATERIAL_MATRIX_REPORT","/tcmIS/tpl_dhl/matrixreport?"},
		          {"VIEW_MSDS","/tcmIS/tpl_dhl/ViewMsds?"},
		          {"BATCH_REPORT_SERVLET","/tcmIS/tpl_dhl/batchreport?"},
		          {"REGISTER_SERVLET","/tcmIS/tpl_dhl/Register"},

		          {"SALES_ORDER_LOCATION","www.tcmis.com"},
		          {"SALES_ORDER_PORT","443"},
		          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.tpl_Dhl.servlets.Tpl_DhlBuildSalesOrder"},

		          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
		          {"BASE_DIR_TCM_WWW","/webdata/html/"},
		          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
		          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
		          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
		          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
		          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
		     };

		     public Tpl_DhlServerResourceBundle(){
		            super();
		            addHash(cHash);
		     }

		     //trong 5/15
		     public String getDbClient(){
		      return ("TPL_DHL");
		     }

		}
