package radian.tcmis.server7.client.magellan.helpers;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class MagellanServerResourceBundle  extends ServerResourceBundle {

	     static final Object[][] cHash = {
	          {"LOG_DIR","/home/servlet/radian/logs/magellan"},
	          {"DEBUG","false"},
	          {"DB_CLIENT","MAGELLAN"},
	          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

	          {"COST_REPORT","/tcmIS/magellan/betacostreport?"},
	          {"SCHEDULDE_DELIVERY","/tcmIS/magellan/schdelivery?"},
	          {"CANCEL_MR","/tcmIS/magellan/cancel?"},
	          {"WASTE_ORDER","/tcmIS/magellan/wasteorder?"},
	          {"WASTE_ADHOC_REPORT","/tcmIS/magellan/wastereport?"},
	          {"USAGE_ADHOC_REPORT","/tcmIS/magellan/usagereport?"},
	          {"MATERIAL_MATRIX_REPORT","/tcmIS/magellan/matrixreport?"},
	          {"VIEW_MSDS","/tcmIS/magellan/ViewMsds?"},
	          {"BATCH_REPORT_SERVLET","/tcmIS/magellan/batchreport?"},
	          {"REGISTER_SERVLET","/tcmIS/magellan/Register"},

	          {"SALES_ORDER_LOCATION","www.tcmis.com"},
	          {"SALES_ORDER_PORT","443"},
	          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.magellan.servlets.MagellanBuildSalesOrder"},

	          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
	          {"BASE_DIR_TCM_WWW","/webdata/html/"},
	          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
	          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
	          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
	          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
	          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
	     };

	     public MagellanServerResourceBundle(){
	            super();
	            addHash(cHash);
	     }

	     //trong 5/15
	     public String getDbClient(){
	      return ("MAGELLAN");
	     }

	}