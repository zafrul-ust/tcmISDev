package radian.tcmis.server7.client.cpp.helpers;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class CPPServerResourceBundle  extends ServerResourceBundle {

	     static final Object[][] cHash = {
	          {"LOG_DIR","/home/servlet/radian/logs/cpp"},
	          {"DEBUG","false"},
	          {"DB_CLIENT","CPP"},
	          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

	          {"COST_REPORT","/tcmIS/cpp/betacostreport?"},
	          {"SCHEDULDE_DELIVERY","/tcmIS/cpp/schdelivery?"},
	          {"CANCEL_MR","/tcmIS/cpp/cancel?"},
	          {"WASTE_ORDER","/tcmIS/cpp/wasteorder?"},
	          {"WASTE_ADHOC_REPORT","/tcmIS/cpp/wastereport?"},
	          {"USAGE_ADHOC_REPORT","/tcmIS/cpp/usagereport?"},
	          {"MATERIAL_MATRIX_REPORT","/tcmIS/cpp/matrixreport?"},
	          {"VIEW_MSDS","/tcmIS/cpp/ViewMsds?"},
	          {"BATCH_REPORT_SERVLET","/tcmIS/cpp/batchreport?"},
	          {"REGISTER_SERVLET","/tcmIS/cpp/Register"},

	          {"SALES_ORDER_LOCATION","www.tcmis.com"},
	          {"SALES_ORDER_PORT","443"},
	          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.cpp.servlets.CPPBuildSalesOrder"},

	          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
	          {"BASE_DIR_TCM_WWW","/webdata/html/"},
	          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
	          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
	          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
	          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
	          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
	     };

	     public CPPServerResourceBundle(){
	            super();
	            addHash(cHash);
	     }

	     //trong 5/15
	     public String getDbClient(){
	      return ("CPP");
	     }

	}

