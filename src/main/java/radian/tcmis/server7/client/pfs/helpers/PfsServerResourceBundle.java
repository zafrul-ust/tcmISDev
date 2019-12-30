package radian.tcmis.server7.client.pfs.helpers;

import radian.tcmis.server7.share.dbObjs.*;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class PfsServerResourceBundle  extends ServerResourceBundle {

	     static final Object[][] cHash = {
	          {"LOG_DIR","/home/servlet/radian/logs/pfs"},
	          {"DEBUG","false"},
	          {"DB_CLIENT","PFS"},
	          {"ORACLE_DRIVER","oracle.jdbc.driver.OracleDriver"},

	          {"COST_REPORT","/tcmIS/pfs/betacostreport?"},
	          {"SCHEDULDE_DELIVERY","/tcmIS/pfs/schdelivery?"},
	          {"CANCEL_MR","/tcmIS/pfs/cancel?"},
	          {"WASTE_ORDER","/tcmIS/pfs/wasteorder?"},
	          {"WASTE_ADHOC_REPORT","/tcmIS/pfs/wastereport?"},
	          {"USAGE_ADHOC_REPORT","/tcmIS/pfs/usagereport?"},
	          {"MATERIAL_MATRIX_REPORT","/tcmIS/pfs/matrixreport?"},
	          {"VIEW_MSDS","/tcmIS/pfs/ViewMsds?"},
	          {"BATCH_REPORT_SERVLET","/tcmIS/pfs/batchreport?"},
	          {"REGISTER_SERVLET","/tcmIS/pfs/Register"},

	          {"SALES_ORDER_LOCATION","www.tcmis.com"},
	          {"SALES_ORDER_PORT","443"},
	          {"SALES_ORDER_URI","/tcmIS/servlet/radian.tcmis.server7.client.pfs.servlets.PfsBuildSalesOrder"},

	          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
	          {"BASE_DIR_TCM_WWW","/webdata/html/"},
	          {"WEBS_HOME_WWW","https://www.tcmis.com/"},
	          {"WEBS_HOME_WWW_ERW_ACTIVE","https://www.tcmis.com/reports/temp/"},
	          {"WEBS_HOME_WWW_ERW_BATCH","https://www.tcmis.com/reports/"},
	          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
	          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
	     };

	     public PfsServerResourceBundle(){
	            super();
	            addHash(cHash);
	     }

	     //trong 5/15
	     public String getDbClient(){
	      return ("PFS");
	     }

	}