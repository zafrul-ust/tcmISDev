package radian.tcmis.server7.client.eaton.helpers;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class EatonServerResourceBundle  extends ServerResourceBundle {

	     static final Object[][] cHash = {
	          {"LOG_DIR","/home/servlet/radian/logs/eaton"},
	          {"DEBUG","false"},
	          {"DB_CLIENT","EATON"},
	          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

	          {"COST_REPORT","/tcmIS/eaton/betacostreport?"},
	          {"SCHEDULDE_DELIVERY","/tcmIS/eaton/schdelivery?"},
	          {"CANCEL_MR","/tcmIS/eaton/cancel?"},
	          {"WASTE_ORDER","/tcmIS/eaton/wasteorder?"},
	          {"WASTE_ADHOC_REPORT","/tcmIS/eaton/wastereport?"},
	          {"USAGE_ADHOC_REPORT","/tcmIS/eaton/usagereport?"},
	          {"MATERIAL_MATRIX_REPORT","/tcmIS/eaton/matrixreport?"},
	          {"VIEW_MSDS","/tcmIS/eaton/ViewMsds?"},
	          {"BATCH_REPORT_SERVLET","/tcmIS/eaton/batchreport?"},
	          {"REGISTER_SERVLET","/tcmIS/eaton/Register"},

	          {"SALES_ORDER_LOCATION","www.tcmis.com"},
	          {"SALES_ORDER_PORT","443"},
	          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.eaton.servlets.EatonBuildSalesOrder"},

	          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
	          {"BASE_DIR_TCM_WWW","/webdata/html/"},
	          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
	          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
	          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
	          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
	          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
	     };

	     public EatonServerResourceBundle(){
	            super();
	            addHash(cHash);
	     }

	     //trong 5/15
	     public String getDbClient(){
	      return ("EATON");
	     }

	}