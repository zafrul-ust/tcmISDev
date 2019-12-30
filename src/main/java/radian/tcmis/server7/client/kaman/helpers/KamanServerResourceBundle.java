package radian.tcmis.server7.client.kaman.helpers;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class KamanServerResourceBundle  extends ServerResourceBundle {

	     static final Object[][] cHash = {
	          {"LOG_DIR","/home/servlet/radian/logs/kaman"},
	          {"DEBUG","false"},
	          {"DB_CLIENT","KAMAN"},
	          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

	          {"COST_REPORT","/tcmIS/kaman/betacostreport?"},
	          {"SCHEDULDE_DELIVERY","/tcmIS/kaman/schdelivery?"},
	          {"CANCEL_MR","/tcmIS/kaman/cancel?"},
	          {"WASTE_ORDER","/tcmIS/kaman/wasteorder?"},
	          {"WASTE_ADHOC_REPORT","/tcmIS/kaman/wastereport?"},
	          {"USAGE_ADHOC_REPORT","/tcmIS/kaman/usagereport?"},
	          {"MATERIAL_MATRIX_REPORT","/tcmIS/kaman/matrixreport?"},
	          {"VIEW_MSDS","/tcmIS/kaman/ViewMsds?"},
	          {"BATCH_REPORT_SERVLET","/tcmIS/kaman/batchreport?"},
	          {"REGISTER_SERVLET","/tcmIS/kaman/Register"},

	          {"SALES_ORDER_LOCATION","www.tcmis.com"},
	          {"SALES_ORDER_PORT","443"},
	          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.kaman.servlets.KamanBuildSalesOrder"},

	          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
	          {"BASE_DIR_TCM_WWW","/webdata/html/"},
	          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
	          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
	          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
	          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
	          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
	     };

	     public KamanServerResourceBundle(){
	            super();
	            addHash(cHash);
	     }

	     //trong 5/15
	     public String getDbClient(){
	      return ("KAMAN");
	     }

	}