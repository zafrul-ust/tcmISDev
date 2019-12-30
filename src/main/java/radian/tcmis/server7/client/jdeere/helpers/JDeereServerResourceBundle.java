package radian.tcmis.server7.client.jdeere.helpers;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class JDeereServerResourceBundle  extends ServerResourceBundle {

	     static final Object[][] cHash = {
	          {"LOG_DIR","/home/servlet/radian/logs/jdeere"},
	          {"DEBUG","false"},
	          {"DB_CLIENT","JDEERE"},
	          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

	          {"COST_REPORT","/tcmIS/jdeere/betacostreport?"},
	          {"SCHEDULDE_DELIVERY","/tcmIS/jdeere/schdelivery?"},
	          {"CANCEL_MR","/tcmIS/jdeere/cancel?"},
	          {"WASTE_ORDER","/tcmIS/jdeere/wasteorder?"},
	          {"WASTE_ADHOC_REPORT","/tcmIS/jdeere/wastereport?"},
	          {"USAGE_ADHOC_REPORT","/tcmIS/jdeere/usagereport?"},
	          {"MATERIAL_MATRIX_REPORT","/tcmIS/jdeere/matrixreport?"},
	          {"VIEW_MSDS","/tcmIS/jdeere/ViewMsds?"},
	          {"BATCH_REPORT_SERVLET","/tcmIS/jdeere/batchreport?"},
	          {"REGISTER_SERVLET","/tcmIS/jdeere/Register"},

	          {"SALES_ORDER_LOCATION","www.tcmis.com"},
	          {"SALES_ORDER_PORT","443"},
	          // Larry Note, this is useless, why is it still here.
	          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.jdeere.servlets.JDeereBuildSalesOrder"},

	          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
	          {"BASE_DIR_TCM_WWW","/webdata/html/"},
	          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
	          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
	          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
	          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
	          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
	     };

	     public JDeereServerResourceBundle(){
	            super();
	            addHash(cHash);
	     }

	     //trong 5/15
	     public String getDbClient(){
	      return ("JDEERE");
	     }

	}