package radian.tcmis.server7.client.bombardier.helpers;

	import radian.tcmis.server7.share.helpers.ServerResourceBundle;

	public class BombardierServerResourceBundle  extends ServerResourceBundle {

		     static final Object[][] cHash = {
		          {"LOG_DIR","/home/servlet/radian/logs/bombardier"},
		          {"DEBUG","false"},
		          {"DB_CLIENT","NORWEGIAN"},
		          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

		          {"COST_REPORT","/tcmIS/bombardier/betacostreport?"},
		          {"SCHEDULDE_DELIVERY","/tcmIS/bombardier/schdelivery?"},
		          {"CANCEL_MR","/tcmIS/bombardier/cancel?"},
		          {"WASTE_ORDER","/tcmIS/bombardier/wasteorder?"},
		          {"WASTE_ADHOC_REPORT","/tcmIS/bombardier/wastereport?"},
		          {"USAGE_ADHOC_REPORT","/tcmIS/bombardier/usagereport?"},
		          {"MATERIAL_MATRIX_REPORT","/tcmIS/bombardier/matrixreport?"},
		          {"VIEW_MSDS","/tcmIS/bombardier/ViewMsds?"},
		          {"BATCH_REPORT_SERVLET","/tcmIS/bombardier/batchreport?"},
		          {"REGISTER_SERVLET","/tcmIS/bombardier/Register"},

		          {"SALES_ORDER_LOCATION","www.tcmis.com"},
		          {"SALES_ORDER_PORT","443"},
		          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.bombardier.servlets.BombardierBuildSalesOrder"},

		          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
		          {"BASE_DIR_TCM_WWW","/webdata/html/"},
		          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
		          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
		          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
		          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
		          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
		     };

		     public BombardierServerResourceBundle(){
		            super();
		            addHash(cHash);
		     }

		     //trong 5/15
		     public String getDbClient(){
		      return ("BOMBARDIER");
		     }

		}
